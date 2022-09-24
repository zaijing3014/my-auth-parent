package com.atguigu.system.service.impl;

import com.atguigu.model.system.SysRole;
import com.atguigu.model.system.SysUserRole;
import com.atguigu.model.vo.AssginRoleVo;
import com.atguigu.model.vo.SysRoleQueryVo;
import com.atguigu.system.mapper.SysRoleMapper;
import com.atguigu.system.mapper.SysUserRoleMapper;
import com.atguigu.system.service.SysRoleService;
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
 * @author zzj
 * @date 2022/9/19
 */
@Transactional
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Override
    public IPage<SysRole> selectPage(Page<SysRole> pageParam, SysRoleQueryVo roleQueryVo) {
        return sysRoleMapper.selectPage(pageParam, roleQueryVo);
    }

    @Override
    public Map<String, Object> getRolesByUserId(Long userId) {
        List<SysRole> sysRoles = sysRoleMapper.selectList(null);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("allRoles",sysRoles);

        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        System.out.println("queryWrapper = " + queryWrapper);
        List<SysUserRole> userRoles = sysUserRoleMapper.selectList(queryWrapper);
        ArrayList<Long> userRoleIds = new ArrayList<>();
        for (SysUserRole userRole : userRoles) {
            userRoleIds.add(userRole.getRoleId());
        }
        hashMap.put("userRoleIds",userRoleIds);
        return hashMap;
    }

    @Override
    public void doAssign(AssginRoleVo assginRoleVo) {
//        根据用户id查询用户分配权限并删除
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        Long userId = assginRoleVo.getUserId();
        System.out.println("userId = " + userId);
        queryWrapper.eq("user_id",assginRoleVo.getUserId());
        sysUserRoleMapper.delete(queryWrapper);

//        添加
        List<Long> roleIdList = assginRoleVo.getRoleIdList();
        for (Long roleId : roleIdList) {
            if (roleId != null){
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setRoleId(roleId);
                sysUserRole.setUserId(assginRoleVo.getUserId());
                sysUserRoleMapper.insert(sysUserRole);
            }
        }
    }
}
