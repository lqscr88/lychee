package org.lychee.feign.user;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.lychee.entity.LycheeUser;
import org.lychee.feign.user.UserApi;
import org.lychee.service.ILycheeUserService;
import org.springframework.web.bind.annotation.GetMapping;
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
}
