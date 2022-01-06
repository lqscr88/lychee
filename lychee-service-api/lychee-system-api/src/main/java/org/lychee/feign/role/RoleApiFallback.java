package org.lychee.feign.role;

import lombok.extern.slf4j.Slf4j;
import org.lychee.entity.LycheeRole;
import org.lychee.entity.LycheeUser;
import org.springframework.stereotype.Component;

/**
 * feign调用容错处理
 */
@Component
@Slf4j
public class RoleApiFallback implements RoleApi {


    @Override
    public LycheeRole selectRoleByUserId(Long userId) {
        log.info("feign调用失败");
        return null;
    }
}
