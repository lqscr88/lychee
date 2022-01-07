package org.lychee.feign.permission;

import lombok.extern.slf4j.Slf4j;
import org.lychee.entity.LycheePermission;
import org.lychee.entity.LycheeRole;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * feign调用容错处理
 */
@Component
@Slf4j
public class PermissionApiFallback implements PermissionApi {

    @Override
    public List<LycheePermission> selectPermissionByRoleId(List<Long>  roleIds) {
        log.info("feign调用失败");
        return null;
    }
}
