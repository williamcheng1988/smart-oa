package com.chz.smartoa.taskScheduler;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.chz.smartoa.common.email.EmailHelper;
import com.chz.smartoa.common.email.EmailMessage;
import com.chz.smartoa.common.email.EmailType;
import com.chz.smartoa.common.schedule.IStatefulTask.AbstractTask;
import com.chz.smartoa.common.schedule.TaskException;
import com.chz.smartoa.common.service.BaseService;
import com.chz.smartoa.global.ServerInfo;
import com.chz.smartoa.system.pojo.Notice;
import com.chz.smartoa.system.service.NoticeBiz;

/**
 * 定时任务示例 
 * @author huangdehui
 */

public class EmailReminder extends AbstractTask {
	
	private static final Logger logger = Logger.getLogger(EmailReminder.class);

	@Override
	@SuppressWarnings("unchecked")
	protected void execute() throws TaskException {
		logger.debug("加载待发送邮件...");
		BaseService baseService = (BaseService) ServerInfo.wac.getBean("baseService");
		NoticeBiz noticeBiz = (NoticeBiz) ServerInfo.wac.getBean("noticeBiz");
		
		Notice param = new Notice(); 
		param.setEmail(1);
		try {
			//查询待发送邮件
			List<Notice> notices = noticeBiz.list(param);
			if(notices != null && notices.size() > 0){
				for (Notice notice : notices) {
					EmailMessage email = new EmailMessage();
					email.setType(EmailType.HTML);
					//查询收件人地址
					List<String> receivers = (List<String>) baseService.queryForList("Staff_email", notice.getToPeople().split(","));
					email.setTo(receivers);
					//查询抄送人地址
					if (StringUtils.isNotEmpty(notice.getCcPeople())) {
						List<String> ccs = (List<String>) baseService.queryForList("Staff_email", notice.getCcPeople().split(","));
						email.setCc(ccs);
					}
					email.setSubject(notice.getTitle());
					email.setMsg(notice.getContent());
					email.setHtmlMsg(notice.getHtmlContent());
					param.setRowId(notice.getRowId());
					try {
						//发送邮件
						EmailHelper.send(email);
						noticeBiz.updateNoticeSendSuccess(param);
					} catch (Exception e) {
						logger.warn("邮件发送失败！"+e);
						noticeBiz.updateNoticeSendCount(param);
					}
				}
			}
		} catch (SQLException e) {
			logger.error("待发送邮件查询失败！"+e);
		} catch (Exception e) {
			logger.error(e);
		}
	}

}