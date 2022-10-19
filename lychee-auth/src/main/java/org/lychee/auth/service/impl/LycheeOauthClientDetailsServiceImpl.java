package org.lychee.auth.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lychee.auth.entity.LycheeOauthClientDetails;
import org.lychee.auth.mapper.LycheeOauthClientDetailsMapper;
import org.lychee.auth.service.ILycheeOauthClientDetailsService;
import org.lychee.common.enums.PasswordEncoderTypeEnum;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lychee
 * @since 2022-08-03
 */
@Service
public class LycheeOauthClientDetailsServiceImpl extends ServiceImpl<LycheeOauthClientDetailsMapper, LycheeOauthClientDetails> implements ILycheeOauthClientDetailsService, ClientDetailsService {

    @Override
    @Cacheable(cacheNames = "auth", key = "'oauth-client:'+#clientId")
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

        try {
            LycheeOauthClientDetails lycheeOauthClientDetails = baseMapper.selectOne(Wrappers.<LycheeOauthClientDetails>lambdaQuery().eq(LycheeOauthClientDetails::getClientId, clientId));
            if (lycheeOauthClientDetails!=null){
                BaseClientDetails clientDetails = new BaseClientDetails(
                        lycheeOauthClientDetails.getClientId(),
                        lycheeOauthClientDetails.getResourceIds(),
                        lycheeOauthClientDetails.getScope(),
                        lycheeOauthClientDetails.getAuthorizedGrantTypes(),
                        lycheeOauthClientDetails.getAuthorities(),
                        lycheeOauthClientDetails.getWebServerRedirectUri()
                );
                clientDetails.setClientSecret(PasswordEncoderTypeEnum.NOOP.getPrefix() + lycheeOauthClientDetails.getClientSecret());
                clientDetails.setAccessTokenValiditySeconds(lycheeOauthClientDetails.getAccessTokenValidity());
                clientDetails.setRefreshTokenValiditySeconds(lycheeOauthClientDetails.getRefreshTokenValidity());
                return clientDetails;
            }else {
                throw new NoSuchClientException("No client with requested id: " + clientId);
            }
        } catch (EmptyResultDataAccessException var4) {
            throw new NoSuchClientException("No client with requested id: " + clientId);
        }
    }
}
