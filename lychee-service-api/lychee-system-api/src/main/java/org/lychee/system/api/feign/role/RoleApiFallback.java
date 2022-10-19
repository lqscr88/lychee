package org.lychee.system.api.feign.role;

import lombok.extern.slf4j.Slf4j;
import org.lychee.system.api.entity.LycheeRole;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * feign调用容错处理
 */
@Component
@Slf4j
public class RoleApiFallback implements RoleApi {


    @Override
    public List<LycheeRole> selectRoleByUserId(Long userId) {
        log.info("feign调用失败");
        return null;
    }
}
