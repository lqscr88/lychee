package org.lychee.system.feign.role;


import org.lychee.system.api.entity.LycheeRole;
import org.lychee.system.api.feign.role.RoleApi;
import org.lychee.system.service.ILycheeRoleService;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

@ApiIgnore
@RestController
public class RoleApiImpl implements RoleApi {


    @Resource
    private ILycheeRoleService iLycheeRoleService;


    @Override
    @GetMapping(API_PREFIX+"/user/{userId}")
    public List<LycheeRole> selectRoleByUserId(@PathVariable("userId") Long userId){
        return iLycheeRoleService.selectRoleByUserId(userId);
    }
}
