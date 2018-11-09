package com.common.quartz;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
* @Description:
* @date 2018年5月16日
*/
public class QzJob implements Job {

   @Override  
   public void execute(JobExecutionContext context) throws JobExecutionException {  
         
   	System.err.println("任务运行开始-------- start --------");   
       try {  
       	String jobName = context.getJobDetail().getKey().getName();
       	String jobGroup = context.getJobDetail().getKey().getGroup();
       	String triggerName = context.getTrigger().getKey().getName();
       	String triggerGroup = context.getTrigger().getKey().getGroup();
       	
       	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
       	System.err.println("触发器Key:" + triggerName + ".." + triggerGroup + " 正在执行...");
       	System.err.println("任务Key:" + jobName + ".." + jobGroup + " 正在执行，执行时间: "
       			+ dateFormat.format(Calendar.getInstance().getTime()));
       	
       }catch (Exception e) {  
       	System.err.println("捕获异常==="+e);  
       }  
       System.err.println("任务运行结束-------- end --------");   
   }  
   
}
