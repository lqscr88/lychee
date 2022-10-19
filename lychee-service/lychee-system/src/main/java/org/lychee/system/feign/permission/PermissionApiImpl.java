package org.lychee.system.feign.permission;


import org.lychee.system.api.entity.LycheePermission;
import org.lychee.system.api.feign.permission.PermissionApi;
import org.lychee.system.service.ILycheePermissionService;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

@ApiIgnore
@RestController
public class PermissionApiImpl implements PermissionApi {


    @Resource
    private ILycheePermissionService lycheePermissionService;


    @Override
    @PostMapping(API_PREFIX+"/role")
    public List<LycheePermission> selectPermissionByRoleId(@RequestBody List<Long>  roleIds) {
        return lycheePermissionService.selectPermissionByRoleId(roleIds);
    }
}
