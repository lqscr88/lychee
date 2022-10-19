package org.lychee.auth.detail;

import cn.hutool.system.UserInfo;
import org.lychee.entity.LycheeUser;
import org.lychee.feign.user.UserApi;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author qt
 * @Date 2021/3/25
 * @Description
 */

@Component
public class SystemUserDetailsService implements UserDetailsService {

    @Resource
    private UserApi userApi;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        /**
         * 1/通过userName 获取到userInfo信息
         * 2/通过User（UserDetails）返回details。
         */
        //通过userName获取用户信息
        LycheeUser lycheeUser = userApi.loadUserByUsername(userName);
        if(lycheeUser == null) {
            throw new UsernameNotFoundException("not found");
        }
        //定义权限列表.
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 用户可以访问的资源名称（或者说用户所拥有的权限） 注意：必须"ROLE_"开头
        authorities.add(new SimpleGrantedAuthority("ROLE_"+ "ADMIN"));

        return new SystemUserDetails(lycheeUser, authorities);
    }
}
