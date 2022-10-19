package org.lychee.system.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.lychee.common.result.Result;
import org.lychee.entity.LycheeUser;
import org.lychee.service.ILycheeUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lychee
 * @since 2022-01-05
 */
@RestController
@RequestMapping("/system/user")
public class LycheeUserController {


    @Resource
    private ILycheeUserService lycheeUserService;

    @PostMapping("add")
    @ApiOperation(value = "新增用户")
    public Result<String> add(@ApiParam(value = "账号") @RequestParam(required = false) String account,
                              @ApiParam(value = "密码") @RequestParam(required = false) String password) {
        int insert = lycheeUserService.getBaseMapper().insert(LycheeUser.builder().username(account).password(password).status(1).createTime(LocalDate.now()).build());
        return Result.success(insert == 1?"新增成功":"新增失败");
    }

    @GetMapping("info/{userId}")
    @ApiOperation(value = "查询用户信息")
    public Result<LycheeUser> info(@PathVariable("userId") Long userId) {
        LycheeUser lycheeUser = lycheeUserService.getBaseMapper().selectById(userId);
        return Result.success(lycheeUser);
    }

    @PostMapping("update")
    @ApiOperation(value = "更新用户信息")
    public Result<String> update(@RequestBody LycheeUser lycheeUser) {
        int i = lycheeUserService.getBaseMapper().updateById(lycheeUser);
        return Result.success(i == 1?"更新成功":"更新失败");
    }

    @DeleteMapping("delete/{userId}")
    @ApiOperation(value = "删除用户")
    public Result<String> delete(@PathVariable("userId") Long userId) {
        int i = lycheeUserService.getBaseMapper().deleteById(userId);
        return Result.success(i == 1?"删除成功":"删除失败");
    }





}
