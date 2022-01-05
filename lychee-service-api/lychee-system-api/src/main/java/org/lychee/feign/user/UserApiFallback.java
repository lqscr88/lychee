package org.lychee.feign.user;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.lychee.entity.LycheeUser;
import org.springframework.stereotype.Component;

/**
 * feign调用容错处理
 */
@Component
public class UserApiFallback implements UserApi {


    @Override
    public LycheeUser getUser(String username, String password) {
        return null;
    }
}
