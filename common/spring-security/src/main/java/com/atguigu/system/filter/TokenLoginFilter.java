package com.atguigu.system.filter;

import com.atguigu.common.helper.JwtHelper;
import com.atguigu.common.result.Result;
import com.atguigu.common.result.ResultCodeEnum;
import com.atguigu.common.util.ResponseUtil;
import com.atguigu.model.system.SysUser;
import com.atguigu.model.vo.LoginVo;
import com.atguigu.system.custom.CustomUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zzj
 * @date 2022/9/23
 */
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    public TokenLoginFilter(AuthenticationManager authenticationManager) {
        this.setAuthenticationManager(authenticationManager);
        //指定登录接口及提交方式，可以指定任意路径
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/admin/system/index/login","POST"));

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
//            获取loginVo对象
            LoginVo loginVo = new ObjectMapper().readValue(request.getInputStream(), LoginVo.class);
//            未认证的Authentication对象
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(loginVo.getUsername(), loginVo.getPassword());
//            调用认证管理器中的去认证
            return this.getAuthenticationManager().authenticate(authRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //认证成功的方法
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //获取认证之后的对象
        CustomUser customUser = (CustomUser) authResult.getPrincipal();
        //获取CustomUser中的SysUser对象
        SysUser sysUser = customUser.getSysUser();
        //通过JWTHelper生成Token
        String token = JwtHelper.createToken(sysUser.getId(), sysUser.getUsername());
        Map map = new HashMap<>();
        map.put("token",token);
        //通过ResponseUtil工具类将token响应到前端
        ResponseUtil.out(response, Result.ok(map));
    }
    //认证失败的方法
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        ResponseUtil.out(response,Result.build(null, ResultCodeEnum.LOGIN_MOBLE_ERROR));
    }
}



























