package org.lychee.controller;




import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.bouncycastle.jcajce.provider.symmetric.AES;
import org.lychee.entity.LycheePermission;
import org.lychee.entity.LycheeRole;
import org.lychee.result.R;
import org.lychee.service.ILycheePermissionService;
import org.lychee.service.ILycheeRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lychee
 * @since 2022-01-05
 */
@Controller
@RequestMapping("/system/permission")
public class LycheePermissionController {


    @Resource
    private ILycheePermissionService lycheePermissionService;

    @PostMapping("add")
    @ApiOperation(value = "新增权限")
    public R<String> add(@ApiParam(value = "模块名称") @RequestParam(required = false) String name,Long roleId) {
        LycheePermission lycheePermission = lycheePermissionService.getBaseMapper().selectOne(Wrappers.<LycheePermission>lambdaQuery().eq(LycheePermission::getPermissionName, name));
        if (Objects.nonNull(lycheePermission)){
            lycheePermissionService.insertByRelationSurface(lycheePermission.getId(),roleId);
        }
        return R.data("新增成功");
    }

    @GetMapping("info/{roleId}")
    @ApiOperation(value = "根据角色id查询权限")
    public R<LycheePermission> info(@PathVariable("roleId") Long roleId) {
        LycheePermission lycheePermission = lycheePermissionService.selectPermissionByRoleIdOne(roleId);
        return R.data(lycheePermission);
    }

    @PostMapping("info")
    @ApiOperation(value = "根据角色ids查询权限")
    public R<List<LycheePermission>> info(List<Long> roleIds) {
        List<LycheePermission> lycheePermission = lycheePermissionService.selectPermissionByRoleId(roleIds);
        return R.data(lycheePermission);
    }




}
