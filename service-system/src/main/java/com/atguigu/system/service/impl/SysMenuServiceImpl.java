package com.atguigu.system.service.impl;

import com.atguigu.common.helper.MenuHelper;
import com.atguigu.common.result.ResultCodeEnum;
import com.atguigu.model.system.SysMenu;
import com.atguigu.model.system.SysRoleMenu;
import com.atguigu.model.vo.AssginMenuVo;
import com.atguigu.system.handler.GuiguException;
import com.atguigu.system.mapper.SysMenuMapper;
import com.atguigu.system.mapper.SysRoleMenuMapper;
import com.atguigu.system.service.SysMenuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zzj
 * @date 2022/9/21
 */
@Service
@Transactional
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<SysMenu> findAll() {
        List<SysMenu> menuList = sysMenuMapper.selectList(null);
        return menuList;
    }

    @Override
    public void deleteById(Long id) {
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",id);
        if (sysMenuMapper.selectCount(queryWrapper)>0){
            throw new GuiguException(ResultCodeEnum.NODE_ERROR);
        } else {
            sysMenuMapper.deleteById(id);
        }
    }

    @Override
    public List<SysMenu> getMenuList(Long roleId) {
        List<SysMenu> menuList = sysMenuMapper.selectList(null);
        List<SysRoleMenu> roleMenuList = sysRoleMenuMapper.selectList(new QueryWrapper<SysRoleMenu>().eq("role_id", roleId));
        List<Long> listMenuIds = new ArrayList<>();
        for (SysRoleMenu sysRoleMenu : roleMenuList) {
            listMenuIds.add(sysRoleMenu.getMenuId());
        }
        for (SysMenu menu : menuList) {
            if (listMenuIds.contains(menu.getId())){
                menu.setSelect(true);
            }
        }
        List<SysMenu> menuListTree = MenuHelper.buildTree(menuList);
        //?????????????????????????????????
        return menuListTree;
    }

    @Override
    public void assignMenu(AssginMenuVo assginMenuVo) {
        //????????????????????????
        sysRoleMenuMapper.delete(new QueryWrapper<SysRoleMenu>().eq("role_id",assginMenuVo.getRoleId()));
        //??????????????????????????????id
        for (Long menuId : assginMenuVo.getMenuIdList()) {
            if (menuId != null){
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setMenuId(menuId);
                sysRoleMenu.setRoleId(assginMenuVo.getRoleId());
                sysRoleMenuMapper.insert(sysRoleMenu);
            }
        }
    }
}























































