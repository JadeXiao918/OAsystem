package com.ao.process.controller.api;

import com.ao.auth.service.SysUserService;
import com.ao.common.result.Result;
import com.ao.process.service.OaProcessService;
import com.ao.process.service.OaProcessTemplateService;
import com.ao.process.service.OaProcessTypeService;
import com.atguigu.model.process.ProcessTemplate;
import com.atguigu.model.process.ProcessType;
import com.atguigu.vo.process.ApprovalVo;
import com.atguigu.vo.process.ProcessFormVo;
import com.atguigu.vo.process.ProcessVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "审批流管理")
@RestController
@RequestMapping(value="/admin/process")
@CrossOrigin  //跨域
public class ProcessController {
        @Autowired
        private OaProcessTypeService processTypeService;
        @Autowired
        private OaProcessTemplateService processTemplateService;
        @Autowired
        private OaProcessService processService;
        @Autowired
        private SysUserService sysUserService;
        @ApiOperation(value = "启动流程")
        @PostMapping("/startUp")
        public Result start(@RequestBody ProcessFormVo processFormVo) {
        processService.startUp(processFormVo);
        return Result.ok();
    }
        @ApiOperation(value = "获取全部审批分类及模板")
        @GetMapping("findProcessType")
        public Result findProcessType() {
        List<ProcessType> list = processTypeService.findProcessType();
        return Result.ok(list);
    }
        //获取审批模板数据
        @ApiOperation(value = "获取审批模板")
        @GetMapping("getProcessTemplate/{processTemplateId}")
        public Result get(@PathVariable Long processTemplateId) {
                ProcessTemplate processTemplate = processTemplateService.getById(processTemplateId);
                return Result.ok(processTemplate);
        }
        //查看审批详情信息
        @ApiOperation(value = "获取审批详情")
        @GetMapping("show/{id}")
        public Result show(@PathVariable Long id) {
            Map<String,Object> map = processService.show(id);
            return Result.ok(map);
        }
        //审批
        @ApiOperation(value = "审批")
        @PostMapping("approve")
        public Result approve(@RequestBody ApprovalVo approvalVo) {
        processService.approve(approvalVo);
        return Result.ok();
    }
        @ApiOperation(value = "已处理")
        @GetMapping("/findProcessed/{page}/{limit}")
        public Result findProcessed(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit) {
        Page<Process> pageParam = new Page<>(page, limit);
        IPage<ProcessVo> pageModel = processService.findProcessed(pageParam);
        return Result.ok(pageModel);
    }
        @ApiOperation(value = "已发起")
        @GetMapping("/findStarted/{page}/{limit}")
        public Result findStarted(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit) {
        Page<ProcessVo> pageParam = new Page<>(page, limit);
        IPage<ProcessVo> pageModel = processService.findStarted(pageParam);
        return Result.ok(pageModel);
    }
        @ApiOperation(value = "获取当前用户基本信息")
        @GetMapping("getCurrentUser")
        public Result getCurrentUser() {
        Map<String,Object> map = sysUserService.getCurrentUser();
        return Result.ok(map);
    }
}
