package org.lychee.mybaties.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("org.lychee.mapper")
@EnableTransactionManagement
@Configuration
public class MybatiesPlusConfig {

}
