package com.atguigu.system.custom;

import com.atguigu.model.system.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author zzj
 * @date 2022/9/23
 */
public class CustomUser extends User {
    private SysUser sysUser;

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public CustomUser(SysUser sysUser, Collection<? extends GrantedAuthority> authorities) {
        super(sysUser.getUsername(),sysUser.getPassword(), authorities);
        this.sysUser = sysUser;
    }
}
