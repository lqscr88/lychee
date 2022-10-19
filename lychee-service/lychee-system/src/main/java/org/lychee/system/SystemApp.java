package org.lychee.system;

import org.lychee.common.constant.AppConstant;
import org.lychee.core.boot.LycheeApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class SystemApp {

    public static void main(String[] args) {
        LycheeApplication.run(AppConstant.APPLICATION_SYSTEM_NAME, SystemApp.class, args);
    }

}
