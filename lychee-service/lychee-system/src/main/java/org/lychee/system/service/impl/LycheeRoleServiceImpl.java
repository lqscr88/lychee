package org.lychee.system.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lychee.system.api.entity.LycheeRole;
import org.lychee.system.mapper.LycheeRoleMapper;
import org.lychee.system.service.ILycheeRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lychee
 * @since 2022-01-05
 */
@Service
public class LycheeRoleServiceImpl extends ServiceImpl<LycheeRoleMapper, LycheeRole> implements ILycheeRoleService {

    @Override
    public List<LycheeRole> selectRoleByUserId(Long userId) {
        return baseMapper.selectRoleByUserId(userId);
    }

    @Override
    public void deleteByRelationSurface(Long roleId) {
         baseMapper.deleteByRelationSurface(roleId);
    }
}
