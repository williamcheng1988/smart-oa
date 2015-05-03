package com.chz.smartoa.common.schedule;

import java.util.HashMap;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

/**
 * 状态的StatefulJob不能并发执行，
 * 这意味着如果前次的StatefulJob还没有执行完毕，下一次的任务将阻塞等待，直到前次任务执行完毕。
 * 有状态任务比无状态任务需要考虑更多的因素，程序往往拥有更高的复杂度，因此除非必要，应该尽量使用无状态的Job
 * @author huangdhui
 */
public interface IStatefulTask extends StatefulJob{

	public void execute(String parameters) throws TaskException;
	
	public abstract class AbstractTask implements IStatefulTask{
		
		protected Map<String,String> parameters = null;
		
		public void execute(JobExecutionContext context) throws JobExecutionException {
			
//			String taskName = context.getJobDetail().getName();
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
