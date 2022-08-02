package org.lychee.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.lychee.constant.SecurityConstant;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sun.security.util.SecurityConstants;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * JWT工具类
 *
 * @author haoxr
 * @date 2022/2/5
 */
@Slf4j
public class JwtUtils {

    @SneakyThrows
    public static JSONObject getJwtPayload() {
        JSONObject jsonObject = null;
        String payload = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader(SecurityConstant.JWT_PAYLOAD_KEY);
        if (StrUtil.isNotBlank(payload)) {
            jsonObject = JSONUtil.parseObj(URLDecoder.decode(payload, StandardCharsets.UTF_8.name()));
        }
        return jsonObject;
    }
}
