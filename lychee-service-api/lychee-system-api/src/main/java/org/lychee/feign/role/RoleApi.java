package org.lychee.feign.role;

import org.lychee.entity.LycheeRole;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "lychee-system", fallback = RoleApiFallback.class)
public interface RoleApi {

    String API_PREFIX = "/role";

    @GetMapping(API_PREFIX+"/user/{userId}")
    List<LycheeRole> selectRoleByUserId(@PathVariable("userId") Long userId);
}
