package org.lychee.route;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;

import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;
import java.net.URI;
import java.util.Map;

@Configuration
public class GatewayRoute implements RouteDefinitionLocator {

    //服务发现客户端
    @Resource
    private DiscoveryClient discoveryClient;
    @Resource
    private DiscoveryLocatorProperties discoveryLocatorProperties;

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        return Flux.fromIterable(discoveryClient.getServices())
                .map(discoveryClient::getInstances)
                .filter(instances -> !instances.isEmpty())
                .map(instances -> instances.get(0))
                // 根据 includeExpression 表达式,过滤不符合的 ServiceInstance
                .map(instance -> {
                    String serviceId = instance.getServiceId();
                    RouteDefinition routeDefinition = new RouteDefinition();
                    routeDefinition.setId(serviceId);
                    String uri = "lb://"+serviceId;
                    routeDefinition.setUri(URI.create(uri));
                    //添加配置的断言表达式
                    for (PredicateDefinition original : discoveryLocatorProperties.getPredicates()) {
                        PredicateDefinition predicate = new PredicateDefinition();
                        predicate.setName(original.getName());
                        String path="/"+serviceId.split("-")[1]+"/**";
                        predicate.addArg("Path",path);
                        routeDefinition.getPredicates().add(predicate);
                    }
                    return routeDefinition;
                });
    }


}
