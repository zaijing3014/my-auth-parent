package com.atguigu.system.service.impl;

import com.atguigu.common.helper.MenuHelper;
import com.atguigu.common.helper.RouterHelper;
import com.atguigu.model.system.SysMenu;
import com.atguigu.model.system.SysUser;
import com.atguigu.model.vo.LoginVo;
import com.atguigu.model.vo.RouterVo;
import com.atguigu.model.vo.SysUserQueryVo;
import com.atguigu.system.mapper.SysMenuMapper;
import com.atguigu.system.mapper.SysUserMapper;
import com.atguigu.system.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService
 * @Autowired
 *     private SysRoleMapper sysRoleMapper;
 *     @Override
 *     public IPage<SysRole> selectPage(Page<SysRole> pageParam, SysRoleQueryVo roleQueryVo) {
 *         return sysRoleMapper.selectPage(pageParam, roleQueryVo);
 *     }
 * @author zzj
 * @date 2022/9/20
 */
@Transactional
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Override
    public IPage<SysUser> selectPage(Page<SysUser> pageParam, SysUserQueryVo userQueryVo) {
       return sysUserMapper.selectPage(pageParam, userQueryVo);
    }

    @Override
    public SysUser getByUserName(LoginVo loginVo) {
        SysUser sysUser = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("username", loginVo.getUsername()));
        return sysUser;
    }

    @Override
    public Map<String, Object> getUserInfoByUserId(Long userId) {
//        Map<String,Object> map = new HashMap<>();
//        map.put("roles","[超级管理员]");
//        map.put("introduction","我是超级管理员");
//        map.put("avatar","https://bpic.588ku.com/ad_diversion/22/08/19/22adee644335a4bbb19915b24b041404.gif");
//        map.put("name","Admin");
        SysUser sysUser = sysUserMapper.selectById(userId);
        List<SysMenu> menuList = null;
        //根据用户id判断该用户是否是系统管理员
        if (userId == 1L){
            menuList = sysMenuMapper.selectList(new QueryWrapper<SysMenu>().eq("status", 1));
        } else {
            menuList = sysMenuMapper.selectUserMenuByUserId(userId);
        }
        //将权限菜单转换为菜单树
        List<SysMenu> sysMenuListTree = MenuHelper.buildTree(menuList);
        //将菜单树转换为路由
        List<RouterVo> routers = RouterHelper.buildRouters(sysMenuListTree);
        List<String> btnPerList = new ArrayList<>();
        for (SysMenu sysMenu : menuList) {
            btnPerList.add(sysMenu.getPerms());
        }
        //创建返回的Map
        Map<String, Object> map = new HashMap<>();
        //向map中保存用户的角色
        map.put("roles",new ArrayList<>());
        //向map中保存用户名
        map.put("name",sysUser.getUsername());
        //向map中存放用户的头像
        map.put("avatar","https://bpic.588ku.com/ad_diversion/22/08/19/22adee644335a4bbb19915b24b041404.gif");
        //向map中存放路由地址
        map.put("routers",routers);
        //向map中存在用户的按钮权限标识符
        map.put("buttons",btnPerList);
        return map;
    }

    @Override
    public SysUser getUserByUserName(String username) {
        SysUser sysUser = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("username",username));
        return sysUser;
    }

    @Override
    public void updateStatus(Long userId, Integer status) {
        SysUser sysUser = sysUserMapper.selectById(userId);
        sysUser.setStatus(status);
        sysUser.setUpdateTime(null);
        sysUserMapper.updateById(sysUser);
    }


}
