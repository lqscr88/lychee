package org.lychee;

import org.lychee.config.LycheeApplication;
import org.lychee.constant.AppConstant;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class AuthApp {

    public static void main(String[] args) {
        LycheeApplication.run(AppConstant.APPLICATION_AUTH_NAME, AuthApp.class, args);
    }

}
