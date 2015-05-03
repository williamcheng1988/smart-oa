package com.chz.smartoa.common.schedule;

import java.text.ParseException;
import java.util.List;

import org.quartz.SchedulerException;

import com.chz.smartoa.common.schedule.dao.TaskDao;
import com.chz.smartoa.common.schedule.pojo.ScheduleTask;

public class TaskLoader {

	public static void start(TaskDao taskDao){
		List<ScheduleTask> tasks = taskDao.getList();
		System.out.println("查询任务数:" + tasks.size());
		
		if(tasks != null){
			for(ScheduleTask t : tasks){
				loadTask(t);
			}
		}
	}

	private static void loadTask(ScheduleTask task){
		try {
			ScheduleManager.addJob(task.getTask_name(),task.getHandler_class(),task.getExpress(),task.getParameters());
			System.out.println("   任务[" + task.getTask_name() + "]已就绪. 时间:" + task.getExpress());
		} catch (SchedulerException e) {
			System.out.println("   任务[" + task.getTask_name() + "]的定时表达式格式无效：" + task.getExpress());
		} catch (ParseException e) {
			System.out.println("   任务[" + task.getTask_name() + "]的定时表达式格式无效:" + task.getExpress());
		} catch (ClassNotFoundException e) {
			System.out.println("   任务[" + task.getTask_name() + "]的任务处理类未找到:" + task.getHandler_class());
		}
	}
	
}
