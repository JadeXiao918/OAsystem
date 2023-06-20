package com.ao.auth.activiti;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;


public class MyTaskListener implements TaskListener {
    @Override
    public void notify(DelegateTask task) {
        if(task.getName().equals("经理审批")){
            //分配任务
            task.setAssignee("jack");
        }else if (task.getName().equals("人事审批")){
            task.setAssignee("tom");
        }
    }
}
