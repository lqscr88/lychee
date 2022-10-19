package org.lychee.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.lychee.system.api.entity.LycheeRole;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lychee
 * @since 2022-01-05
 */
public interface ILycheeRoleService extends IService<LycheeRole> {

    List<LycheeRole> selectRoleByUserId(Long userId);

    void deleteByRelationSurface(Long roleId);
}
