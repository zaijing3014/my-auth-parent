package com.atguigu.system.config;

import com.atguigu.system.custom.CustomMd5PasswordEncoder;
import com.atguigu.system.filter.TokenLoginFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.annotation.Resource;

/**
 * @author zzj
 * @date 2022/9/23
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private CustomMd5PasswordEncoder customMd5PasswordEncoder;

    @Resource
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        设置UserDetailsService对象和密码加密器
        auth.userDetailsService(userDetailsService).passwordEncoder(customMd5PasswordEncoder);
//        auth.userDetailsService(userDetailsService).passwordEncoder(customMd5PasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //关闭跨域请求伪造（csrf）功能
        http.csrf().disable();
        //设置那些请求不需要认证，那些请求需要认证
        http.authorizeRequests().antMatchers("/admin/system/index/login","/admin/system/index/info","/admin/system/index/logout").permitAll().anyRequest().authenticated();
        //添加自定义的过滤器
        http.addFilter(new TokenLoginFilter(authenticationManager()));
    }

    /**
     * 配置哪些请求不拦截
     * 排除swagger相关请求
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/favicon.ico","/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**", "/doc.html");
    }
}































