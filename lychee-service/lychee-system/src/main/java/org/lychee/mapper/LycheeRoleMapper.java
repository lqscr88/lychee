package org.lychee.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.lychee.entity.LycheeRole;
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
public interface LycheeRoleMapper extends BaseMapper<LycheeRole> {

    List<LycheeRole> selectRoleByUserId(@Param("userId") Long userId);

    void deleteByRelationSurface(@Param("roleId") Long roleId);
}
