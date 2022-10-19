package org.lychee.system.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.lychee.common.constant.TokenConstant;
import org.lychee.common.result.Result;
import org.lychee.entity.LycheeRole;
import org.lychee.service.ILycheeRoleService;
import org.lychee.gateway.util.RedisUtil;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.time.LocalDate;
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
@RequestMapping("/system/role")
public class LycheeRoleController {

    @Resource
    private ILycheeRoleService lycheeRoleService;
    @Resource
    private RedisUtil redisUtil;

    @PostMapping("add")
    @ApiOperation(value = "新增角色")
    public Result<String> add(@ApiParam(value = "账号") @RequestParam(required = false) String name) {
        int insert = lycheeRoleService.getBaseMapper().insert(LycheeRole.builder().name(name).createTime(LocalDate.now()).build());
        return Result.success(insert == 1?"新增成功":"新增失败");
    }

    @PostMapping("add/user")
    @ApiOperation(value = "给指定用户新增角色")
    public Result<String> addUser(@ApiParam(value = "账号") @RequestParam(required = false) Long roleId,Long userId) {
        LycheeRole lycheeRole = lycheeRoleService.getBaseMapper().selectById(roleId);
        if (!Objects.isNull(lycheeRole)){
            Object hget = redisUtil.hget(userId.toString(), TokenConstant.TOKEN_ROLE);
            if (Objects.isNull(hget)){
                redisUtil.hset(userId.toString(),TokenConstant.TOKEN_ROLE,lycheeRole.getName());
            }else {
                List<String> collect = Stream.of(hget).map(String::valueOf).collect(Collectors.toList());
                collect.add(lycheeRole.getName());
                redisUtil.hset(userId.toString(),TokenConstant.TOKEN_ROLE, String.join(",", collect));
            }
        }
        return Result.success("权限名称");
    }

    @GetMapping("info/{userId}")
    @ApiOperation(value = "根据用户id查询角色")
    public Result<List<LycheeRole> > info(@PathVariable("userId") Long userId) {
        List<LycheeRole> lycheeRole = lycheeRoleService.selectRoleByUserId(userId);
        return Result.success(lycheeRole);
    }


    @GetMapping("info/name/{userId}")
    @ApiOperation(value = "根据用户id查询角色名称")
    public Result<List<String>> infoName(@PathVariable("userId") Long userId) {
        List<String> names;
        Object hget = redisUtil.hget(userId.toString(), TokenConstant.TOKEN_ROLE);
        if (!Objects.isNull(hget)){
            names = Stream.of(hget).map(String::valueOf).collect(Collectors.toList());
        }else {
            names = lycheeRoleService.selectRoleByUserId(userId).stream().map(LycheeRole::getName).collect(Collectors.toList());
        }
        return Result.success(names);
    }
    @DeleteMapping("delete/{roleId}")
    @ApiOperation(value = "删除角色")
    public Result<String> delete(@PathVariable("roleId") Long roleId) {
        int i = lycheeRoleService.getBaseMapper().deleteById(roleId);
        lycheeRoleService.deleteByRelationSurface(roleId);
        return Result.success(i == 1?"删除成功":"删除失败");
    }

    @DeleteMapping("delete/user/{roleId}/{userId}")
    @ApiOperation(value = "删除指定用户角色")
    public Result<String> deleteUser(@PathVariable("roleId") Long roleId,@PathVariable("userId") Long userId) {
        LycheeRole lycheeRole = lycheeRoleService.getBaseMapper().selectById(roleId);
        int i = lycheeRoleService.getBaseMapper().deleteById(roleId);
        lycheeRoleService.deleteByRelationSurface(roleId);
        Object hget = redisUtil.hget(userId.toString(), TokenConstant.TOKEN_ROLE);
        if (!Objects.isNull(hget)){
            List<String> collect = Stream.of(hget).map(String::valueOf).collect(Collectors.toList());
            collect.remove(lycheeRole.getName());
            redisUtil.hset(userId.toString(),TokenConstant.TOKEN_ROLE, String.join(",", collect));

        }
        return Result.success(i == 1?"删除成功":"删除失败");
    }

}