package org.lychee.feign.permission;

import lombok.extern.slf4j.Slf4j;
import org.lychee.entity.LycheePermission;
import org.lychee.entity.LycheeRole;
import org.springframework.stereotype.Component;

/**
 * feign调用容错处理
 */
@Component
@Slf4j
public class PermissionApiFallback implements PermissionApi {

    @Override
    public LycheePermission selectPermissionByRoleId(Long roleId) {
        log.info("feign调用失败");
        return null;
    }
}
