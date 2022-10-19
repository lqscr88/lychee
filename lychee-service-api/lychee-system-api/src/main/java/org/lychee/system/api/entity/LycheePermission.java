package org.lychee.system.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 *
 * </p>
 *
 * @author lychee
 * @since 2022-01-05
 */
@TableName("lychee_permission")
@ApiModel(value = "LycheePermission对象", description = "")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LycheePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String permissionPath;

    private LocalDate createTime;

    private String permissionName;

    private Long permissionParentId;
}
