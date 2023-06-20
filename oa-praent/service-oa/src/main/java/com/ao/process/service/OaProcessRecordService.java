package com.ao.process.service;


import com.atguigu.model.process.ProcessRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 审批记录 服务类
 * </p>
 *
 */
public interface OaProcessRecordService extends IService<ProcessRecord> {
    void record(Long processId,Integer status,String description);
}
