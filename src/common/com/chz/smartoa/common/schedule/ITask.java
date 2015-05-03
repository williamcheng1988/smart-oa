package com.chz.smartoa.common.schedule;

import java.util.HashMap;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public interface ITask extends Job{

	public void execute(String parameters) throws TaskException;
	
	public abstract class AbstractTask implements ITask{
		
		protected Map<String,String> parameters = null;
		
		public void execute(JobExecutionContext context) throws JobExecutionException {
			
			String taskName = context.getJobDetail().getName();
			String params = "";
			
			if(context.getJobDetail().getJobDataMap().containsKey("params")){
				params = context.getJobDetail().getJobDataMap().getString("params");
			}
			
			// Execute task logic
			execute(params);
			
		}
		
		public final void execute(String parameters) throws TaskException{
			
			// parse parameters and store to parameters map
			parse(parameters);
			
			// call execute to process
			execute();
			
		}
		
		protected final void parse(String parameter){
			
			if(parameter == null){
				return;
			}
			
			String[] params = parameter.split("[;]");
			if(params.length > 0){
				
				parameters = new HashMap<String,String>();
				
				for(String param : params ){
					
					String[] items = param.split("[=]");
					if(items.length == 0){
						continue;
					}
					
					String paramName = items[0];
					String paramValue = "";
					
					if(items.length > 1){
						paramValue = items[1];
					}
					
					parameters.put(paramName, paramValue);
					
				}
			}
		}
		
		protected abstract void execute() throws TaskException;
		
	}
}
