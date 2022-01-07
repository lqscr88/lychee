package org.lychee.feign.permission;

import org.lychee.entity.LycheePermission;
import org.lychee.entity.LycheeRole;
import org.lychee.feign.role.RoleApi;
import org.lychee.service.ILycheePermissionService;
import org.lychee.service.ILycheeRoleService;
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
