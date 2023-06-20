package com.ao.auth;

import com.ao.auth.mapper.SysRoleMapper;
import com.atguigu.model.system.SysRole;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class TestMpDemo1 {
    @Autowired
    private SysRoleMapper mapper;
    @Test
    public void getAll(){
        List<SysRole> list = mapper.selectList(null);
        System.out.println(list);
    }
    @Test
    public void add(){
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("角色管理员");
        sysRole.setRoleCode("role");
        sysRole.setDescription("角色管理员");
        int insert = mapper.insert(sysRole);
        System.out.println(insert);
        System.out.println(sysRole);
    }
    @Test
    public void update(){
        SysRole sysRole = mapper.selectById(9);
        sysRole.setRoleName("atguigu角色管理员");
        int rows = mapper.updateById(sysRole);
        System.out.println(rows);
        System.out.println(sysRole);
    }
    @Test
    public void delete(){
        int rows = mapper.deleteById(9);
    }
    @Test
    public void testDeleteBathIds(){
        int results = mapper.deleteBatchIds(Arrays.asList(1, 2));
        System.out.println(results);
    }
    @Test
    public void testQuery1(){
        //创建QueryWrapper对象，调用方法封装条件
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.eq("role_name","总经理");
        List<SysRole> list = mapper.selectList(wrapper);
        System.out.println(list);
    }
    @Test
    public void testQuery2(){
        //创建QueryWrapper对象，调用方法封装条件
        LambdaQueryWrapper<SysRole>  wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getRoleName,"总经理");
        List<SysRole> list = mapper.selectList(wrapper);
        System.out.println(list);
    }
}
