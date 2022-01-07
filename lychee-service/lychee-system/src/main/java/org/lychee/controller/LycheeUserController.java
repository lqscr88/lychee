package org.lychee.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.lychee.entity.LycheeUser;
import org.lychee.result.R;
import org.lychee.service.ILycheeUserService;
import org.lychee.utils.JwtUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @ApiOperation(value = "获取认证token", notes = "传入租户ID:tenantId,账号:account,密码:password")
    public R<String> token(@ApiParam(value = "账号") @RequestParam(required = false) String account,
                           @ApiParam(value = "密码") @RequestParam(required = false) String password) {
        LycheeUser lycheeUser=new LycheeUser();
        lycheeUser.setUsername(account);
        lycheeUser.setPassword(password);
        lycheeUser.setStatus(1);
        lycheeUser.setCreateTime(LocalDate.now());
        int insert = lycheeUserService.getBaseMapper().insert(lycheeUser);
        return R.data(insert == 1?"更新成功":"更新失败");
    }


}
