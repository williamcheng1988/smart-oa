package com.chz.smartoa.common.schedule;

import com.chz.smartoa.common.schedule.ITask.AbstractTask;

public class TaskExecutor extends AbstractTask {

	@Override
	protected void execute() throws TaskException {
		System.out.println("task was started.");
	}

}
