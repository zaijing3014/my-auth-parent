package com.atguigu.system;

import com.atguigu.model.system.SysRole;
import com.atguigu.system.service.SysRoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author zzj
 * @date 2022/9/19
 */
@SpringBootTest
public class SysRoleServiceTest {
    @Autowired
    private SysRoleService sysRoleService;

    @Test
    public void testSelectList(){
        List<SysRole> list = sysRoleService.list();
        for (SysRole sysRole : list) {
            System.out.println("sysRole service = " + sysRole);
        }
    }

}
