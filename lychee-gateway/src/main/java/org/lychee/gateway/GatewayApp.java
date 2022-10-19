package org.lychee.gateway;


import org.lychee.common.constant.AppConstant;
import org.lychee.core.boot.LycheeApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApp {


    public static void main(String[] args) {
        LycheeApplication.run(AppConstant.APPLICATION_GATEWAY_NAME, GatewayApp.class, args);
    }
}
