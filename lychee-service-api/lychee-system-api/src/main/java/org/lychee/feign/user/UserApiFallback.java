package org.lychee.feign.user;

import lombok.extern.slf4j.Slf4j;
import org.lychee.entity.LycheeUser;
import org.springframework.stereotype.Component;

/**
 * feign调用容错处理
 */
@Component
@Slf4j
public class UserApiFallback implements UserApi {


    @Override
    public LycheeUser login(String username, String password) {
        log.info("feign调用失败");
        return null;
    }

    @Override
    public LycheeUser loadUserByUsername(String username) {
        log.info("feign调用失败");
        return null;
    }
}
