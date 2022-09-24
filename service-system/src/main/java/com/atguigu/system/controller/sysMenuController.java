package com.atguigu.system.controller;

import com.atguigu.common.helper.MenuHelper;
import com.atguigu.common.result.Result;
import com.atguigu.model.system.SysMenu;
import com.atguigu.model.vo.AssginMenuVo;
import com.atguigu.system.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zzj
 * @date 2022/9/20
 */
@Api(value = "菜单管理")
@RestController
@RequestMapping("/admin/system/sysMenu")
public class sysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    @ApiOperation("查询所有菜单")
    @GetMapping("/findMenuNodes")
    public Result findMenuNodes(){
         List<SysMenu>  menuList =  sysMenuService.findAll();
        List<SysMenu> menuTree = MenuHelper.buildTree(menuList);
        return Result.ok(menuTree);
    }

    @ApiOperation("通过id删除节点")
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Long id){
        sysMenuService.deleteById(id);
        return Result.ok();
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public Result save(@RequestBody SysMenu sysMenu){
        sysMenuService.save(sysMenu);
        return Result.ok();
    }

    @ApiOperation("根据id查询")
    @GetMapping("/getById/{id}")
    public Result getById(@PathVariable Long id){
        SysMenu sysMenu = sysMenuService.getById(id);
        return Result.ok(sysMenu);
    }

    @ApiOperation("更新")
    @PutMapping("/update")
    public Result update(@RequestBody SysMenu sysMenu){
        sysMenuService.updateById(sysMenu);
        return Result.ok();
    }

    @ApiOperation("查询角色菜单")
    @GetMapping("/getRoleMenuList/{roleId}")
    public Result getRoleMenuList(@PathVariable Long roleId){
        List<SysMenu> roleMenuList = sysMenuService.getMenuList(roleId);
        return Result.ok(roleMenuList);
    }

    @ApiOperation("保存权限列表")
    @PostMapping("/assignMenu")
    public Result assignMenu(@RequestBody AssginMenuVo assginMenuVo){
        sysMenuService.assignMenu(assginMenuVo);
        return Result.ok();
    }
}
