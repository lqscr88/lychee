package org.lychee.feign.permission;

import org.lychee.entity.LycheePermission;
import org.lychee.entity.LycheeRole;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "lychee-system", fallback = PermissionApiFallback.class)
public interface PermissionApi {

    String API_PREFIX = "/permission";

    @GetMapping(API_PREFIX+"/role/{roleId}")
    LycheePermission selectPermissionByRoleId(@PathVariable("roleId") Long roleId);

}
