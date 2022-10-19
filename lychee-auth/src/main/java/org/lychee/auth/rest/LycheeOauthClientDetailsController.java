package org.lychee.auth.rest;


import io.swagger.annotations.ApiOperation;
import org.lychee.common.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lychee
 * @since 2022-08-03
 */
@RestController
@RequestMapping("client")
public class LycheeOauthClientDetailsController {


    @GetMapping("cs")
    @ApiOperation(value = "测试")
    public Result<Object> info() {
        return Result.success("cs");
    }

}
