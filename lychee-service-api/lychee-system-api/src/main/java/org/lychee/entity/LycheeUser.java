package org.lychee.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

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
@TableName("lychee_user")
@ApiModel(value = "LycheeUser对象", description = "")
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LycheeUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String username;

    private String password;

    private LocalDate createTime;

    private Integer status;

}
