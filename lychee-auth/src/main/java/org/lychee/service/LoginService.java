package org.lychee.service;


import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lychee
 * @since 2022-01-05
 */
public interface LoginService {

    String getToken(String account,String password);

}
