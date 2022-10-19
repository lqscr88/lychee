package org.lychee.system.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lychee.system.api.entity.LycheePermission;
import org.lychee.system.mapper.LycheePermissionMapper;
import org.lychee.system.service.ILycheePermissionService;
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
public class LycheePermissionServiceImpl extends ServiceImpl<LycheePermissionMapper, LycheePermission> implements ILycheePermissionService {

    @Override
    public List<LycheePermission> selectPermissionByRoleId(List<Long>  roleIds) {
        return baseMapper.selectPermissionByRoleId(roleIds);
    }

    @Override
    public LycheePermission selectPermissionByRoleIdOne(Long roleId) {
        return baseMapper.selectPermissionByRoleIdOne(roleId);
    }

    @Override
    public void insertByRelationSurface(Long permissionId, Long roleId) {
        baseMapper.insertByRelationSurface(permissionId,roleId);
    }

    @Override
    public void deleteByRelationSurface(Long permissionId) {
        baseMapper.deleteByRelationSurface(permissionId);
    }
}
