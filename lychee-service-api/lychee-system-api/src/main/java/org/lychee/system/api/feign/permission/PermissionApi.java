package org.lychee.system.api.feign.permission;

import org.lychee.system.api.entity.LycheePermission;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "lychee-system", fallback = PermissionApiFallback.class)
public interface PermissionApi {

    String API_PREFIX = "/permission";

    @PostMapping(API_PREFIX+"/role")
    List<LycheePermission> selectPermissionByRoleId(@RequestBody List<Long>  roleIds);

}
