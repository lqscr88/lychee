package org.lychee.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.lychee.entity.LycheeRole;
import org.lychee.result.R;
import org.lychee.service.ILycheeRoleService;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lychee
 * @since 2022-01-05
 */
@Controller
@RequestMapping("/system/role")
public class LycheeRoleController {

    @Resource
    private ILycheeRoleService lycheeRoleService;

    @PostMapping("add")
    @ApiOperation(value = "新增角色")
    public R<String> add(@ApiParam(value = "账号") @RequestParam(required = false) String name) {
        int insert = lycheeRoleService.getBaseMapper().insert(LycheeRole.builder().name(name).createTime(LocalDate.now()).build());
        return R.data(insert == 1?"新增成功":"新增失败");
    }

    @GetMapping("info/{userId}")
    @ApiOperation(value = "根据用户id查询角色")
    public R<List<LycheeRole> > info(@PathVariable("userId") Long userId) {
        List<LycheeRole> lycheeRole = lycheeRoleService.selectRoleByUserId(userId);
        return R.data(lycheeRole);
    }

    @DeleteMapping("delete/{roleId}")
    @ApiOperation(value = "删除角色")
    public R<String> delete(@PathVariable("roleId") Long roleId) {
        int i = lycheeRoleService.getBaseMapper().deleteById(roleId);
        lycheeRoleService.deleteByRelationSurface(roleId);
        return R.data(i == 1?"删除成功":"删除失败");
    }

}
