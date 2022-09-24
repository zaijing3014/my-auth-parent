package com.atguigu.system.controller;

import com.atguigu.common.helper.JwtHelper;
import com.atguigu.common.result.Result;
import com.atguigu.common.result.ResultCodeEnum;
import com.atguigu.common.util.MD5;
import com.atguigu.model.system.SysUser;
import com.atguigu.model.vo.LoginVo;
import com.atguigu.system.handler.GuiguException;
import com.atguigu.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zzj
 * @date 2022/9/20
 */
@Api(tags = "后台登录管理")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {
    @Autowired
    private SysUserService sysUserService;

    @ApiOperation("登录")
    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo){
        SysUser sysUser =  sysUserService.getByUserName(loginVo);
        if (sysUser == null){
            throw new GuiguException(ResultCodeEnum.ACCOUNT_ERROR);
        }
        if (!MD5.encrypt(loginVo.getPassword()).equals(sysUser.getPassword())){
            throw new GuiguException(ResultCodeEnum.PASSWORD_ERROR);
        }
        if(sysUser.getStatus().intValue() == 0) {
            throw new GuiguException(ResultCodeEnum.ACCOUNT_STOP);
        }
        Map<String,Object> map = new HashMap<>();

        map.put("token", JwtHelper.createToken(sysUser.getId(),sysUser.getUsername()));
        return Result.ok(map);
    }

    @ApiOperation("获取用户信息")
    @GetMapping("/info")
    public Result info(HttpServletRequest request){
        Long userId = JwtHelper.getUserId(request.getHeader("token"));
        Map<String,Object> map =  sysUserService.getUserInfoByUserId(userId);
//        Map<String,Object> map = new HashMap<>();
//        map.put("roles","[超级管理员]");
//        map.put("introduction","我是超级管理员");
//        map.put("avatar","https://bpic.588ku.com/ad_diversion/22/08/19/22adee644335a4bbb19915b24b041404.gif");
//        map.put("name","Admin");
        return Result.ok(map);
    }

    @ApiOperation("等处")
    @PostMapping("/logout")
    public Result logout(){
        return Result.ok();
    }
}
