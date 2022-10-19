package org.lychee.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.lychee.system.api.entity.LycheeRole;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lychee
 * @since 2022-01-05
 */
public interface LycheeRoleMapper extends BaseMapper<LycheeRole> {

    List<LycheeRole> selectRoleByUserId(@Param("userId") Long userId);

    void deleteByRelationSurface(@Param("roleId") Long roleId);
}
