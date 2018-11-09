import com.common.quartz.QuartzManager;
import com.common.quartz.QzJob;

public class Task {  
 
	public static void main(String[] args) {
		System.err.println("【系统启动】");  
		String corn = "0/5 * * * * ?";
		//QuartzManager.addJob("job1", "jobGooup", "trigger1", "triggerGroup", MyJob.class, corn);
		QuartzManager.addJob("job1",  QzJob.class, corn);
		System.err.println("添加任务一"); 
		QuartzManager.getTriggerState("jobs");
		
		//睡眠一分钟
		try {
			Thread.sleep(60L * 1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		
		
		QuartzManager.modifyJobTime("job1", "0/3 * * * * ?");
		System.out.println("修改触发时间");
		
		try {
			Thread.sleep(15L * 1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		
		QuartzManager.removeJob("job1");
		//QuartzManager.removeJob("job1", "jobGooup", "trigger1", "triggerGroup");
		System.out.println("删除任务");
		
		try {
			Thread.sleep(5L * 1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		
		 System.out.println("【添加定时任务】");  
		 QuartzManager.addJob("job1",  QzJob.class, corn);
		//QuartzManager.shutdownJobs();
		//System.out.println("停止所有任务");
		try {
			Thread.sleep(5L * 1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		
		 System.out.println("【暂停定时任务】"); 
		QuartzManager.pauseJob("job1");
		 System.out.println("【立即运行一次】"); 
		QuartzManager.triggerJob("job1");
		try {
			Thread.sleep(5L * 1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		
		 System.out.println("【恢复定时任务】"); 
		QuartzManager.resumeJob("job1");
		try {
			Thread.sleep(5L * 1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		
		QuartzManager.shutdownJobs();
	}
}  
