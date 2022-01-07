package org.lychee.feign.permission;

import org.lychee.entity.LycheePermission;
import org.lychee.entity.LycheeRole;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "lychee-system", fallback = PermissionApiFallback.class)
public interface PermissionApi {

    String API_PREFIX = "/permission";

    @PostMapping(API_PREFIX+"/role")
    List<LycheePermission> selectPermissionByRoleId(@RequestBody List<Long>  roleIds);

}
