package com.ao.auth.mapper;

import com.atguigu.model.system.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import javax.management.relation.Role;
@Repository
public interface SysRoleMapper extends BaseMapper<SysRole> {
}
