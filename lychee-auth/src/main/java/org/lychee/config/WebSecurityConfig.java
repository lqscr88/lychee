package org.lychee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.annotation.Resource;

/**
 * @Author qt
 * @Date 2021/3/25
 * @Description spring-security权限管理的核心配置
 */
@Configuration
@EnableWebSecurity//开启Spring Security的功能
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    //http请求拦截配置
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();//开启运行iframe嵌套页面

        http//1、配置权限认证
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .anyRequest() //任何其它请求
                .authenticated() //都需要身份认证
                .and()
                //2、登录配置表单认证方式
                .formLogin()
//                .loginPage("/login")//自定义登录页面的url
//                .usernameParameter("username")//设置登录账号参数，与表单参数一致
//                .passwordParameter("password")//设置登录密码参数，与表单参数一致
                .loginProcessingUrl("/login")//配置默认登录入口
                .defaultSuccessUrl("/index")//登录成功后默认的跳转页面路径
                .failureUrl("/login?error=true")
                .and()
                //3、注销
                .logout()
                .logoutUrl("/logout")
                .permitAll()
                .and()
                //4、session管理
                .sessionManagement()
                .invalidSessionUrl("/login") //失效后跳转到登陆页面
                .and()
                //5、禁用跨站csrf攻击防御
                .csrf()
                .disable();
    }

    /**
     * 指定加密方式
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        // 使用BCrypt加密密码
        return new BCryptPasswordEncoder();
    }
}