package org.lychee.enums;

import lombok.Getter;
import org.lychee.base.IBaseEnum;

/**
 * 认证身份标识枚举
 *
 * @author haoxr
 * @date 2021/10/4
 */
public enum AuthenticationIdentityEnum implements IBaseEnum<String> {

    USERNAME("username", "用户名"),
    MOBILE("mobile", "手机号"),
    OPENID("openId", "开放式认证系统唯一身份标识");

    @Getter
    private String value;

    @Getter
    private String label;

    AuthenticationIdentityEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }
}
