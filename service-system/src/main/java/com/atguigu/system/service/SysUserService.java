package com.atguigu.system.service;

import com.atguigu.model.system.SysUser;
import com.atguigu.model.vo.LoginVo;
import com.atguigu.model.vo.SysUserQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author zzj
 * @date 2022/9/20
 */
@Repository
public interface SysUserService extends IService<SysUser> {

    void updateStatus(Long userId, Integer status);

    IPage<SysUser> selectPage(Page<SysUser> pageParam, SysUserQueryVo sysUserQueryVo);

    SysUser getByUserName(LoginVo loginVo);

    Map<String, Object> getUserInfoByUserId(Long userId);

    SysUser getUserByUserName(String username);
}
