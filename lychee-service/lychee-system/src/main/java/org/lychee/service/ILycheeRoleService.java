package org.lychee.service;

import org.lychee.entity.LycheeRole;
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
public interface ILycheeRoleService extends IService<LycheeRole> {

    List<LycheeRole> selectRoleByUserId(Long userId);
}
