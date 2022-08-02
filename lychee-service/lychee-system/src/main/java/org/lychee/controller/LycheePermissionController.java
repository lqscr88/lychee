package org.lychee.controller;




import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.lychee.constant.TokenConstant;
import org.lychee.entity.LycheePermission;
import org.lychee.result.Result;
import org.lychee.service.ILycheePermissionService;
import org.lychee.util.RedisUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    @Resource
    private RedisUtil redisUtil;

    @PostMapping("add")
    @ApiOperation(value = "新增权限")
    public Result<String> add(@ApiParam(value = "模块名称") @RequestParam(required = false) String name,Long roleId,Long userId) {
        LycheePermission lycheePermission = lycheePermissionService.getBaseMapper().selectOne(Wrappers.<LycheePermission>lambdaQuery().eq(LycheePermission::getPermissionName, name));
        if (Objects.nonNull(lycheePermission)){
            lycheePermissionService.insertByRelationSurface(lycheePermission.getId(),roleId);
            Object hget = redisUtil.hget(userId.toString(), TokenConstant.TOKEN_PERMISSION);
            if (Objects.isNull(hget)){
                redisUtil.hset(userId.toString(),TokenConstant.TOKEN_PERMISSION,lycheePermission.getPermissionPath());
            }else {
                List<String> collect = Stream.of(hget).map(String::valueOf).collect(Collectors.toList());
                collect.add(lycheePermission.getPermissionPath());
                redisUtil.hset(userId.toString(),TokenConstant.TOKEN_PERMISSION, String.join(",", collect));
            }
        }
        return Result.success("新增成功");
    }

    @GetMapping("info/{roleId}")
    @ApiOperation(value = "根据角色id查询权限")
    public Result<LycheePermission> info(@PathVariable("roleId") Long roleId) {
        LycheePermission lycheePermission = lycheePermissionService.selectPermissionByRoleIdOne(roleId);
        return Result.success(lycheePermission);
    }

    @PostMapping("info")
    @ApiOperation(value = "根据角色ids查询权限")
    public Result<List<LycheePermission>> info(List<Long> roleIds) {
        List<LycheePermission> lycheePermission = lycheePermissionService.selectPermissionByRoleId(roleIds);
        return Result.success(lycheePermission);
    }

    @DeleteMapping("delete/{permissionId}/{userId}")
    @ApiOperation(value = "删除权限")
    public Result<String> delete(@PathVariable("permissionId") Long permissionId,@PathVariable("userId") Long userId) {
        LycheePermission lycheePermission = lycheePermissionService.getBaseMapper().selectById(permissionId);
        int i = lycheePermissionService.getBaseMapper().deleteById(permissionId);
        lycheePermissionService.deleteByRelationSurface(permissionId);
        Object hget = redisUtil.hget(userId.toString(), TokenConstant.TOKEN_PERMISSION);
        if (!Objects.isNull(hget)){
            List<String> collect = Stream.of(hget).map(String::valueOf).collect(Collectors.toList());
            collect.remove(lycheePermission.getPermissionPath());
            redisUtil.hset(userId.toString(),TokenConstant.TOKEN_PERMISSION, String.join(",", collect));

        }
        return Result.success(i == 1?"删除成功":"删除失败");
    }




}
