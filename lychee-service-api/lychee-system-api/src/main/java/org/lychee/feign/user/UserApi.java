package org.lychee.feign.user;

import org.apache.ibatis.annotations.Param;
import org.lychee.entity.LycheeUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "system-user", fallback = UserApiFallback.class)
public interface UserApi {

    @PostMapping("/login")
    LycheeUser getUser(@Param("username") String username,@Param("password") String password);
}
