package org.lychee.detail;

import cn.hutool.core.collection.CollectionUtil;
import jdk.nashorn.internal.runtime.GlobalConstants;
import lombok.Data;
import org.lychee.entity.LycheeUser;
import org.lychee.enums.PasswordEncoderTypeEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * 系统管理用户认证信息
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2021/9/27
 */
@Data
public class SystemUserDetails implements UserDetails {

    /**
     * 扩展字段：用户ID
     */
    private Long userId;

    /**
     * 默认字段
     */
    private String username;
    private String password;
    private Boolean enabled;
    private Collection<GrantedAuthority> authorities;

    /**
     * 系统管理用户
     */
    public SystemUserDetails(LycheeUser lycheeUser, List<GrantedAuthority> roles) {
        this.setUserId(lycheeUser.getId());
        this.setUsername(lycheeUser.getUsername());
        this.setPassword(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(lycheeUser.getPassword()));
        this.setEnabled(true);
        if (CollectionUtil.isNotEmpty(roles)) {
            authorities= new ArrayList<>();
            authorities.addAll(roles);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
