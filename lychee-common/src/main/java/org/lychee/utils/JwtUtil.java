package org.lychee.utils;

import cn.hutool.jwt.JWT;

public class JwtUtil {


    public static JWT createToken(){
        return JWT.create();
    }

    public static JWT jxToken(String token){
        return JWT.of(token);
    }
}
