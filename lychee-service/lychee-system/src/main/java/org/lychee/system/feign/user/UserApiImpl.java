package org.lychee.system.feign.user;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.lychee.system.api.entity.LycheeUser;
import org.lychee.system.api.feign.user.UserApi;
import org.lychee.system.service.ILycheeUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

@ApiIgnore
@RestController
public class UserApiImpl implements UserApi {

    @Resource
    private ILycheeUserService lycheeUserService;

    @Override
    @PostMapping(API_PREFIX+"/login")
    public LycheeUser login(@RequestParam("username") String username, @RequestParam("password") String password) {
        return lycheeUserService
                .getOne(Wrappers.<LycheeUser>lambdaQuery()
                        .eq(LycheeUser::getUsername,username)
                        .eq(LycheeUser::getPassword,password));
    }

    @Override
    @PostMapping(API_PREFIX+"/login/username")
    public LycheeUser loadUserByUsername(@RequestParam("username") String username) {
        return lycheeUserService
                .getOne(Wrappers.<LycheeUser>lambdaQuery()
                        .eq(LycheeUser::getUsername,username));
    }
}
