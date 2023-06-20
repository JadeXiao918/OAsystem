package com.ao.process.service.impl;

import com.ao.auth.service.SysUserService;
import com.ao.security.custom.LoginUserInfoHelper;
import com.atguigu.model.process.ProcessRecord;
import com.ao.process.mapper.OaProcessRecordMapper;
import com.ao.process.service.OaProcessRecordService;
import com.atguigu.model.system.SysUser;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 审批记录 服务实现类
 * </p>

 */
@Service
public class OaProcessRecordServiceImpl extends ServiceImpl<OaProcessRecordMapper, ProcessRecord> implements OaProcessRecordService {
    @Autowired
    private SysUserService sysUserService;
    @Override
    public void record(Long processId, Integer status, String description) {
        Long userId = LoginUserInfoHelper.getUserId();
        SysUser sysUser = sysUserService.getById(userId);
        ProcessRecord processRecord = new ProcessRecord();
        processRecord.setProcessId(processId);
        processRecord.setStatus(status);
        processRecord.setDescription(description);
        processRecord.setOperateUser(sysUser.getUsername());
        processRecord.setOperateUserId(userId);
        baseMapper.insert(processRecord);
    }
}
