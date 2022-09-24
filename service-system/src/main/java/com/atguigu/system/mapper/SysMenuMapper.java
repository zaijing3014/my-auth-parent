package com.atguigu.system.mapper;

import com.atguigu.model.system.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zzj
 * @date 2022/9/21
 */
@Repository
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> selectUserMenuByUserId(Long userId);
}
