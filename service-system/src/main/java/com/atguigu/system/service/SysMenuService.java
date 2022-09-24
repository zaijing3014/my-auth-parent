package com.atguigu.system.service;

import com.atguigu.model.system.SysMenu;
import com.atguigu.model.vo.AssginMenuVo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zzj
 * @date 2022/9/21
 */
@Repository
public interface SysMenuService extends IService<SysMenu> {
    List<SysMenu> findAll();

    void deleteById(Long id);

    List<SysMenu> getMenuList(Long roleId);

    void assignMenu(AssginMenuVo assginMenuVo);
}
