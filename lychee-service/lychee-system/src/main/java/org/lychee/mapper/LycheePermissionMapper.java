package org.lychee.mapper;

import org.apache.ibatis.annotations.Param;
import org.lychee.entity.LycheePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lychee
 * @since 2022-01-05
 */
public interface LycheePermissionMapper extends BaseMapper<LycheePermission> {

    List<LycheePermission> selectPermissionByRoleId(@Param("roleIds")List<Long>  roleIds);

    LycheePermission selectPermissionByRoleIdOne(@Param("roleId")Long roleId);

    void insertByRelationSurface(@Param("permissionId")Long permissionId,@Param("roleId") Long roleId);

    void deleteByRelationSurface(@Param("permissionId")Long permissionId);
}
