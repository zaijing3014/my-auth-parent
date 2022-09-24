package com.atguigu.system.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author zzj
 * @date 2022/9/23
 */
@SpringBootConfiguration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
}
