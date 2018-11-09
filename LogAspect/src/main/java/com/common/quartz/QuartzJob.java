package com.common.quartz;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.entity.ScheduleJob;

/**
 * 通过注解实现有状态的job
 *
 */
@DisallowConcurrentExecution  
public class QuartzJob  implements Job{

    //@Override
    public void execute(JobExecutionContext job) throws JobExecutionException {
        ScheduleJob scheduleJob = (ScheduleJob)job.getMergedJobDataMap().get("scheduleJob");  
        String name=scheduleJob.getJobName();
        System.out.println("定时器"+name+"正在运行。。。。。");
    }

}
