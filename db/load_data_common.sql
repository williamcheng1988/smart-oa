-- 初始化权限资源
insert into t_resource
(resource_id,parent_id,resource_name,address_url,resource_desc,resource_type,auth_type,sort_num)
values
('regist_page','other','用户注册页面','staff/regist.html',null,1,'UNAUTH',1),
('regist','other','用户注册','/staff!regist.do',null,1,'UNAUTH',1),
('reset_pwd_page','other','用户密码重置页面','staff/resetPwd.html',null,1,'LOGIN_AUTH',1),
('reset_pwd','other','用户密码重置','/staff!resetPwd.do',null,1,'LOGIN_AUTH',1),
('staff_login_page','other','用户登录页面','/login.html',null,1,'UNAUTH',1),
('index_redirect','other','默认页面跳转','/redirect.html',null,1,'UNAUTH',1),
('staff_login','other','用户登录','/staff!login.do',null,1,'UNAUTH',1),
('staff_logout','other','用户登出','/staff!logout.do',null,1,'UNAUTH',1),
('common_auth','other','auth','/common/authError.html',null,1,'UNAUTH',1),
('common_error','other','error','/common/error.html',null,1,'UNAUTH',1),
('common_sucess','other','sucess','/common/success.html',null,1,'UNAUTH',1),
('index','other','首页','/index.do',null,1,'LOGIN_AUTH',1),
('staff_info','other','个人资料','/staff!staffInfo.do',null,1,'LOGIN_AUTH',1),
('contacts','other','通讯录','/staff!contacts.do',null,1,'LOGIN_AUTH',1),
('home','other','主页','/home.html',null,1,'LOGIN_AUTH',1),
('quick_menu','other','快捷菜单','/citi.html',null,1,'UNAUTH',1),
('error','other','出错页','/error.html',null,1,'LOGIN_AUTH',1);

-- 我的任务
insert into t_resource
(resource_id,parent_id,resource_name,address_url,resource_desc,resource_type,auth_type,sort_num)
values
('work_flow','root','我的任务',null,null,1,'LOGIN_AUTH',100),
	('undo_work_flow','work_flow','待办任务','flow/todoList.html',null,1,'LOGIN_AUTH',1),
	('work_flow_query','work_flow','任务跟踪','flow!historyListPage.do',null,1,'LOGIN_AUTH',2),
	('delegation','work_flow','授权','delegation!delegationSelfPage.do',null,1,'LOGIN_AUTH',3);
	
insert into t_resource 
(resource_id,parent_id,resource_name,address_url,resource_desc,resource_type,auth_type,sort_num)
values
('flow_launch','other','发起流程页面','flow!launch.do',null,1,'LOGIN_AUTH',1),
('flow_draftList','other','草稿列表','flow!draftList.do',null,1,'LOGIN_AUTH',1),
('flow_save','other','提交流程','flow!save.do',null,1,'LOGIN_AUTH',1),
('flow_todo_list','other','待办任务查询','flow!todoList.do',null,1,'LOGIN_AUTH',1),
('flow_deal_page','other','处理任务页面','flow!approve.do',null,1,'LOGIN_AUTH',1),
('flow_approve_result','other','查看审批意见页面','flow!approveResult.do',null,1,'LOGIN_AUTH',1),
('flow_deal','other','处理任务','flow!deal.do',null,1,'LOGIN_AUTH',1), 
('flow_history','other','办结任务列表','flow!historyList.do',null,1,'LOGIN_AUTH',1),	
('flow_view','other','查看任务','flow!view.do',null,1,'LOGIN_AUTH',1),
('flow_manage','other','任务管理','flow!manage.do',null,1,'LOGIN_AUTH',1),
('flow_modify','other','修改流程','flow!saveReconf.do',null,1,'LOGIN_AUTH',1),
('delegation_list','other','授权列表','delegation!list.do',null,1,'LOGIN_AUTH',1),
('delegation_insert','other','新增授权','delegation!insert.do',null,1,'LOGIN_AUTH',1),
('delegation_log_page','other','授权日志页面','delegation!logPage.do',null,1,'LOGIN_AUTH',1),
('delegation_log','other','授权日志','delegation!log.do',null,1,'LOGIN_AUTH',1),
('delegation_undo_log_page','other','授权待办任务页面','delegation!undoLog.do',null,1,'LOGIN_AUTH',1),
('delegation_undo_log','other','授权待办任务','delegation!undo.do',null,1,'LOGIN_AUTH',1),
('delegation_cancel','other','取消授权日志','delegation!cancel.do',null,1,'LOGIN_AUTH',1);

-- 系统设置
insert into t_resource
(resource_id,parent_id,resource_name,address_url,resource_desc,resource_type,auth_type,sort_num,icon_name)
values
('system_setting','root','系统设置',null,null,1,'AUTH',900,'icon-setting');
insert into t_resource
(resource_id,parent_id,resource_name,address_url,resource_desc,resource_type,auth_type,sort_num)
values
	('staff_manage','system_setting','用户管理','staff/list.html',null,1,'AUTH',1),
	('role_manage','system_setting','角色管理','role/list.html',null,1,'AUTH',2),
	('delegation_manage','system_setting','授权管理','delegation!manageList.do',null,1,'AUTH',3),
	('process_config','system_setting','流程配置','process!listPage.do',null,1,'AUTH',4);
	
INSERT INTO t_resource_option VALUES
	('process_config','流程列表','process!list.do'),
	('process_config','新增流程','process!insert.do'),
	('process_config','配置流程','process!insertReconf.do'),
	('process_config','修改流程','process!update.do'),
	('process_config','流程配置明细','process!listConfs.do'),
	('process_config','启用/挂起','process!updateStatus.do');	
	
INSERT INTO t_resource_option VALUES
('delegation_manage','授权列表','delegation!manage.do');	
	
-- 用户管理操作地址
INSERT INTO t_resource_option VALUES
('staff_manage','加载用户列表','staff!list.do'),
('staff_manage','新增用户','staff!insert.do'),
('staff_manage','修改用户','staff!update.do'),
('staff_manage','删除用户','staff!delete.do'),
('staff_manage','锁定','staff!lock.do'),
('staff_manage','解锁','staff!unlock.do'),
('staff_manage','重置密码','staff!reset.do');


-- 角色管理操作地址
INSERT INTO t_resource_option VALUES
('role_manage','加载角色列表','role!list.do'),
('role_manage','新增角色页面','role/add.html'),
('role_manage','新增角色','role!insert.do'),
('role_manage','修改角色','role!update.do'),
('role_manage','删除角色','role!delete.do'),
('role_manage','加载资源树','role!resource.do');

-- 日志表
insert into t_resource
(resource_id,parent_id,resource_name,address_url,resource_desc,resource_type,auth_type,sort_num,icon_name)
values
('system_log','root','系统日志',null,null,1,'AUTH',901,'log.png'),
	('log_login','system_log','登录日志','log/loginList.html',null,1,'AUTH',1,null),
	('log_opt','system_log','操作日志','log/optList.html',null,1,'AUTH',2,null);
INSERT INTO t_resource_option VALUES
('log_login','登录日志','log!login.do'),
('log_opt','操作日志','log!opt.do');

-- 添加图标
update t_resource set icon_name = 'files.png' where resource_id = 'file_manager';
update t_resource set icon_name = 'flowInput.png' where resource_id = 'FORMTEMPLATETYPE1';
update t_resource set icon_name = 'flowInput.png' where resource_id = 'formTmpType1';
update t_resource set icon_name = 'flowInput.png' where resource_id = 'formTmpType2';
update t_resource set icon_name = 'flowInput.png' where resource_id = 'formTmpType4';
update t_resource set icon_name = 'icon_process.png' where resource_id = 'process_manage';
update t_resource set icon_name = 'total.png' where resource_id = 'reportStatistics_manage';
update t_resource set icon_name = 'log.png' where resource_id = 'system_log';
update t_resource set icon_name = 'setting.png' where resource_id = 'system_setting';
update t_resource set icon_name = 'todo.png' where resource_id = 'work_flow';

-- 插入定时邮件提醒任务
INSERT INTO t_schedule_task
  (ID,TASK_NAME,TASK_DESC,GROUP_ID,GROUP_NAME,EXPRESS,HANDLER_CLASS,PARAMETERS,CATEGORY,STARTDATE,STATUS,DELETEFLAG)
VALUES
  ('DCFC_timedEmailReminder','DCFC定时邮件提醒任务','这个JOB12点调用一次','DCFC_timedReminder','启动定时邮件提醒任务',
'0 00 12 * * ? 2013','com.chz.smartoa.taskScheduler.TimedEmailReminder','times=1','','',0,0);	

insert into t_resource
(resource_id,parent_id,resource_name,address_url,resource_desc,resource_type,auth_type,sort_num)
VALUES
('dictionary_manage','system_setting','字典管理','dictionary/list.html',null,1,'AUTH',3),
('file_manager','root','文件管理',NULL,null,1,'AUTH',300),
('file_publicInfo','file_manager','公共信息','filemanager/list_public.html',null,1,'AUTH',1),
('file_technologyInfo','file_manager','技术信息','filemanager/list_technology.html',null,1,'AUTH',2),
-- ('affiche_manager','system_setting','公告管理','afficheInfo/list.html',null,1,'AUTH',6),
('calendar_manager','system_setting','日历管理','calendarInfo/manager.html',null,1,'AUTH',7),
('workPlan_manager','other','日程管理','workPlanInfo/manager.html',null,1,'LOGIN_AUTH',8),
('dictionary_getSonList','other','获取子字典项','filemanager!getSonList.do',null,1,'LOGIN_AUTH',1),
('file_delete','other','删除文件管理表记录','filemanager!deleteForPage.do',null,1,'LOGIN_AUTH',1),
('file_grouplist','other','流程未提交的文件列表','filemanager!getGroupFilelist.do',null,1,'LOGIN_AUTH',1),
('file_saveAddFile','other','保存附件','upload!saveAddFile.do',null,1,'LOGIN_AUTH',1),
('file_saveUpdate','other','流程未提交的附件修改保存','upload!saveFileForUpdate.do',null,1,'LOGIN_AUTH',1),
('file_seeContent','other','查看附件内容','filemanager!examineFileContent.do',null,1,'LOGIN_AUTH',1),
('file_update','other','附件修改界面','filemanager!updateWindow.do',null,1,'LOGIN_AUTH',1),
('file_updateSave','other','附件修改保存操作','upload!saveAfterFileForUpdate.do',null,1,'LOGIN_AUTH',1),
('file_saveFileForAudit','other','审批过程中添加附件','upload!saveFileForAudit.do',null,1,'LOGIN_AUTH',1),
('file_upload','other','文件上传界面','/filemanager!addWindow.do',null,1,'LOGIN_AUTH',1),
('file_firstPageForPublic','other','首页公共信息显示','filemanager!displayInfoForPublic.do',null,1,'LOGIN_AUTH',1),
('file_firstPageForTechnology','other','首页技术信息显示','filemanager!displayInfoForTechnology.do',null,1,'LOGIN_AUTH',1),
('file_firstPageForAffiche','other','首页公告信息显示','afficheInfo!firstPageForNewAffiche.do',null,1,'LOGIN_AUTH',1),
('file_firstPageForAfficheSeeContent','other','首页公告信息查看详细内容','afficheInfo!firstPgeeForSeeContent.do',null,1,'LOGIN_AUTH',1),
('file_filedownLoad','other','文件下载','download!filedownLoad.do',null,1,'LOGIN_AUTH',1),
('workPlanInfo_save','other','日程数据保存操作','workPlanInfo!save.do',null,1,'LOGIN_AUTH',1),
('workPlanInfo_queryWorkPlan','other','根据日期获取日程数据','workPlanInfo!queryWorkPlan.do',null,1,'LOGIN_AUTH',1),
('workPlanInfo_toEidt','other','日程数据编辑','workPlanInfo!toEidt.do',null,1,'LOGIN_AUTH',1),
('workPlanInfo_delete','other','日程数据删除操作','workPlanInfo!delete.do',null,1,'LOGIN_AUTH',1),
('workPlanInfo_queryWorkDateForDate','other','获取前后一个月已设置的日程时间','workPlanInfo!queryWorkDateForDate.do',null,1,'LOGIN_AUTH',1),
('workPlanInfo_firstPageForNewWorkPlan','other','当天的日程安排数据列表','workPlanInfo!firstPageForNewWorkPlan.do',null,1,'LOGIN_AUTH',1),
('workPlanInfo_firstPgeeForSeeContent','other','查看日程相信内容','workPlanInfo!firstPgeeForSeeContent.do',null,1,'LOGIN_AUTH',1),
('file_findFileListPage','other','查看文件视图界面','filemanager/list_query.html',null,1,'LOGIN_AUTH',1),
('file_findFileList','other','查看所有允许查看的附件','filemanager!findFileList.do',null,1,'LOGIN_AUTH',1),
('file_querymorePublic','other','查询更多公共信息列表','filemanager/query_more_public.html',null,1,'LOGIN_AUTH',1),
('file_querymoreTechnology','other','查询更多技术信息列表','filemanager/query_more_technology.html',null,1,'LOGIN_AUTH',1);



insert into t_resource_option values('dictionary_manage','加载字典配置菜单','dictionary!generateTree.do');
insert into t_resource_option values('dictionary_manage','查询字典列表','dictionary!list.do');
insert into t_resource_option values('dictionary_manage','添加字典','dictionary/add.html');
insert into t_resource_option values('dictionary_manage','保存字典','dictionary!saveInsert.do');
insert into t_resource_option values('dictionary_manage','字典项新增页面','dictionary!toInsertSub.do');
insert into t_resource_option values('dictionary_manage','保存子字典项','dictionary!saveSubInsert.do');
insert into t_resource_option values('dictionary_manage','字典项修改界面','dictionary!toEdit.do');
insert into t_resource_option values('dictionary_manage','字典项保存修改','dictionary!saveUpdate.do');
insert into t_resource_option values('dictionary_manage','加载公共信息字典菜单项','dictionary!treeForPublic.do');
insert into t_resource_option values('dictionary_manage','加载技术信息字典菜单项','dictionary!treeForTechnology.do');

insert into t_resource_option values('file_publicInfo','附件列表公共信息查询','filemanager!listForPublic.do');
insert into t_resource_option values('file_publicInfo','附件修改界面','filemanager!toEditpage.do');
insert into t_resource_option values('file_publicInfo','删除操作','filemanager!delete.do');
insert into t_resource_option values('file_publicInfo','保存修改后的附件','upload!saveUpdateFile.do');
insert into t_resource_option values('file_publicInfo','添加附件','filemanager/add.html');
insert into t_resource_option values('file_publicInfo','根据groupId获取附件列表显示界面','filemanager/group_list.html');
insert into t_resource_option values('file_publicInfo','附件公开操作','filemanager!toPublic.do');
insert into t_resource_option values('file_publicInfo','附件公开操作','filemanager!unPublic.do');
insert into t_resource_option values('file_technologyInfo','附件非公开操作','filemanager!toPublic.do');
insert into t_resource_option values('file_technologyInfo','附件非公开操作','filemanager!unPublic.do');
insert into t_resource_option values('file_technologyInfo','附件列表技术信息查询','filemanager!listFortechnology.do');



insert into t_resource_option values('calendar_manager','根据日期获取日历数据','calendarInfo!queryCalendar.do');
insert into t_resource_option values('calendar_manager','日历数据保存操作','calendarInfo!save.do');
insert into t_resource_option values('calendar_manager','日历数据界面修改','calendarInfo!toEidt.do');
insert into t_resource_option values('calendar_manager','日历数据删除操作','calendarInfo!delete.do');
insert into t_resource_option values('calendar_manager','根据日期获取日历数据(显示日程节假日)','calendarInfo!queryCalendarForDate.do');
insert into t_resource_option values('calendar_manager','根据日期获取指定日期数据','calendarInfo!findCalendar.do');



update t_resource set icon_name = 'files.png' where resource_id = 'file_manager';


INSERT INTO t_resource_option VALUES
('role_manage','角色对应文件类型允许权限设置','role!allowfileTypes.do');

INSERT INTO t_resource_option VALUES
('role_manage','角色对应文件类型不允许权限设置','role!unAllowfileTypes.do');

INSERT INTO T_DICTIONARY_CONFIG VALUES (1, '--- 项目客户档案 ---', 'cfg_xmkhdangan', 1, 93, 1, 1, NULL, NULL, 1, NULL, 2, NULL);

INSERT INTO T_DICTIONARY_CONFIG VALUES (2, '项目客户资料', 'cfg_pro_customer_data', 2, 93, 1, 1, NULL, NULL, 1, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (3, '基本证照', 'cfg_base_zz', 1, 22, NULL, 1, NULL, '01-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (4, '行业技术', 'cfg_trade_technology', 2, 2, NULL, 1, NULL, '02-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (5, '财务信息', 'cfg_finance_info', 3, 2, NULL, 1, NULL, '03-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (6, '内部控制', 'cfg_in_control', 4, 2, NULL, 1, NULL, '04-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (7, '组织人事', 'cfg_organize_people', 5, 2, NULL, 1, NULL, '05-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (8, '分支机构', 'cfg_branch_orgn', 6, 2, NULL, 1, NULL, '06-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (9, '发展规划', 'cfg_dev_plan', 7, 2, NULL, 1, NULL, '07-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (10, '其他资料', 'cfg_other_data', 8, 2, NULL, 1, NULL, '08-{yy}', 1, NULL, 3,2);

INSERT INTO T_DICTIONARY_CONFIG VALUES (11, '项目服务控制', 'cfg_pro_service_control', 3, 93, 1, 1, NULL, NULL, 1, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (12, '投标文件', 'cfg_toubiao_file', 1, 11, NULL, 1, NULL, '11-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (13, '立项报告', 'cfg_lixiang_report', 2, 11, NULL, 1, NULL, '12-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (14, '预算决算', 'cfg_yusuan_juesuan', 3, 11, NULL, 1, NULL, '13-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (15, '尽职调查', 'cfg_jinzhidiaocha', 4, 11, NULL, 1, NULL, '14-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (16, '投资评估', 'cfg_touzipinggu', 5, 11, NULL, 1, NULL, '15-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (17, '沟通记录', 'cfg_goutong_record', 6, 11, NULL, 1, NULL, '16-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (18, '专题报告', 'cfg_zhuanti_report', 7, 11, NULL, 1, NULL, '17-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (19, '协议合同', 'cfg_protocol_bargain', 8, 11, NULL, 1, NULL, '18-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (20, '其他文件', 'cfg_other_file', 9, 11, NULL, 1, NULL, '19-{yy}', 1, NULL, 3,2);

INSERT INTO T_DICTIONARY_CONFIG VALUES (21, '--- 信诺投资档案 ---', 'cfg_xntzdangan', 4, 93, 1, 1, NULL, NULL, 1, NULL, 2, NULL);

INSERT INTO T_DICTIONARY_CONFIG VALUES (22, '公司运行环境', 'cfg_company_run_entironment', 5, 93, 1, 1, NULL, NULL, 1, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (23, '行业', 'cfg_hangye', 1, 22, NULL, 1, NULL, '11-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (24, '工商', 'cfg_gongshang', 2, 22, NULL, 1, NULL, '12-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (25, '国税', 'cfg_guoshui', 3, 22, NULL, 1, NULL, '13-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (26, '地税', 'cfg_dishui', 4, 22, NULL, 1, NULL, '14-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (27, '银行', 'cfg_bank', 5, 22, NULL, 1, NULL, '15-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (28, '网站', 'cfg_network', 6, 22, NULL, 1, NULL, '16-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (29, '其他', 'cfg_other', 7, 22, NULL, 1, NULL, '17-{yy}', 1, NULL, 3,2);

INSERT INTO T_DICTIONARY_CONFIG VALUES (30, '公司基本制度', 'cfg_company_base_system', 6, 93, 1, 1, NULL, NULL, 1, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (31, '基本规章', 'cfg_base_guizhang', 1, 30, NULL, 1, NULL, '21-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (32, '业务管理', 'cfg_service_manage', 2, 30, NULL, 1, NULL, '22-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (33, '财务会计', 'cfg_finance_accountant', 3, 30, NULL, 1, NULL, '23-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (34, '组织人事', 'cfg_zuzhirenshi', 4, 30, NULL, 1, NULL, '24-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (35, '网络通信', 'cfg_network_tongxin', 5, 30, NULL, 1, NULL, '25-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (36, '档案管理', 'cfg_archives_manage', 6, 30, NULL, 1, NULL, '26-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (37, '行政后台', 'cfg_xingzhenghoutai', 7, 30, NULL, 1, NULL, '27-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (38, '其他规章', 'cfg_other_rule', 8, 30, NULL, 1, NULL, '28-{yy}', 1, NULL, 3,2);

INSERT INTO T_DICTIONARY_CONFIG VALUES (39, '公司内控决策', 'cfg_company_in_decision', 7, 93, 1, 1, NULL, NULL, 1, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (40, '股东会议', 'cfg_gudong_meeting', 1, 39, NULL, 1, NULL, '31-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (41, '执行董事', 'cfg_zhixingdongshi', 2, 39, NULL, 1, NULL, '32-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (42, '合伙人会议', 'cfg_cobber_meeting', 3, 39, NULL, 1, NULL, '33-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (43, '公司通知', 'cfg_company_notice', 4, 39, NULL, 1, NULL, '34-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (44, '归档记录', 'cfg_guidang_record', 5, 39, NULL, 1, NULL, '35-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (45, '印鉴控制', 'cfg_yinjian_control', 6, 39, NULL, 1, NULL, '36-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (46, '其他控制', 'cfg_other_control', 7, 39, NULL, 1, NULL, '37-{yy}', 1, NULL, 3,2);

INSERT INTO T_DICTIONARY_CONFIG VALUES (47, '公司证照资料', 'cfg_company_certificate_data', 8, 93, 1, 1, NULL, NULL, 1, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (48, '营业执照', 'cfg_business_certificate', 1, 47, NULL, 1, NULL, '01-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (49, '国税登记', 'cfg_guoshui_register', 2, 47, NULL, 1, NULL, '02-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (50, '地税登记', 'cfg_dishui_register', 3, 47, NULL, 1, NULL, '03-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (51, '组织机构代码登记', 'cfg_zzjg_code_register', 4, 47, NULL, 1, NULL, '04-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (52, '银行基本帐户', 'cfg_bank_base_account', 5, 47, NULL, 1, NULL, '05-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (53, '其他证照资料', 'cfg_other_certificate_data', 6, 47, NULL, 1, NULL, '06-{yy}', 1, NULL, 3,2);

INSERT INTO T_DICTIONARY_CONFIG VALUES (54, '公司技术支持', 'cfg_company_tech_suppor', 9, 93, 2, 1, NULL, NULL, 1, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (55, '基本法律法规', 'cfg_base_lex_rule', 1, 54, NULL, 1, NULL, '41-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (56, '基本市场数据', 'cfg_base_market_data', 2, 54, NULL, 1, NULL, '42-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (57, '专题研究资料', 'cfg_ztyj_data', 3, 54, NULL, 1, NULL, '43-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (58, '其他技术资料', 'cfg_other_tech_data', 4, 54, NULL, 1, NULL, '44-{yy}', 1, NULL, 3,2);

INSERT INTO T_DICTIONARY_CONFIG VALUES (59, '公司标准文档', 'cfg_company_standard_document', 10, 93, 2, 1, NULL, NULL, 1, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (60, '项目建议', 'cfg_pro_advice', 1, 59, NULL, 1, NULL, '51-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (61, '服务方案', 'cfg_service_scheme', 2, 59, NULL, 1, NULL, '52-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (62, '尽职调查', 'cfg_jz_research', 3, 59, NULL, 1, NULL, '53-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (63, '工作备忘', 'cfg_work_bw', 4, 59, NULL, 1, NULL, '54-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (64, '流程控制', 'cfg_flow_control', 5, 59, NULL, 1, NULL, '55-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (65, '协议模档', 'cfg_protocal_template', 6, 59, NULL, 1, NULL, '56-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (66, '业务手册', 'cfg_service_notebook', 7, 59, NULL, 1, NULL, '57-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (67, '其他模档', 'cfg_other_template', 8, 59, NULL, 1, NULL, '58-{yy}', 1, NULL, 3,2);

INSERT INTO T_DICTIONARY_CONFIG VALUES (68, '公司人员信息', 'cfg_company_employee_info', 11, 93, 1, 1, NULL, NULL, 1, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (69, '合伙人个人资料', 'cfg_cobber_self_info', 1, 68, NULL, 1, NULL, '61-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (70, '外聘专家资料', 'cfg_out_expert_info', 2, 68, NULL, 1, NULL, '62-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (71, '职员个人资料', 'cfg_employee_info', 3, 68, NULL, 1, NULL, '63-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (72, '公司人员通行录', 'cfg_company_employee_permit_record', 4, 68, NULL, 1, NULL, '64-{yy}', 1, NULL, 3,2);

INSERT INTO T_DICTIONARY_CONFIG VALUES (73, '公司客户维护', 'cfg_customer_vindicate', 12, 93, 1, 1, NULL, NULL, 1, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (74, '客户基本统计', 'cfg_customer_base_stat', 1, 73, NULL, 1, NULL, '71-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (75, '客户维护记录', 'cfg_customer_vindicate_record', 2, 73, NULL, 1, NULL, '72-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (76, '客户合同原件', 'cfg_customer_bargain_org', 3, 73, NULL, 1, NULL, '73-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (77, '其他客服资料', 'cfg_other_customer_data', 4, 73, NULL, 1, NULL, '74-{yy}', 1, NULL, 3,2);

INSERT INTO T_DICTIONARY_CONFIG VALUES (78, '公司财务会计', 'cfg_company_finance_accountant', 13, 93, 1, 1, NULL, NULL, 1, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (79, '财务会计报表', 'cfg_finance_accountnt_report', 1, 78, NULL, 1, NULL, '81-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (80, '原始凭证单据', 'cfg_org_credence_bill', 2, 78, NULL, 1, NULL, '82-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (81, '国税纳税申报', 'cfg_guo_na_apply', 3, 78, NULL, 1, NULL, '83-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (82, '地税纳税申报', 'cfg_di_na_apply', 4, 78, NULL, 1, NULL, '84-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (83, '注册验资报告', 'cfg_register_yan_report', 5, 78, NULL, 1, NULL, '85-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (84, '固定资产明细', 'cfg_gu_zc_detail', 6, 78, NULL, 1, NULL, '86-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (85, '其他财务资料', 'cfg_other_finance_data', 7, 78, NULL, 1, NULL, '87-{yy}', 1, NULL, 3,2);

INSERT INTO T_DICTIONARY_CONFIG VALUES (86, '公司经营分析', 'cfg_work_analyse', 14, 93, 1, 1, NULL, NULL, 1, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (87, '业务管理分析', 'cfg_business_manage_analyse', 1, 86, NULL, 1, NULL, '91-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (88, '财务管理分析', 'cfg_finance_manage_analyse', 2, 86, NULL, 1, NULL, '92-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (89, '人事管理分析', 'cfg_people_analyse', 3, 86, NULL, 1, NULL, '93-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (90, '绩效管理分析', 'cfg_performance_manage_analyse', 4, 86, NULL, 1, NULL, '94-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (91, '风险控制分析', 'cfg_risk_control_analyse', 5, 86, NULL, 1, NULL, '95-{yy}', 1, NULL, 3,2);
INSERT INTO T_DICTIONARY_CONFIG VALUES (92, '其他经营分析', 'cfg_other_work_analyse', 6, 86, NULL, 1, NULL, '96-{yy}', 1, NULL, 3,2);

INSERT INTO T_DICTIONARY_CONFIG VALUES (93, '文件类型', 'cfg_wenjianleixing', 1, -1, NULL, 1, NULL, NULL, 1, NULL, 1, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (94, '所在省市', 'cfg_suoshushegnshi', 2, -1, NULL, 1, NULL, NULL, 3, NULL, 1, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (95, '所属行业', 'cfg_suoshuhangye', 3, -1, NULL, 1, NULL, NULL, 3, NULL, 1, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (96, '业务品种', 'cfg_yewupinzhong', 4, -1, NULL, 1, NULL, NULL, 3, NULL, 1, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (97, '费用性质', 'cfg_feiyongxingzhi', 5, -1, NULL, 1, NULL, NULL, 3, NULL, 1, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (98, '专业级别', 'cfg_zhuanshujibie', 6, -1, NULL, 1, NULL, NULL, 3, NULL, 1, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (99, '承担角色', 'cfg_chengdanjuese', 7, -1, NULL, 1, NULL, NULL, 3, NULL, 1, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (100, '招待对象', 'zhaodaiduixiang', 8, -1, NULL, 1, NULL, NULL, 3, NULL, 1, NULL);

INSERT INTO T_DICTIONARY_CONFIG VALUES (101, '北京', 'SSCS_BEIJING', 1, 94, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (102, '天津', 'SSCS_TIANJING', 1, 94, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (103, '上海', 'SSCS_SHANGHAI', 1, 94, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (104, '重庆', 'SSCS_CHONGQING', 1, 94, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (105, '黑龙江', 'SSCS_HEILONGJIANG', 1, 94, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (106, '吉林', 'SSCS_JILIN', 1, 94, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (107, '辽宁', 'SSCS_LIAONING', 1, 94, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (108, '新疆', 'SSCS_XINJIANG', 1, 94, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (109, '内蒙古', 'SSCS_NEIMENGGU', 1, 94, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (110, '甘肃', 'SSCS_GANSU', 1, 94, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (111, '陕西', 'SSCS_SHANXI1', 1, 94, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (112, '山西', 'SSCS_SHANXI2', 1, 94, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (113, '河北', 'SSCS_HEBEI', 1, 94, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (114, '河南', 'SSCS_HENAN', 1, 94, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (115, '山东', 'SSCS_SHANDONG', 1, 94, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (116, '西藏', 'SSCS_XIZANG', 1, 94, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (117, '四川', 'SSCS_SICHUAN', 1, 94, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (118, '湖北', 'SSCS_HUBEI', 1, 94, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (119, '江苏', 'SSCS_JIANGSU', 1, 94, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (120, '安徽', 'SSCS_ANHUI', 1, 94, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (121, '贵州', 'SSCS_GUIZHOU', 1, 94, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (122, '湖南', 'HUNAN', 1, 94, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (123, '江西', 'SSCS_JIANGXI', 1, 94, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (124, '浙江', 'SSCS_ZHEJIANG', 1, 94, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (125, '云南', 'SSCS_YUNNAN', 1, 94, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (126, '广西', 'SSCS_GUANGXI', 1, 94, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (127, '广东', 'SSCS_GUANGDONG', 1, 94, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (128, '福建', 'SSCS_FUJIAN', 1, 94, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (129, '台湾', 'SSCS_TAIWAN', 1, 94, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (130, '海南', 'SSCS_HAINAN', 1, 94, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (131, '青海', 'SSCS_QINGHAI', 1, 94, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (132, '香港', 'SSCS_XIANGGANG', 1, 94, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (133, '澳门', 'SSCS_AOMEN', 1, 94, 3, 1, NULL, NULL, 3, NULL, 2, NULL);

INSERT INTO T_DICTIONARY_CONFIG VALUES (134, 'A 农、林、牧、渔业', 'SSHY_NONGLINMUYUYE', 1, 95, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (135, 'B 采掘业', 'SSHY_CAIJUEYE', 1, 95, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (136, 'C 制造业', 'SSHY_ZHIZAOYE', 1, 95, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (137, '--食品、饮料', 'SSHY_SHIPINYINLIAO', 1, 136, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (138, '--纺织、服装、皮毛', 'SSHY_FANGZHIFUZHUANGPIMAO', 1, 136, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (139, '--木材、家具', 'SSHY_MUCAIJIAJU', 1, 136, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (140, '--造纸、印刷', 'SSHY_ZAOZHIYINSHUA', 1, 136, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (141, '--石油、化学、塑胶、塑料', 'SSHY_SHIYOUHUAXUESUJIAOSULIAO', 1, 136, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (142, '--电子', 'SSHY_DIANZIYE', 1, 136, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (143, '--金属、非金属', 'SSHY_JINSHUFEIJINSHU', 1, 136, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (144, '--机械、设备、仪表', 'SSHY_JIXIESHEBEIYIBIAO', 1, 136, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (145, '--医药、生物制品', 'SSHY_YIYAOSHENGWUZHIPIN', 1, 136, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (146, '--其他制造业', 'SSHY_QITAZHIYAOYE', 1, 136, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (147, 'D 电力、煤气及水的生产和供应业', 'SSHY_DLMQJSDSCHGYY', 1, 95, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (148, 'E 建筑业', 'SSHY_JIANZHUYE', 1, 95, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (149, 'F 交通运输、仓储业', 'SSHY_JIAOTONGYUNSHUCANGCHU', 1, 95, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (150, 'G 信息技术业', 'SSHY_XINXIJISHUYE', 1, 95, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (151, 'H 批发和零售贸易', 'SSHY_PIFAHELINGSHOUMAOYI', 1, 95, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (152, 'I 金融、保险业', 'SSHY_JINRONGBAOXIAN', 1, 95, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (153, 'J 房地产业', 'SSHY_FANGDICHANYE', 1, 95, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (154, 'K 社会服务业', 'SSHY_SHEHUIFUWUYE', 1, 95, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (155, 'L 传播与文化产业', 'SSHY_CHUANBOHEWENHUACHANYE', 1, 95, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (156, 'M 综合类', 'SSHY_ZONGHELEIYE', 1, 95, 3, 1, NULL, NULL, 3, NULL, 2, NULL);


INSERT INTO T_DICTIONARY_CONFIG VALUES (157, '财务顾问', 'YWPZ_CAIWUGUWEN', 1, 96, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (158, '融资顾问', 'YWPZ_RONGZGUWEN', 1, 96, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (159, '资本管理', 'YWPZ_ZIBENGUANLI', 1, 96, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (160, '自有投资', 'YWPZ_ZIYOUTOUZI', 1, 96, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (161, '中介合作', 'YWPZ_ZHONGJIEHEZUO', 1, 96, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (162, '其他业务', 'YWPZ_QITAYEWU', 1, 96, 3, 1, NULL, NULL, 3, NULL, 2, NULL);


INSERT INTO T_DICTIONARY_CONFIG VALUES (163, '财务顾问费', 'FYXZ_CAIWUGUWENFEI', 1, 97, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (164, '资本管理费', 'FYXZ_ZIBENGUANLIFEI', 1, 97, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (165, '中介支出', 'FYXZ_ZHONGJIEZHICHU', 1, 97, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (166, '居间收入', 'FYXZ_JUJIANSHOURU', 1, 97, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (167, '其他费用', 'FYXZ_QITAFEIYONG', 1, 97, 3, 1, NULL, NULL, 3, NULL, 2, NULL);


INSERT INTO T_DICTIONARY_CONFIG VALUES (168, '分析师', 'ZYJB_FENXISHI', 1, 98, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (169, '高级分析师', 'ZYJB_GAOJIFENXISHI', 1, 98, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (170, '业务董事', 'ZYJB_YEWUDONGSHI', 1, 98, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (171, '执行董事', 'ZYJB_ZHIXINGDONGSHI', 1, 98, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (172, '董事总经理', 'ZYJB_DONGSHIZONGJINGLI', 1, 98, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (173, '合伙人', 'ZYJB_HEHUOREN', 1, 98, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (174, '外聘专家', 'ZYJB_WAIPINZHUANJIA', 1, 98, 3, 1, NULL, NULL, 3, NULL, 2, NULL);


INSERT INTO T_DICTIONARY_CONFIG VALUES (175, '财务顾问', 'CDJS_CAIWUJUWEN', 1, 99, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (176, '其他角色', 'CDJS_QITAJUESE', 1, 99, 3, 1, NULL, NULL, 3, NULL, 2, NULL);


INSERT INTO T_DICTIONARY_CONFIG VALUES (177, '客户', 'ZDDX_KEHU', 1, 100, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (178, '政府', 'ZDDX_ZHENGFU', 1,100, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (179, '中介', 'ZDDX_ZHONGJIE', 1, 100, 3, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (180, '公司', 'ZDDX_GONGSI', 1, 100, 3, 1, NULL, NULL, 3, NULL, 2, NULL);


INSERT INTO T_DICTIONARY_CONFIG VALUES (181, '客户简称', 'DIC_KHJC', 9, -1, NULL, 1, NULL, NULL, 3, NULL, 1, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (182, 'a', 'DIC_KHJC_a', 3, 181, NULL, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (183, 'b', 'DIC_KHJC_b', 3, 181, NULL, 1, NULL, NULL, 3, NULL, 2, NULL);
INSERT INTO T_DICTIONARY_CONFIG VALUES (184, 'c', 'DIC_KHJC_c', 3, 181, NULL, 1, NULL, NULL, 3, NULL, 2, NULL);



INSERT into T_DICTIONARY_CONFIG(DICTIONARY_NAME,DICTIONARY_KEY,PARENT_ID,IS_VALID,CONFIG_TYPE,SQL_VALUE)
VALUES('用户','NORMAL_USERS',-1,1,2,'select login_name dictionary_key,real_name dictionary_name from t_staff where status = 1 or status = 2  ORDER BY login_name');

INSERT into T_DICTIONARY_CONFIG(DICTIONARY_NAME,DICTIONARY_KEY,PARENT_ID,IS_VALID,CONFIG_TYPE,SQL_VALUE)
VALUES('角色','NORMAL_ROLES',-1,1,2,'select role_id dictionary_key,role_name dictionary_name from t_role ORDER BY role_id');

INSERT into T_DICTIONARY_CONFIG(DICTIONARY_NAME,DICTIONARY_KEY,PARENT_ID,IS_VALID,CONFIG_TYPE,SQL_VALUE)
VALUES('项目角色','NORMAL_ROLES_PROJECTS',-1,1,2,'select role_id dictionary_key,role_name dictionary_name from t_role where role_type=2 ORDER BY role_id');

INSERT into T_DICTIONARY_CONFIG(DICTIONARY_NAME,DICTIONARY_KEY,PARENT_ID,IS_VALID,CONFIG_TYPE,SQL_VALUE)
VALUES('流程','PROCESS_PROCDEF',-1,1,2,'select DISTINCT name_ dictionary_key,name_ dictionary_name from t_re_procdef where procdef_status_ = 1 ORDER BY create_time_ DESC');

INSERT into T_DICTIONARY_CONFIG(DICTIONARY_NAME,DICTIONARY_KEY,PARENT_ID,IS_VALID,CONFIG_TYPE,SQL_VALUE)
VALUES('岗位','NORMAL_POSTS',-1,1,2,'select post_id dictionary_key,post_name dictionary_name from t_post ORDER BY post_id DESC');


-- 资源模块
INSERT INTO t_resource(resource_id,parent_id,resource_name,address_url,resource_desc,resource_type,auth_type,sort_num) VALUES ('formTemplateType_manage', 'system_setting', '模板管理', 'dynamicForm/formTemplateType/list-form-template-type.html', NULL, '1', 'AUTH', 3);
INSERT INTO t_resource_option VALUES ('formTemplateType_manage', '模板列表管理', 'formTemplate!list.do');
INSERT INTO t_resource(resource_id,parent_id,resource_name,address_url,resource_desc,resource_type,auth_type,icon_name,sort_num) VALUES ('reportStatistics_manage', 'root', '报表统计', '', NULL, '1', 'AUTH','total.png',299);
INSERT INTO t_resource(resource_id,parent_id,resource_name,address_url,resource_desc,resource_type,auth_type,sort_num) VALUES ('formStatistics_manage', 'reportStatistics_manage', '填报统计', 'dynamicForm/statistics/formStatistics.html', NULL, '1', 'AUTH', 5);

-- 客户首页
INSERT INTO t_resource(resource_id,parent_id,resource_name,address_url,resource_desc,resource_type,auth_type,sort_num) 
VALUES ('customer_index', 'other', '客户首页', '/basic.html', NULL, '1', 'LOGIN_AUTH', 1);
INSERT INTO t_resource(resource_id,parent_id,resource_name,address_url,resource_desc,resource_type,auth_type,sort_num) 
VALUES ('customer_index_home', 'other', '客户首页-主页', 'home!basicHome.do', NULL, '1', 'LOGIN_AUTH', 1);

INSERT INTO t_resource(resource_id,parent_id,resource_name,address_url,resource_desc,resource_type,auth_type,sort_num) 
VALUES ('customer_index_switch', 'other', '客户首页-主页-跳转', 'home.do', NULL, '1', 'LOGIN_AUTH', 1);
INSERT INTO t_resource(resource_id,parent_id,resource_name,address_url,resource_desc,resource_type,auth_type,sort_num) 
VALUES ('index_switch', 'other', '主页-跳转', 'index!maicustomer_index_switchn.do', NULL, '1', 'LOGIN_AUTH', 1);


-- 模板类别管理
INSERT INTO t_resource_option VALUES ('formTemplateType_manage', '模板类型列表', 'formTemplateType!list.do');
INSERT INTO t_resource_option VALUES ('formTemplateType_manage', '新增模板类型', 'formTemplateType!insert.do');
INSERT INTO t_resource_option VALUES ('formTemplateType_manage', '修改模板类型', 'formTemplateType!update.do');
INSERT INTO t_resource_option VALUES ('formTemplateType_manage', '删除模板类型', 'formTemplateType!delete.do');

-- 模板管理
INSERT INTO t_resource_option VALUES ('formTemplateType_manage', '模板列表', 'formTemplate!listData.do');
INSERT INTO t_resource_option VALUES ('formTemplateType_manage', '新增模板', 'formTemplate!insert.do');
INSERT INTO t_resource_option VALUES ('formTemplateType_manage', '修改模板', 'formTemplate!update.do');
INSERT INTO t_resource_option VALUES ('formTemplateType_manage', '删除模板', 'formTemplate!delete.do');

INSERT INTO t_resource_option VALUES ('formTemplateType_manage', '填报录入', 'form!insert.do');

-- 填报统计
INSERT INTO t_resource_option VALUES ('formStatistics_manage', '模板信息树形', 'formStatistics!treeDataFormTempate.do');
INSERT INTO t_resource_option VALUES ('formStatistics_manage', '填报信息列表', 'formStatistics!displayExpDatas.do');
INSERT INTO t_resource_option VALUES ('formStatistics_manage', '填报信息导出', 'formStatistics!exportExcel.do');

INSERT INTO t_resource_option VALUES ('formTemplateType_manage', '获取角色字典', 'formTemplate!fetchRoles.do');
INSERT INTO t_resource_option VALUES ('formTemplateType_manage', '获取用户字典', 'formTemplate!fetchUsers.do');
INSERT INTO t_resource_option VALUES ('formTemplateType_manage', '获取项目列表', 'formTemplate!listProjects.do');
INSERT INTO t_resource_option VALUES ('formTemplateType_manage', '获取字典明细', 'formTemplate!listDictionarys.do');

INSERT INTO t_resource_option VALUES ('formTemplateType_manage', '获取xf数据', 'form!showXFTab.do');
INSERT INTO t_resource_option VALUES ('formTemplateType_manage', '获取项目明细', 'form!showDetails.do');

-- 邮件定时任务
INSERT INTO `t_schedule_task` VALUES ('DCFC_EmailReminder', '邮件定时任务', '每分钟调用一次', 'DCFC_EmailReminder', '邮件定时任务', '1 * * * * ?', 'com.chz.smartoa.taskScheduler.EmailReminder', NULL, NULL, NULL, 0, 0);









