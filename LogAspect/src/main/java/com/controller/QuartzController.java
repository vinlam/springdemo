package com.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.quartz.QuartzJob;
import com.entity.ScheduleJob;

@Controller
@RequestMapping(value = "/quartz")
public class QuartzController {

	@Autowired
	private Scheduler scheduler;

	/**
	 * 进入定时器管理界面
	 * 
	 * @param jobName
	 * @param jobGroup
	 * @return
	 */
	@RequestMapping("/quartzPage")
	public String quartzPage(String jobName, String jobGroup) {
		return "backstageViews/quartz/quartz";
	}

	/**
	 * 任务创建与更新(未存在的就创建，已存在的则更新)
	 * 
	 * @param request
	 * @param response
	 * @param scheduleJob
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/startQuartz")
	@ResponseBody
	public Map<String, Object> updateQuartz(String jobName, String jobGroup) {
		Map<String, Object> map = new HashMap<>();

		ScheduleJob job = new ScheduleJob();
		job.setJobGroup(jobGroup.trim());
		job.setJobName(jobName.trim());
		System.out.println(jobName + "," + jobGroup);
		try {
			// 获取触发器标识
			TriggerKey triggerKey = TriggerKey.triggerKey(jobName.trim(), jobGroup.trim());
			// 获取触发器trigger
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			if (null == trigger) {// 不存在任务
				// 创建任务
				JobDetail jobDetail = JobBuilder.newJob(QuartzJob.class).withIdentity(jobName.trim(), jobGroup.trim())
						.build();
				jobDetail.getJobDataMap().put("scheduleJob", job);
				// 表达式调度构建器
				// 这里的时间也可以通过页面传送过来。具体怎么写请看上一篇quartz介绍
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/10 * * * * ?");
				// 按新的cronExpression表达式构建一个新的trigger
				trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup())
						.withSchedule(scheduleBuilder).build();
				scheduler.scheduleJob(jobDetail, trigger);
			} else {// 存在任务
					// Trigger已存在，那么更新相应的定时设置
					// 表达式调度构建器

				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/5 * * * *");
				// 按新的cronExpression表达式重新构建trigger
				trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
				// 按新的trigger重新设置job执行
				scheduler.rescheduleJob(triggerKey, trigger);
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		map.put("msg", "success");
		map.put("code", 200);
		return map;
	}

	/**
	 * 删除任务
	 * 
	 * @param request
	 * @param response
	 * @param scheduleJob
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/removeQuartz")
	public void deleteQuartz(String jobName, String jobGroup) {
		JobKey jobKey = JobKey.jobKey(jobName.trim(), jobGroup.trim());
		System.out.println("删除" + jobName.trim() + "任务");
		try {
			scheduler.deleteJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 暂停任务
	 * 
	 * @param request
	 * @param response
	 * @param job
	 * @param model
	 * @return http://localhost:8091/SpringAop/quartz/pauseQuartz?jobName=job1&jobGroup=1
	 */
	@RequestMapping(value = "/pauseQuartz")
	@ResponseBody
	public void pauseQuartz(String jobName, String jobGroup) {
		ScheduleJob job = new ScheduleJob();
		job.setJobGroup(jobGroup);
		job.setJobName(jobName);
		System.out.println(jobName + "," + jobGroup);
		JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
		try {
			scheduler.pauseJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 恢复任务
	 * 
	 * @param request
	 * @param response
	 * @param scheduleJob
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/resumeQuartz")
	@ResponseBody
	public void resumeQuartz(String jobName, String jobGroup) {
		ScheduleJob job = new ScheduleJob();
		job.setJobGroup(jobGroup);
		job.setJobName(jobName);
		System.out.println(jobName + "," + jobGroup);
		JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
		try {
			scheduler.resumeJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

}
