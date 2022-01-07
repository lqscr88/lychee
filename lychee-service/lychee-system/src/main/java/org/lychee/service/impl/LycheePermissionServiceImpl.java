package org.lychee.service.impl;

import org.lychee.entity.LycheePermission;
import org.lychee.mapper.LycheePermissionMapper;
import org.lychee.service.ILycheePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
}
