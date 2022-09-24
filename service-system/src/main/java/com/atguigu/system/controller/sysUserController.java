package com.atguigu.system.controller;

import com.atguigu.common.result.Result;
import com.atguigu.model.system.SysUser;
import com.atguigu.model.vo.SysUserQueryVo;
import com.atguigu.system.service.SysUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zzj
 * @date 2022/9/20
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/admin/system/sysUser")
public class sysUserController {
    @Autowired
    private SysUserService sysUserService;

    @ApiOperation(value = "用户查询")
    @GetMapping("/{page}/{limit}")
    public Result index(
            @PathVariable Long page,
            @PathVariable Long limit,
            SysUserQueryVo sysUserQueryVo
            ){
        Page<SysUser> pageParam = new Page<>(page, limit);
        IPage<SysUser> sysUserIPage = sysUserService.selectPage(pageParam, sysUserQueryVo);
        return Result.ok(sysUserIPage);
    }

    @ApiOperation("获取用户")
    @GetMapping("/getById/{id}")
    public Result getById(@PathVariable Long id){
        SysUser sysUser = sysUserService.getById(id);
        return Result.ok(sysUser);
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public Result save(@RequestBody SysUser sysUser){
        sysUserService.save(sysUser);
        return Result.ok();
    }

    @ApiOperation("更新用户状态")
    @GetMapping("/updateStatus/{userId}/{status}")
    public Result updateStatus(@PathVariable Long userId , @PathVariable Integer status){
       sysUserService.updateStatus(userId,status);
       return Result.ok();
    }

    @ApiOperation("修改")
    @PutMapping("/update")
    public Result update(@RequestBody SysUser sysUser){
        sysUserService.updateById(sysUser);
        return Result.ok();
    }
    @ApiOperation("删除")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id){
        sysUserService.removeById(id);
        return Result.ok();
    }
}
