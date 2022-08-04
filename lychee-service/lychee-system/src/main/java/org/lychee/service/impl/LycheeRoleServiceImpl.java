package org.lychee.service.impl;

import org.lychee.entity.LycheeRole;
import org.lychee.mapper.LycheeRoleMapper;
import org.lychee.service.ILycheeRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
