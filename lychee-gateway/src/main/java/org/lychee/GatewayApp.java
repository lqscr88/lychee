package org.lychee;


import com.alibaba.cloud.nacos.ribbon.NacosServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCaching
public class GatewayApp {


    public static void main(String[] args) {
        SpringApplication.run(GatewayApp.class,args);
    }
}
