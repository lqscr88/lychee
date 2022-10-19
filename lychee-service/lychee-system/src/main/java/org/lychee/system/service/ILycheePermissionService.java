package org.lychee.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.lychee.system.api.entity.LycheePermission;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lychee
 * @since 2022-01-05
 */
public interface ILycheePermissionService extends IService<LycheePermission> {

    List<LycheePermission> selectPermissionByRoleId(List<Long>  roleIds);

    LycheePermission selectPermissionByRoleIdOne(Long roleId);

    void insertByRelationSurface(Long permissionId, Long roleId);

    void deleteByRelationSurface(Long permissionId);
}
