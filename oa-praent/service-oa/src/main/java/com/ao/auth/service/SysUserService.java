package com.ao.auth.service;


import com.atguigu.model.system.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *

 */
public interface SysUserService extends IService<SysUser> {
    //更新状态
    void updateStatus(Long id, Integer status);
    //根据用户名称进行查询
    SysUser getUserByUserName(String username);

    Map<String, Object> getCurrentUser();
}
