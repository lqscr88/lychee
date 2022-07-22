package org.lychee;


import org.lychee.config.LycheeApplication;
import org.lychee.constant.AppConstant;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApp {


    public static void main(String[] args) {
        LycheeApplication.run(AppConstant.APPLICATION_GATEWAY_NAME, GatewayApp.class, args);
    }
}
