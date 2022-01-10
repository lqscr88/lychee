package org.lychee.filter;

import cn.hutool.jwt.JWT;
import com.alibaba.nacos.common.utils.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.lychee.config.UrlConfig;
import org.lychee.constant.AuthConstant;
import org.lychee.constant.TokenConstant;
import org.lychee.provider.ResponseProvider;
import org.lychee.utils.JwtUtil;
import org.lychee.utils.RedisUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@Slf4j
public class TokenFilter implements GlobalFilter, Ordered {

    @Resource
    private  ObjectMapper objectMapper;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private UrlConfig urlConfig;



    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("进入到全局过滤器了........");
        String path = exchange.getRequest().getPath().toString();
        if (!CollectionUtils.isEmpty(urlConfig.getUrl()) && urlConfig.getUrl().contains(path) || urlConfig.getUrl().contains("/**")){
            return  chain.filter(exchange); // 放行
        }
        String token = exchange.getRequest().getHeaders().getFirst(TokenConstant.TOKEN_HEADER);
        ServerHttpResponse resp = exchange.getResponse();
        if (StringUtils.isBlank(token) ) {
            return unAuth(resp, AuthConstant.MISSING_TOKEN);
        }
        JWT jwt;
        try{
             jwt = JwtUtil.jxToken(token);
        }catch (Exception e){
            return unAuth(resp, AuthConstant.ILLEGAL_TOKEN);
        }
        String userId = jwt.getPayload(TokenConstant.TOKEN_USER_ID).toString();
        Object username = redisUtil.hget(userId, TokenConstant.TOKEN_USERNAME);
        if (Objects.isNull(username)){
            return unAuth(resp, AuthConstant.TOKEN_EXPIRED);
        }
        Object permission = redisUtil.hget(userId, TokenConstant.TOKEN_PERMISSION);
        if (Objects.isNull(permission)){
            return unAuth(resp, AuthConstant.NO_AUTHORITY);
        }
        List<String> objects = Stream.of(permission).map(String::valueOf).collect(Collectors.toList());
        List<Boolean> collect = objects.stream().map(path::contains).collect(Collectors.toList());
        if (collect.contains(true)){
            return chain.filter(exchange); // 放
        }else {
            return unAuth(resp, AuthConstant.NO_AUTHORITY);
        }
    }


    private Mono<Void> unAuth(ServerHttpResponse resp, String msg) {
        resp.setStatusCode(HttpStatus.UNAUTHORIZED);
        resp.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        String result = "";
        try {
            result = objectMapper.writeValueAsString(ResponseProvider.unAuth(msg));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        DataBuffer buffer = resp.bufferFactory().wrap(result.getBytes(StandardCharsets.UTF_8));
        return resp.writeWith(Flux.just(buffer));
    }

    @Override
    public int getOrder() {
        return -100;
    }
}