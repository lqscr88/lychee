package org.lychee.route;

import org.lychee.custom.NacosHttpApi;
import org.lychee.domain.nacos.InstantceList;
import org.lychee.util.EhCacheUtils;
import org.lychee.wrapper.InstantceListChangerRouteDefinition;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.Objects;

@Component
public class RedisRouteDefinitionRepository implements RouteDefinitionRepository {

    public static final String GATEWAY_ROUTES = "geteway_routes";

    @Resource
    private NacosHttpApi nacosHttpApi;

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        if (EhCacheUtils.get(GATEWAY_ROUTES)==null) {
            Flux<RouteDefinition> change = InstantceListChangerRouteDefinition.change(Flux.just(nacosHttpApi.getInstantce()));
            EhCacheUtils.put(GATEWAY_ROUTES,change.collectList().block());
            return change;
        }else {
            return Flux.fromIterable(Objects.requireNonNull(EhCacheUtils.get(GATEWAY_ROUTES)));
        }
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return null;
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return null;
    }

}