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
//        map.put("roles","[???????????????]");
//        map.put("introduction","?????????????????????");
//        map.put("avatar","https://bpic.588ku.com/ad_diversion/22/08/19/22adee644335a4bbb19915b24b041404.gif");
//        map.put("name","Admin");
        SysUser sysUser = sysUserMapper.selectById(userId);
        List<SysMenu> menuList = null;
        //????????????id???????????????????????????????????????
        if (userId == 1L){
            menuList = sysMenuMapper.selectList(new QueryWrapper<SysMenu>().eq("status", 1));
        } else {
            menuList = sysMenuMapper.selectUserMenuByUserId(userId);
        }
        //?????????????????????????????????
        List<SysMenu> sysMenuListTree = MenuHelper.buildTree(menuList);
        //???????????????????????????
        List<RouterVo> routers = RouterHelper.buildRouters(sysMenuListTree);
        List<String> btnPerList = new ArrayList<>();
        for (SysMenu sysMenu : menuList) {
            btnPerList.add(sysMenu.getPerms());
        }
        //???????????????Map
        Map<String, Object> map = new HashMap<>();
        //???map????????????????????????
        map.put("roles",new ArrayList<>());
        //???map??????????????????
        map.put("name",sysUser.getUsername());
        //???map????????????????????????
        map.put("avatar","https://bpic.588ku.com/ad_diversion/22/08/19/22adee644335a4bbb19915b24b041404.gif");
        //???map?????????????????????
        map.put("routers",routers);
        //???map???????????????????????????????????????
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
