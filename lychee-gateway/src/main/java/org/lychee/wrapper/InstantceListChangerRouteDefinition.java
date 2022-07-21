package org.lychee.wrapper;

import org.lychee.domain.nacos.InstantceList;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class InstantceListChangerRouteDefinition {


    public static Flux<RouteDefinition> change(Publisher<InstantceList> flux) {
        Flux<InstantceList> data = flux instanceof Flux ? (Flux<InstantceList>) flux : Flux.from(flux);
        return data
                .map(InstantceList::getDoms)
                .concatMap(Flux::fromIterable)
                .map(e->{
                    RouteDefinition definition = new RouteDefinition();
                    URI uri = URI.create("lb://"+e); //UriComponentsBuilder.fromHttpUrl("lb://"+e).build().toUri();
                    // URI uri = UriComponentsBuilder.fromHttpUrl("http://baidu.com").build().toUri();
                    definition.setUri(uri);
                    //定义第一个断言
                    PredicateDefinition predicate = new PredicateDefinition();
                    predicate.setName("Path");

                    Map<String, String> predicateParams = new HashMap<>(8);
                    predicateParams.put("pattern", "/"+e+"/**");
                    predicate.setArgs(predicateParams);

                    //定义Filter
                    FilterDefinition filter = new FilterDefinition();
                    filter.setName("RewritePath");
                    Map<String, String> filterParams = new HashMap<>(8);
                    //该_genkey_前缀是固定的，见org.springframework.cloud.gateway.support.NameUtils类
                    filterParams.put("regexp", "/"+e+"/(?<remaining>.*)");
                    filterParams.put("replacement", "/${remaining}");
                    filter.setArgs(filterParams);

                    definition.setFilters(Collections.singletonList(filter));

                    definition.setPredicates(Collections.singletonList(predicate));
                    return definition;
                });
    }
}
