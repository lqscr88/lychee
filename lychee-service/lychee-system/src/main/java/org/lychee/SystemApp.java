package org.lychee;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = "org.lychee.feign")
public class SystemApp {

    public static void main(String[] args) {
        SpringApplication.run(SystemApp.class, args);
    }

}
