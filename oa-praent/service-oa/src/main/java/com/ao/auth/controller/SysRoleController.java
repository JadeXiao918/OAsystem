package com.ao.auth.controller;

import com.ao.auth.service.SysRoleService;
import com.ao.common.config.exception.AoException;
import com.ao.common.result.Result;
import com.atguigu.model.system.SysRole;
import com.atguigu.vo.system.AssginRoleVo;
import com.atguigu.vo.system.SysRoleQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "角色管理接口")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {
    @Autowired
    private SysRoleService service;
    //查询所有角色 和 当前用户所属角色
    @ApiOperation("获取角色")
    @GetMapping("/toAssign/{userId}")
    public Result toAssign(@PathVariable Long userId){
        Map<String,Object> map = service.findRoleDataByUserId(userId);
        return  Result.ok(map);
    }
    //为用户分配角色
    @ApiOperation("为用户分配角色")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginRoleVo assginRoleVo){
        service.doAssign(assginRoleVo);
        return Result.ok();
    }
    //查询所有角色
    /*@GetMapping("/findAll")
    public List<SysRole> findAll(){
        List<SysRole> list = service.list();
        return list;
    }*/
    @ApiOperation("查询所有角色")
    @GetMapping("/findAll")
    public Result findAll(){
        List<SysRole> list = service.list();
        /*//模拟异常效果
        try{
            int i = 10/0;
        } catch (Exception e){
            throw new AoException(20001,"执行了自定义异常处理");
        }*/
        return Result.ok(list);
    }
    //条件分页查询
    //page表示当前页 limit表示每页显示记录数
    //SysRoleQueryVo表示条件对象
    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @ApiOperation("条件分页查询")
    @GetMapping("{page}/{limit}")
    public Result pageQueryRole(@PathVariable Long page,
                                @PathVariable Long limit, SysRoleQueryVo sysRoleQueryVo){
        //调用service的方法实现
        //1 创建Page对象，传递分页相关参数
        Page<SysRole> pageParam = new Page<>(page,limit);
        //2 封装条件，判断条件是否为空，不为空进行封装
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        String roleName = sysRoleQueryVo.getRoleName();
        if (!StringUtils.isEmpty(roleName)){
           wrapper.like(SysRole::getRoleName,roleName);
        }
        //3 调用方法实现
        IPage<SysRole> pageModel = service.page(pageParam, wrapper);
        return Result.ok(pageModel);
    }
    //添加角色
    @PreAuthorize("hasAuthority('bnt.sysRole.add')")
    @ApiOperation("添加角色")
    @PostMapping("save")
    public Result save(@RequestBody SysRole role){
        //调用service方法
        boolean is_success = service.save(role);
        if (is_success){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }
    //修改角色—根据id查询
    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @ApiOperation("根据id查询")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id){
        SysRole sysRole = service.getById(id);
        return Result.ok(sysRole);
    }
    //修改角色—最终修改
    @PreAuthorize("hasAuthority('bnt.sysRole.update')")
    @ApiOperation("修改角色")
    @PutMapping("update")
    public Result update(@RequestBody SysRole role){
        //调用service方法
        boolean is_success = service.updateById(role);
        if (is_success){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }
    //根据id删除
    @PreAuthorize("hasAuthority('bnt.sysRole.remove')")
    @ApiOperation("根据id删除")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id){
        boolean is_success = service.removeById(id);
        if (is_success){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }
    //批量删除
    @PreAuthorize("hasAuthority('bnt.sysRole.remove')")
    @ApiOperation("批量删除")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList){
        boolean is_success = service.removeByIds(idList);
        if (is_success){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

}
