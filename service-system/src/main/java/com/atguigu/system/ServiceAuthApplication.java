package com.atguigu.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @author zzj
 * @date 2022/9/19
 */

@SpringBootApplication
@EnableSwagger2WebMvc
//@MapperScan(basePackages = "com.atguigu.system.mapper")
public class ServiceAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceAuthApplication.class,args);
    }
}

