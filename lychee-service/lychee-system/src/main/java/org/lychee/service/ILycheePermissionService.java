package org.lychee.service;

import org.lychee.entity.LycheePermission;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
