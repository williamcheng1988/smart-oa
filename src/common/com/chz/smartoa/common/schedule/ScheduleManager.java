package com.chz.smartoa.common.schedule;

import java.text.ParseException;

import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 任务调度管理器
 * 
 * @author JESON
 */
public class ScheduleManager {

	private static SchedulerFactory sf		 	= new StdSchedulerFactory();
	private static String JOB_GROUP_NAME 		= "job_group";
	private static String TRIGGER_GROUP_NAME 	= "trigger_group";
	
	/**
	 * 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
	 * 
	 * @param jobName	任务名
	 * @param jobClassName	任务处理类
	 * @param time	任务启动时间
	 * 
	 * @throws SchedulerException
	 * @throws ParseException
	 * @throws ClassNotFoundException
	 */
	public static void addJob(String jobName,String jobClassName,String time,String params) throws SchedulerException, ParseException, ClassNotFoundException{
		
		// 取得计划
		Scheduler scheduler = sf.getScheduler();
		
		// 任务名，任务组，任务执行类
		JobDetail jobDetail = new JobDetail(jobName, JOB_GROUP_NAME, Job.class.getClassLoader().loadClass(jobClassName));
		jobDetail.getJobDataMap().put("params", params);
		
		// 触发器并设置时间
		CronTrigger trigger = new CronTrigger(jobName, TRIGGER_GROUP_NAME);// 触发器名,触发器组
		trigger.setCronExpression(time);
		
		scheduler.scheduleJob(jobDetail, trigger);
		
		if(scheduler.isShutdown()){
			scheduler.start();
		}
	}
	
	/**
	 * 添加一个定时任务
	 * 
	 * @param jobName
	 *            任务名
	 * @param jobGroupName
	 *            任务组名
	 * @param triggerName
	 *            触发器名
	 * @param triggerGroupName
	 *            触发器组名
	 * @param job
	 *            任务
	 * @param time
	 *            时间设置，参考quartz说明文档
	 * @throws SchedulerException
	 * @throws ParseException
	 */
	public static void addJob(String jobName, String jobGroupName,
			String triggerName, String triggerGroupName, String jobclassname,
			String time) throws SchedulerException, ParseException,
			ClassNotFoundException {
		Scheduler sched = sf.getScheduler();
		JobDetail jobDetail = new JobDetail(jobName, jobGroupName, Job.class.getClassLoader().loadClass(jobclassname));// 任务名，任务组，任务执行类
		// 触发器
		CronTrigger trigger = new CronTrigger(triggerName, triggerGroupName);// 触发器名,触发器组
		trigger.setCronExpression(time);// 触发器时间设定
		sched.scheduleJob(jobDetail, trigger);
		if (sched.isShutdown())
			sched.start();
	}
	
	/**
	 * 检查任务是否在任务计划表中已经存在
	 * 
	 * @author JESON 2010-12-3
	 * @param jobName
	 * 			任务名称
	 * @return
	 * 			true 	: 存在
	 * 			false 	: 不存在
	 * @throws SchedulerException
	 */
	public static boolean hasJob(String jobName) throws SchedulerException{
		
		Scheduler sched = sf.getScheduler();
		JobDetail jobDetail = sched.getJobDetail(jobName,JOB_GROUP_NAME);
		if(jobDetail != null){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 检查任务触发器是否已经存在
	 * 
	 * @author JESON 2010-12-3
	 * @param name
	 * 			任务触发器名称
	 * @return
	 * 			true	: 存在
	 * 			false	: 不存在
	 * 
	 * @throws SchedulerException
	 */
	public static boolean hasTrigger(String name) throws SchedulerException{
		
		Scheduler sched = sf.getScheduler();
		Trigger trigger = sched.getTrigger(name,TRIGGER_GROUP_NAME);
		if(trigger != null){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 取得任务的启动表达式
	 * 
	 * @author JESON	2010-12-03
	 * @param triggerName
	 * 			任务名
	 * @return
	 * 			任务执行表达式
	 * 
	 * @throws SchedulerException
	 */
	public static String getTimerExpress(String jobName)  throws SchedulerException{
		Scheduler sched = sf.getScheduler();
		Trigger trigger = sched.getTrigger(jobName,TRIGGER_GROUP_NAME);
		if(trigger != null && trigger instanceof CronTrigger){
			return ((CronTrigger)trigger).getCronExpression();
		}
		
		return null;
	}
	
	/**
	 * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
	 * 
	 * @param jobName
	 * @param time
	 * @throws SchedulerException
	 * @throws ParseException
	 */
	public static void modifyJobTime(String jobName, String time)
			throws SchedulerException, ParseException {
		Scheduler sched = sf.getScheduler();
		Trigger trigger = sched.getTrigger(jobName, TRIGGER_GROUP_NAME);
		if (trigger != null) {
			CronTrigger ct = (CronTrigger) trigger;
			ct.setCronExpression(time);
			sched.resumeTrigger(jobName, TRIGGER_GROUP_NAME);
		}
	}
	
	/**
	 * 修改一个任务的触发时间
	 * 
	 * @param triggerName
	 * @param triggerGroupName
	 * @param time
	 * @throws SchedulerException
	 * @throws ParseException
	 */
	public static void modifyJobTime(String triggerName,
			String triggerGroupName, String time) throws SchedulerException,
			ParseException {
		Scheduler sched = sf.getScheduler();
		Trigger trigger = sched.getTrigger(triggerName, triggerGroupName);
		if (trigger != null) {
			CronTrigger ct = (CronTrigger) trigger;
			// 修改时间
			ct.setCronExpression(time);
			// 重启触发器
			sched.resumeTrigger(triggerName, triggerGroupName);
		}
	}	
	
	/**
	 * 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
	 * 
	 * @param jobName
	 * @throws SchedulerException
	 */
	public static void removeJob(String jobName) throws SchedulerException {
		Scheduler sched = sf.getScheduler();
		sched.pauseTrigger(jobName, TRIGGER_GROUP_NAME);// 停止触发器
		sched.unscheduleJob(jobName, TRIGGER_GROUP_NAME);// 移除触发器
		sched.deleteJob(jobName, JOB_GROUP_NAME);// 删除任务
	}
	
	/**
	 * 移除一个任务
	 * 
	 * @param jobName
	 * @param jobGroupName
	 * @param triggerName
	 * @param triggerGroupName
	 * @throws SchedulerException
	 */
	public static void removeJob(String jobName, String jobGroupName,
			String triggerName, String triggerGroupName)
			throws SchedulerException {
		Scheduler sched = sf.getScheduler();
		sched.pauseTrigger(triggerName, triggerGroupName);// 停止触发器
		sched.unscheduleJob(triggerName, triggerGroupName);// 移除触发器
		sched.deleteJob(jobName, jobGroupName);// 删除任务
	}
	
	public static void start(){
		try {
			Scheduler sched = sf.getScheduler();
			sched.start();
		} catch (Exception e) {
			
		}
	}
	
	public static void Shutdown(){
		try {
			Scheduler sched = sf.getScheduler();
			sched.shutdown();
			
			sf = new StdSchedulerFactory();
			
		} catch (Exception e) {
			
		}
	}	
}
