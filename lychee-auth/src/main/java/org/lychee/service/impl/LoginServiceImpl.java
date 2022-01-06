package org.lychee.service.impl;

import cn.hutool.jwt.JWT;
import org.lychee.constant.AuthConstant;
import org.lychee.entity.LycheePermission;
import org.lychee.entity.LycheeRole;
import org.lychee.entity.LycheeUser;
import org.lychee.feign.permission.PermissionApi;
import org.lychee.feign.role.RoleApi;
import org.lychee.feign.user.UserApi;
import org.lychee.service.LoginService;
import org.lychee.utils.JwtUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lychee
 * @since 2022-01-05
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private UserApi userApi;
    @Resource
    private RoleApi roleApi;
    @Resource
    private PermissionApi permissionApi;

    @Override
    public String getToken(String account, String password) {
        JWT jwt = JwtUtil
                .createToken()
                .setPayload("account", account);
        LycheeUser user = userApi.login(account, password);
        if (Objects.isNull(user)){
            return AuthConstant.USERNAME_PASSWORD_UNFIND;
        }
        jwt.setPayload("user_id", user.getId());
        LycheeRole lycheeRole = roleApi.selectRoleByUserId(user.getId());
        if (!Objects.isNull(lycheeRole)){
            jwt.setPayload("role_id", lycheeRole.getId());
            LycheePermission lycheePermission = permissionApi.selectPermissionByRoleId(lycheeRole.getId());
            if (!Objects.isNull(lycheePermission)){
                jwt.setPayload("permission_id", lycheePermission.getId());
            }
        }
        return jwt.setKey(AuthConstant.JWT_KEY.getBytes()).sign();
    }
}
