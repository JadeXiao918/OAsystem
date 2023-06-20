package com.ao.auth.service;


import com.atguigu.model.system.SysMenu;
import com.atguigu.vo.system.AssginMenuVo;
import com.atguigu.vo.system.RouterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *

 */
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenu> findNodes();
    //删除菜单
    void removeMenuById(Long id);

    List<SysMenu> findMenuByRoleId(Long roleId);
    //角色分配菜单
    void doAssign(AssginMenuVo assginMenuVo);
    //根据用户id获取用户可以操作菜单列表
    List<RouterVo> findUserMenuListByUserId(Long userId);
    //根据用户id获取用户可以操作按钮列表
    List<String> findUserPermsByUserId(Long userId);
}
