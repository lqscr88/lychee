package org.lychee.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWT;
import org.lychee.constant.AuthConstant;
import org.lychee.constant.TokenConstant;
import org.lychee.entity.LycheePermission;
import org.lychee.entity.LycheeRole;
import org.lychee.entity.LycheeUser;
import org.lychee.feign.permission.PermissionApi;
import org.lychee.feign.role.RoleApi;
import org.lychee.feign.user.UserApi;
import org.lychee.service.LoginService;
import org.lychee.utils.JwtUtil;
import org.lychee.utils.RedisUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    @Resource
    private RedisUtil redisUtil;
    @Override
    public String getToken(String account, String password) {
        JWT jwt = JwtUtil
                .createToken();
        Map<String,Object> param=new HashMap<>();
        LycheeUser user = userApi.login(account, password);
        if (Objects.isNull(user)){
            return AuthConstant.USERNAME_PASSWORD_UNFIND;
        }
        jwt.setPayload(TokenConstant.TOKEN_USER_ID, user.getId());
        jwt.setPayload(TokenConstant.TOKEN_USERNAME, user.getUsername());
        param.put(TokenConstant.TOKEN_USERNAME,user.getUsername());
        List<LycheeRole> lycheeRole = roleApi.selectRoleByUserId(user.getId());
        if (CollectionUtil.isNotEmpty(lycheeRole)){
            param.put(TokenConstant.TOKEN_ROLE,lycheeRole.stream().map(LycheeRole::getName).collect(Collectors.toList()).toString().replace("[","").replace("]",""));
            List<LycheePermission> lycheePermission = permissionApi.selectPermissionByRoleId(lycheeRole.stream().map(LycheeRole::getId).collect(Collectors.toList()));
            if (CollectionUtil.isNotEmpty(lycheePermission)){
                param.put(TokenConstant.TOKEN_PERMISSION, lycheePermission.stream().map(LycheePermission::getPermissionPath).collect(Collectors.toList()).toString().replace("[","").replace("]",""));
            }
        }
        redisUtil.hmset(user.getId().toString(), param);
        return jwt.setKey(AuthConstant.JWT_KEY.getBytes()).sign();
    }
}
