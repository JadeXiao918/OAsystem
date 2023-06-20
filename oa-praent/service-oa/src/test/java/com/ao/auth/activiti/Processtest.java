package com.ao.auth.activiti;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class Processtest {
    //注入RepositoryService
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    //单个的流程实例挂起
    @Test
    public void SingleSuspendProcessInstance() {
        String processInstanceId = "b61bebd6-0c1a-11ee-9cd2-1cbfc0db6ea3";
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        boolean suspended = processInstance.isSuspended();
        if (suspended){
            runtimeService.activateProcessInstanceById(processInstanceId);
            System.out.println(processInstanceId+"激活");
        } else {
            runtimeService.suspendProcessInstanceById(processInstanceId);
            System.out.println(processInstanceId+"挂起");
        }
    }
    //全部流程实例挂起
    @Test
    public void suspendProcessInstanceAll(){
        //1.获取流程定义的对象
        ProcessDefinition qingjia = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("qingjia").singleResult();
        //2.调用流程定义对象的方法判断当前状态:挂起 激活
        boolean suspended = qingjia.isSuspended();
        //3.判断 如果挂起，实现激活
        if (suspended){
            //第一个参数 流程定义id
            //第二个参数 是否激活 true
            //第三个参数 时间点
            repositoryService.activateProcessDefinitionById(qingjia.getId(),true,null);
            System.out.println(qingjia.getId()+"激活了");
        } else {
            //如
            //激活，实现挂起
            repositoryService.suspendProcessDefinitionById(qingjia.getId(),true,null);
            System.out.println(qingjia.getId()+"被挂起");
        }

    }
    //创建流程实例，指定BusinessKey
    @Test
    public void startUpProcessAddBusinessKey(){
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("qingjia", "1001");
        System.out.println(processInstance.getBusinessKey());
        System.out.println(processInstance.getId());
    }
    //查询已经处理的任务
    @Test
    public void findCompletedTask(){
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .taskAssignee("lisi")
                .finished().list();
        for (HistoricTaskInstance historicTaskInstance : list){
            System.out.println("实例id："+historicTaskInstance.getId());
            System.out.println("任务id：" + historicTaskInstance.getId());
            System.out.println("任务负责人：" + historicTaskInstance.getAssignee());
            System.out.println("任务名称：" + historicTaskInstance.getName());
        }
    }
    //处理当前任务
    @Test
    public void completeTask(){
        //查询负责人需要处理的任务，返回一条
        Task task = taskService.createTaskQuery()
                .taskAssignee("zhangsan")
                .singleResult();
        //完成任务,参数是任务id
        taskService.complete(task.getId());
    }
    //查询某个人代办任务--zhangsan
    @Test
    public void findTaskList(){
        String assign = "zhangsan";
        List<Task> list = taskService.createTaskQuery()
                .taskAssignee(assign).list();
        for (Task task : list){
            System.out.println("流程实例id："+task.getProcessInstanceId());
            System.out.println("任务id：" + task.getId());
            System.out.println("任务负责人：" + task.getAssignee());
            System.out.println("任务名称：" + task.getName());
        }
    }
    //启动流程实例
    @Test
    public void startProcess(){
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("qingjia");
        System.out.println("流程定义id："+processInstance.getProcessInstanceId());
        System.out.println("流程实例id："+processInstance.getId());
        System.out.println("流程活动id："+processInstance.getActivityId());
    }
    //单个文件部署
    @Test
    public void deployProcess(){
        //流程部署
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("process/qingjia.bpmn20.xml")
                .addClasspathResource("process/qingjia.png")
                .name("请假申请流程")
                .deploy();
        System.out.println(deploy.getId());
        System.out.println(deploy.getName());
    }
}
