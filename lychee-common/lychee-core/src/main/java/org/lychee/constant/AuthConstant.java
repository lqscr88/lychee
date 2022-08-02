package org.lychee.constant;

/**
 * 系统常量
 *
 * @author Chill
 */
public interface AuthConstant {
	/**
	 * 默认为空消息
	 */
	String USERNAME_PASSWORD_UNFIND = "用户名或密码错误";
	/**
	 * 默认成功消息
	 */
	String JWT_KEY = "lychee";

	String MISSING_TOKEN="缺失令牌,鉴权失败";

	String ILLEGAL_TOKEN="非法令牌";

	String TOKEN_EXPIRED="令牌已过期";

	String NO_AUTHORITY="没权限";

}
