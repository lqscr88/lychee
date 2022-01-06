package org.lychee.feign.permission;

import org.lychee.entity.LycheePermission;
import org.lychee.entity.LycheeRole;
import org.lychee.feign.role.RoleApi;
import org.lychee.service.ILycheePermissionService;
import org.lychee.service.ILycheeRoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

@ApiIgnore
@RestController
public class PermissionApiImpl implements PermissionApi {


    @Resource
    private ILycheePermissionService lycheePermissionService;


    @Override
    @GetMapping(API_PREFIX+"/role/{roleId}")
    public LycheePermission selectPermissionByRoleId(@PathVariable("roleId") Long roleId) {
        return lycheePermissionService.selectPermissionByRoleId(roleId);
    }
}
