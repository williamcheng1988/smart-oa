package com.chz.smartoa.taskScheduler;

import org.apache.log4j.Logger;

import com.chz.smartoa.common.schedule.ITask.AbstractTask;
import com.chz.smartoa.common.schedule.TaskException;

/**
 * 定时任务示例 
 * @author huangdehui
 */

public class TimedEmailReminder extends AbstractTask {
	private static final Logger logger = Logger.getLogger(TimedEmailReminder.class);

	@Override
	protected void execute() throws TaskException {
		logger.debug("定时任务示例");
		
		//获取数据库参数
		String times = this.parameters.get("times");
		System.out.println("--------------"+times+"------------------");
	}
}