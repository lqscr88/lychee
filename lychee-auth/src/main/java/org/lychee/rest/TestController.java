package org.lychee.rest;

import org.lychee.entity.LycheeUser;
import org.lychee.feign.user.UserApi;
import org.lychee.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {

    @Resource
    private UserApi userApi;

    @GetMapping("/login")
    public Result<LycheeUser> request(@RequestParam("username") String username, @RequestParam("password") String password) {
        LycheeUser login = userApi.login(username, password);
        return Result.success(login);
    }
}