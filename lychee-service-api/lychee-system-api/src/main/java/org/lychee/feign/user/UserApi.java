package org.lychee.feign.user;

import org.lychee.entity.LycheeUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "lychee-system", fallback = UserApiFallback.class)
public interface UserApi {

    String API_PREFIX = "/user";

    @PostMapping(API_PREFIX+"/login")
    LycheeUser login(@RequestParam("username") String username, @RequestParam("password") String password);

    @PostMapping(API_PREFIX+"/login/username")
    LycheeUser loadUserByUsername(@RequestParam("username") String username);
}
