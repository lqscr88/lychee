package org.lychee.config;

import cn.hutool.core.map.MapUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.lychee.constant.SecurityConstant;
import org.lychee.detail.SystemUserDetails;
import org.lychee.detail.SystemUserDetailsService;
import org.lychee.service.impl.LycheeOauthClientDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import sun.security.util.SecurityConstants;

import javax.annotation.Resource;
import java.security.KeyPair;
import java.util.*;


/**
 * 认证授权配置
 */
@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
@Slf4j
public class AuthorizationServerConfig  extends AuthorizationServerConfigurerAdapter {

    @Resource
    private  AuthenticationManager authenticationManager;
    @Resource
    private SystemUserDetailsService sysUserDetailsService;
    @Resource
    private LycheeOauthClientDetailsServiceImpl lycheeOauthClientDetailsService;


    /**
     * OAuth2客户端
     */
    @Override
    @SneakyThrows
    public void configure(ClientDetailsServiceConfigurer clients) {
        clients.withClientDetails(lycheeOauthClientDetailsService);
    }

//    /**
//     * 配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)
//     */
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
//
//        endpoints.tokenStore(jwtTokenStore())
//                .authenticationManager(authenticationManager)
//                .accessTokenConverter(jwtAccessTokenConverter())
//                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)  //支持GET  POST  请求获取token
//                .userDetailsService(sysUserDetailsService) //必须注入userDetailsService否则根据refresh_token无法加载用户信息
//                .reuseRefreshTokens(true);  //开启刷新token
//    }

    /**
     * 配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        DefaultTokenServices service = new DefaultTokenServices();
        service.setClientDetailsService(lycheeOauthClientDetailsService);
        service.setSupportRefreshToken(true);
        service.setTokenStore(jwtTokenStore());
        //令牌增强
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> tokenEnhancers = new ArrayList<>();
        //内容增强
        tokenEnhancers.add(tokenEnhancer());
        tokenEnhancers.add(jwtAccessTokenConverter());
        tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);

        service.setTokenEnhancer(tokenEnhancerChain);
        service.setAccessTokenValiditySeconds(7200); // 令牌默认有效期2小时
        service.setRefreshTokenValiditySeconds(259200); // 刷新令牌默认有效期3天

        endpoints
                .authenticationManager(authenticationManager)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)  //支持GET  POST  请求获取token
                .userDetailsService(sysUserDetailsService) //必须注入userDetailsService否则根据refresh_token无法加载用户信息
                .tokenServices(service)
                .reuseRefreshTokens(true)//开启刷新token
                .exceptionTranslator(loggingExceptionTranslator());

    }

    /**
     * jwt token存储模式
     */
    @Bean
    public JwtTokenStore jwtTokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * 使用非对称加密算法对token签名
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyPair());
        return converter;
    }

    /**
     * 密钥库中获取密钥对(公钥+私钥)
     */
    @Bean
    public KeyPair keyPair() {
        KeyStoreKeyFactory factory = new KeyStoreKeyFactory(new ClassPathResource("lychee.jks"), "123456".toCharArray());
        return factory.getKeyPair("lychee", "123456".toCharArray());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //允许表单提交
        security.allowFormAuthenticationForClients()
                .checkTokenAccess("permitAll()");
    }


    /**
     * JWT内容增强
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            Map<String, Object> additionalInfo = MapUtil.newHashMap();
            Object principal = authentication.getUserAuthentication().getPrincipal();
            if (principal instanceof SystemUserDetails) {
                SystemUserDetails systemUserDetails = (SystemUserDetails) principal;
                additionalInfo.put("userId", systemUserDetails.getUserId());
            }
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        };
    }

    /*
     * 报错打印
     */
    public WebResponseExceptionTranslator loggingExceptionTranslator() {
        return new DefaultWebResponseExceptionTranslator() {
            @Override
            public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
                log.error("生成token异常", e);
                e.printStackTrace();
                ResponseEntity<OAuth2Exception> responseEntity = super.translate(e);
                HttpHeaders headers = new HttpHeaders();
                headers.setAll(responseEntity.getHeaders().toSingleValueMap());
                OAuth2Exception excBody = responseEntity.getBody();
                return new ResponseEntity<>(excBody, headers, responseEntity.getStatusCode());
            }
        };
    }




}
