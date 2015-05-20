drop table IF EXISTS t_schedule_task;
drop table IF EXISTS t_operate_log;
drop table IF EXISTS t_department_role;
drop table IF EXISTS t_staff_role;
drop table IF EXISTS t_department_post_staff;
drop table IF EXISTS t_role_resource;
drop table IF EXISTS t_resource_option;
drop table IF EXISTS t_resource;
DROP TABLE IF EXISTS T_STAFF_BENEFITS;
DROP TABLE IF EXISTS T_STAFF_WAGES;
drop table IF EXISTS t_staff;
drop table IF EXISTS t_role;
drop table IF EXISTS t_department;
drop table IF EXISTS t_post;
drop table IF EXISTS t_delegation;
drop table IF EXISTS t_delegation_log;
drop table IF EXISTS  T_HI_TASK;
drop table IF EXISTS  T_RU_TASK;
drop table IF EXISTS  T_RE_CONF;
drop table IF EXISTS  T_RU_CONF;
drop table IF EXISTS  T_RE_PROCDEF;
drop table IF EXISTS  T_GE_EXECUTION;
drop table IF EXISTS  t_notice;

DROP TABLE IF EXISTS T_FORM_PROP;
DROP TABLE IF EXISTS T_FORM_RECORD;
DROP TABLE IF EXISTS T_FORM_TEMPLATE;
DROP TABLE IF EXISTS T_FORM_TEMPLATE_TYPE;

-- 删除已存在的表
DROP TABLE IF EXISTS T_DICTIONARY_CONFIG;
DROP TABLE IF EXISTS T_FILE_MANAGER;
DROP TABLE IF EXISTS T_FILE_GROUP;
DROP TABLE IF EXISTS T_AFFICHE_INFO;
DROP TABLE IF EXISTS T_CALENDAR;
DROP TABLE IF EXISTS T_WORK_PLAN;
DROP TABLE IF EXISTS T_ROLE_FILETYPE;

-- prompt creating t_department
create table t_department
(
  department_id    varchar(64) not null,
  department_name  varchar(100) not null,
  department_desc  varchar(100),
  parent_id        varchar(16),
  level            int(2),
  status           int(1) not null default 1,
  create_user      varchar(20) not null,
  create_date      datetime not null,
  last_update_date datetime
);
--  comment on table t_department is '组织表';
--  comment on column t_department.department_id is '组织ID';
--  comment on column t_department.department_name  is '组织名称';
--  comment on column t_department.department_desc is '组织描述';
--  comment on column t_department.parent_id is '父级组织ID';
--  comment on column t_department.level is '组织级别';
--  comment on column t_department.status is '状态：1有效 0无效';
--  comment on column t_department.email is '组织的邮件';
--  comment on column t_department.create_user is '组织创建者';
--  comment on column t_department.create_date is '组织创建时间';
--  comment on column t_department.last_update_date is '组织最后修改时间';
alter table t_department add primary key (department_id);

-- 岗位表
create table t_post
(
  post_id    varchar(64) not null,
  post_name  varchar(100) not null,
  create_user      varchar(20) not null,
  create_date      datetime not null,
  last_update_date datetime
);
alter table t_post add primary key (post_id);

-- 岗位关系表
create table t_department_post_staff
(
  id               int(10)  not null primary key auto_increment,
  dept_id          varchar(64) not null,
  post_id          varchar(64) not null,
  staff_ids        varchar(320) not null,
  create_user      varchar(20) not null,
  create_date      datetime not null,
  last_update_date datetime
);
-- ALTER TABLE T_DEPARTMENT_POST_STAFF modify column staff_ids varchar(320) not null 

create unique index idx_t_department_post_staff on t_department_post_staff (dept_id,post_id);
alter table t_department_post_staff add foreign key (dept_id) references t_department (department_id) on delete cascade on update cascade;
alter table t_department_post_staff add foreign key (post_id) references t_post (post_id) on delete cascade on update cascade;


-- prompt creating t_role
create table t_role
(
  role_id          int(10)  not null primary key auto_increment,
  role_name        varchar(100) not null,
  role_key         varchar(64),
  role_type 	   int(1),
  level            int(1) not null,
  data_type         int(1) ,
  role_desc        varchar(400),
  super_admin      varchar(1) default 'N',
  create_user      varchar(20) not null,
  create_date      datetime not null,
  last_update_date datetime
);
--  comment on table t_role is '角色表';
--  comment on column t_role.role_id is '角色ID';
--  comment on column t_role.role_name is '角色名称';
--  comment on column t_role.role_type is '角色类型 1：公司角色  2:项目角色 ';

--  comment on column t_role.role_key is '角色外码';
--  comment on column t_role.role_desc is '角色描述';
--  comment on column t_role.super_admin is '是否是超级管理员：Y：是 N:不是';
--  comment on column t_role.create_user is '角色创建者';
--  comment on column t_role.create_date is '角色创建时间';
--  comment on column t_role.last_update_date is '角色最后修改时间';
create unique index idx_t_role_name on t_role (role_name);

-- prompt creating t_staff
create table t_staff
(
  login_name           varchar(64) not null,
  department_id        varchar(16),
  real_name            varchar(100) not null,
  password             varchar(128) not null,
  status               varchar(20) not null,
  sex                  varchar(10),
  telephone            varchar(30),
  mobile               varchar(16),
  email                varchar(64),
  last_degree          varchar(10),
  speciality           varchar(100),
  address              varchar(300),
  zip                  varchar(8),
  create_user          varchar(16) not null,
  create_date          datetime not null,
  expire_date          datetime,
  last_update_date     datetime,
  password_expire_date datetime,
  lock_date            datetime,
  pay_hour             double,
  pay_base             double,
  pay_attendance       double,
  pay_hrcost           double,
  pwd_err_count        int(2) default 0
);
--  comment on table t_staff is '成员表';
--  comment on column t_staff.login_name is '登录名';
--  comment on column t_staff.department_id is '组织ID';
--  comment on column t_staff.real_name is '成员姓名';
--  comment on column t_staff.password is '密码（经过加密）';
--  comment on column t_staff.status is '成员状态：0：待激活，1：正常，2：锁定,3：注销';
--  comment on column t_staff.sex is '性别：MALE-男性；FEMALE-女性；UNKNOW-未知';
--  comment on column t_staff.telephone is '电话';
--  comment on column t_staff.mobile is '手机号码';
--  comment on column t_staff.email is '邮件地址';

--  comment on column t_staff.last_degree is '学历';
--  comment on column t_staff.speciality is '专业';
--  comment on column t_staff.address is '地址';
--  comment on column t_staff.zip is '邮编';

--  comment on column t_staff.create_user is '成员创建者';
--  comment on column t_staff.create_date is '成员创建时间';
--  comment on column t_staff.expire_date is '成员帐号过期时间';
--  comment on column t_staff.last_update_date is '成员最后修改时间';
--  comment on column t_staff.pwd_err_count is '密码连续输入错误的次数';

alter table t_staff add primary key (login_name);
alter table t_staff add foreign key (department_id) references t_department (department_id) on delete cascade on update cascade;

  
-- prompt creating t_resource
create table t_resource
(
  resource_id   varchar(100) not null,
  parent_id  varchar(100) not null,
  resource_name varchar(100) not null,
  address_url varchar(100),
  resource_desc varchar(100),
  resource_type varchar(20) not null,
  auth_type     varchar(10) not null,
  icon_name varchar(100),
  sort_num int default 9
)
;
--  comment on table t_resource is '资源表';
--  comment on column t_resource.resource_id is '资源ID';
--  comment on column t_resource.resource_key is '资源外码';
--  comment on column t_resource.resource_name is '资源名称';
--  comment on column t_resource.resource_desc is '资源描述';
--  comment on column t_resource.address_url is '资源地址';
--  comment on column t_resource.resource_type is '资源类型:1目录，2数据权限';
--  comment on column t_resource.icon_name is 'icon名称';
--  comment on column t_resource.auth_type is '鉴权类型(AUTH：鉴权，UNAUTH：不鉴权，LOGIN_AUTH：登录鉴权)'; 
alter table t_resource add primary key (resource_id);
alter table t_resource add constraint unq_t_resource_key unique (resource_id);

-- prompt creating t_resource_options
CREATE TABLE t_resource_option
(
	resource_id VARCHAR(100) NOT NULL,
	option_name VARCHAR(100) NOT NULL,
	address_url VARCHAR(100)  NOT NULL
);
--  comment on table t_resource_option is '资源操作表';
--  comment on column t_resource_option.resource_id is '资源ID';
--  comment on column t_resource_option.option_name is '操作名称';
--  comment on column t_resource_option.address_url is '操作地址';
alter table t_resource_option add foreign key (resource_id) references t_resource(resource_id) on delete cascade on update cascade;



-- prompt creating t_role_resource
create table t_role_resource
(
  role_id       varchar(64) not null,
  resource_key  varchar(100) not null
);
--  comment on table t_role_operation  is '角色资源关系表';
--  comment on column t_role_operation.role_id  is '角色ID';
--  comment on column t_role_operation.resource_key  is '资源key';
alter table t_role_resource add primary key (role_id,resource_key);


-- prompt creating t_staff_role
create table t_staff_role
(
  login_name      varchar(64) not null,
  role_id       varchar(64) not null,
  project_id    varchar(64)
);

--  comment on table t_staff_role  is '成员角色关系表';
--  comment on column t_staff_role.login_name  is '成员ID';
--  comment on column t_staff_role.role_id  is '角色ID';
alter table t_staff_role add foreign key (login_name) references t_staff (login_name) on delete cascade on update cascade;

-- prompt creating t_department_role
create table t_department_role
(
  department_id      varchar(64) not null,
  role_id       varchar(32) not null
);

--  comment on table t_staff_role  is '成员角色关系表';
--  comment on column t_staff_role.staff_id  is '成员ID';
--  comment on column t_staff_role.role_id  is '角色ID';
alter table t_department_role add primary key (department_id, role_id);
alter table t_department_role add foreign key (department_id) references t_department (department_id) on delete cascade on update cascade;


-- *********************************************************
-- 	操作日志表
-- *********************************************************
-- prompt creating t_operate_log
create table t_operate_log
(
  log_id           int not null auto_increment,
  log_type         varchar(32) not null,
  operator_id      varchar(64) not null,
  operator_name    varchar(64) not null,
  client_ip        varchar(100) not null,
  entity_id      varchar(64) not null,
  entity_name    varchar(64) not null,
  description      varchar(600),
  operate_result varchar(400) not null,
  operate_date   datetime not null,
  primary key (log_id)
);

--  comment on table t_operate_log  is '操作日志表';
--  comment on column t_operate_log.log_id  is '操作日志ID';
--  comment on column t_operate_log.log_type  is '日志类型:1.登录日志;2,操作日志';
--  comment on column t_operate_log.operator_id  is '操作员ID';
--  comment on column t_operate_log.operator_name  is '操作员名称';
--  comment on column t_operate_log.client_ip  is '客户端IP';
--  comment on column t_operate_log.entity_id  is '被操作实体ID';
--  comment on column t_operate_log.entity_name is '被操作实体名称';
--  comment on column t_operate_log.description  is '操作说明';
--  comment on column t_operate_log.operate_result  is '操作结果';
--  comment on column t_operate_log.operate_date  is '操作时间';
create index idx_t_operate_log_date on t_operate_log(operate_date);
create index idx_t_operate_log_opr_id on t_operate_log(operator_id);

create table t_schedule_task
(
  ID VARCHAR(100) not null,
  TASK_NAME     VARCHAR(100),
  TASK_DESC     VARCHAR(100),
  GROUP_ID      VARCHAR(20),
  GROUP_NAME    VARCHAR(100),
  EXPRESS       VARCHAR(20) not null,
  HANDLER_CLASS VARCHAR(100),
  PARAMETERS    VARCHAR(4000),
  CATEGORY      VARCHAR(2),
  STARTDATE     VARCHAR(10),
  STATUS        INT not null,
  DELETEFLAG    INT default 0
);
alter table t_schedule_task add primary key (ID);

-- Add comments to the table 
-- comment on table t_schedule_task is '任务管理表';
-- Add comments to the columns 
-- comment on column t_schedule_task.ID is '任务ID';
-- comment on column t_schedule_task.TASK_NAME  is '任务名称';
-- comment on column t_schedule_task.TASK_DESC is '任务说明';
-- comment on column t_schedule_task.GROUP_ID is '模块ID';
-- comment on column t_schedule_task.GROUP_NAME  is '模块名称';
-- comment on column t_schedule_task.EXPRESS is '表达式';
-- comment on column t_schedule_task.HANDLER_CLASS is '处理类';
-- comment on column t_schedule_task.PARAMETERS is '参数,示例:para1=1;param=abc';
-- comment on column t_schedule_task.STARTDATE is '生效日期';
-- comment on column t_schedule_task.STATUS is '状态(0:待启动;1:启动;2:失败;3:停止)';
-- comment on column t_schedule_task.DELETEFLAG  is '删除标记(0:未删除;1:删除)';


-- 流程定义表
create table T_RE_PROCDEF
(
	procdef_id_ VARCHAR(64) not NULL,
	name_ VARCHAR(64) not NULL,
	version_ int not NULL,
	complete_remind_ int not NULL,
	uplink_ int not NULL,
	manager_ VARCHAR(64),
	Important_grade_ Int not NULL,
	procdef_status_ Int not NULL,
	create_time_ datetime not NULL
);
alter table T_RE_PROCDEF add primary key (procdef_id_);

-- 流程定义配置表
create table T_RE_CONF
(
	conf_id_ Int(10) not null primary key auto_increment,
	sort_num_ int not null,
	procdef_id_ VARCHAR(64) not NULL,
	task_desc_ VARCHAR(64),
	action_type_ int not NULL,
	action_obj_ VARCHAR(64) not NULL,
	action_obj_type_ int not NULL,
	action_obj_src_ VARCHAR(64),
	is_turn_ int,
	is_ask_ int,
	is_modify_ int,
	expiry_days_ int,
	arrive_remind_ int,
	expiry_remind_ int,
	template_id_ VARCHAR(64)
);
alter table T_RE_CONF add foreign key (procdef_id_) references T_RE_PROCDEF (procdef_id_) on delete cascade on update cascade;

-- 流程实例表
create table T_GE_EXECUTION
(
	execution_id_ VARCHAR(64) not null,
	business_key_ VARCHAR(64) not null,
	business_title_ VARCHAR(100),
	project_id_ VARCHAR(100),
	owner_ VARCHAR(64) not null,
	department_id_ VARCHAR(100),
	template_id_ VARCHAR(200) not null,
	procdef_id_ VARCHAR(64),
	task_status_ int not null,
	task_num_ Int(2) not null,
	version_ Int(2) not null,
	priority_ int not null,
	desc_ varchar(4000),
	start_time_ datetime,
	end_time_ datetime
);
alter table T_GE_EXECUTION add primary key (execution_id_); 


-- 流程运行步骤表
create table T_RU_CONF
(
	conf_id_ Int(10) not null primary key auto_increment,
	sort_num_ int not null,
	execution_id_ VARCHAR(64) not NULL,
	task_desc_ VARCHAR(64),
	action_type_ int not NULL,
	action_obj_ VARCHAR(64) not NULL,
	action_obj_type_ int not NULL,
	action_obj_src_ VARCHAR(64),
	is_turn_ int,
	is_ask_ int,
	is_modify_ int,
	expiry_days_ int,
	arrive_remind_ int,
	expiry_remind_ int,
	template_id_ VARCHAR(64)
);
alter table T_RU_CONF add foreign key (execution_id_) references T_GE_EXECUTION (execution_id_) on delete cascade on update cascade;


-- 流程运行时任务数据表
create table T_RU_TASK
(
	task_id_  Int(10) not null primary key auto_increment ,
	execution_id_ VARCHAR(64) not null,
	assignee_ VARCHAR(64) not null,
	delegation_ VARCHAR(64),
	consult_ VARCHAR(64),
	conf_id_ int(10) not null,
	expiry_days_ int,
	arrive_time_ datetime
);
-- alter table T_RU_TASK add foreign key (execution_id_) references T_GE_EXECUTION (execution_id_) on delete cascade on update cascade;

-- 流程任务历史数据表
create table T_HI_TASK
(
	execution_id_ VARCHAR(64) not null,
	task_num_ Int(2) not null,
	conf_id_ Int(10),
	task_id_ int not null,
	version_ Int(2) not null,
	assignee_ VARCHAR(64) not null,
	delegation_ VARCHAR(64),
	record_type_ int not null,
	operate_result_ Int(2) not null,
	approve_type_ int(2),
	approve_desc_ VARCHAR(1000),
	start_time_ datetime,
	expiry_days_ VARCHAR(64),
	end_time_ datetime
);
-- alter table T_HI_TASK add foreign key (execution_id_) references T_GE_EXECUTION (execution_id_) on delete cascade on update cascade;


-- 授权表
create table t_delegation
(
  delegation_id varchar(64) not null primary key,
  from_user     varchar(50),
  from_username varchar(50),
  to_user       varchar(50),
  to_username   varchar(50),
  start_date    datetime,
  end_date      datetime,
  status        int(1), -- 状态：1有效
  createor      varchar(50),
  create_date   datetime
);

-- 授权处理记录
create table t_delegation_log
(
  log_id BIGINT auto_increment not null primary key,
  delegation_id varchar(64) not null,
  execution_id  varchar(64) not null,
  create_date   datetime not null, -- 到达时间
  deal_date datetime -- 处理时间：时间为null表示未处理
);

-- 消息通知表 
create table t_notice
(
  row_id  varchar(100) PRIMARY KEY not null,
  to_people    varchar(300) not null,
  cc_people  varchar(300),
  title  varchar(300) not null,
  content        TEXT,
  html_content   TEXT,
  station     int(2) not null,
  email      int(2) not null,
  sms     int(2) not null,
  create_time datetime not null
);
-- comment on column t_notice.to_people is '通知人';
-- comment on column t_notice.cc_people  is '抄送人';
-- comment on column t_notice.title is '标题';
-- comment on column t_notice.content is '内容';
-- comment on column t_notice.station  is '站内通知次数，-1 代表已通知成功';
-- comment on column t_notice.eamil is '邮件通知次数，-1 代表已通知成功';
-- comment on column t_notice.sms is '短信通知次数，-1 代表已通知成功';


-- -----------------------------------------------------------------------------
--  T_STAFF_WAGES 
-- -----------------------------------------------------------------------------
CREATE TABLE T_STAFF_WAGES(
	UUID VARCHAR(64)						COMMENT 'ID',
	ID VARCHAR(50)			 				COMMENT '编号',
	TOPIC VARCHAR(50)						COMMENT '标题',
	MONTH	VARCHAR(50)						COMMENT '发放月份',
	TEMPLATEID	VARCHAR(50)					COMMENT '模板ID',
	STATUS int								COMMENT '状态',
	PAY_BASE NUMERIC(11,2)            	COMMENT '基本工资  ',
	PAY_ATTENDANCE NUMERIC(11,2)  		COMMENT '考勤补贴  ',
	PENSION_INDIVIDUAL NUMERIC(11,2)    COMMENT '养老(个人)',
	PENSION_UNITS NUMERIC(11,2)         COMMENT '养老(单位)',
	HOUSING_INDIVIDUAL NUMERIC(11,2)    COMMENT '住房(个人)',
	HOUSING_UNITS NUMERIC(11,2)         COMMENT '住房(单位)',
	MEDICAL_PERSONAL NUMERIC(11,2)      COMMENT '医疗(个人)',
	MEDICAL_UNITS NUMERIC(11,2)         COMMENT '医疗(单位)',
	INJURY_PERSONAL NUMERIC(11,2)       COMMENT '工伤(个人)',
	INJURY_UNITS NUMERIC(11,2)          COMMENT '工伤(单位)',
	UNEMPLOYMENT_PERSONAL NUMERIC(11,2) COMMENT '失业(个人)',
	UNEMPLOYMENT_UNITS NUMERIC(11,2)    COMMENT '失业(单位)',
	FERTILITY_PERSONAL NUMERIC(11,2)    COMMENT '生育(个人)',
	FERTILITY_UNITS NUMERIC(11,2)       COMMENT '生育(单位)',
	INCOME_TAX NUMERIC(11,2)            COMMENT '所得税    ',
	ATTENDANCE_CHARGEBACK NUMERIC(11,2) COMMENT '考勤扣款  ',
	REAL_WAGES NUMERIC(11,2)            COMMENT '实发工资  ',
	REMARK	 VARCHAR(500) 				COMMENT '备注',
	FIELD_COST NUMERIC(11,2)			COMMENT '外勤费用',
	MONEY_WAGES NUMERIC(11,2)			COMMENT '现金工资',
	PAYHR_COST NUMERIC(11,2)			COMMENT '人力成本',
	CREATE_USER VARCHAR(10)   				COMMENT '创建人',
	CREATE_DATE DATETIME       				COMMENT '创建时间',
	UPDATE_USER VARCHAR(10)   				COMMENT '更新人',
	UPDATE_DATE DATETIME       				COMMENT '更新时间',
		CONSTRAINT PK_STAFF_WAGES PRIMARY KEY(UUID)
) ENGINE=INNODB COMMENT'员工工资总表';


-- -----------------------------------------------------------------------------
--  T_STAFF_BENEFITS 
-- -----------------------------------------------------------------------------
CREATE TABLE T_STAFF_BENEFITS(
	ID VARCHAR(64)			 			COMMENT 'ID',
	LOGIN_NAME	VARCHAR(64)				COMMENT '员工',
	REAL_NAME	VARCHAR(64)				COMMENT '员工真实姓名',
	PAY_BASE NUMERIC(11,2)            	COMMENT '基本工资  ',
	PAY_ATTENDANCE NUMERIC(11,2)  		COMMENT '考勤补贴  ',
	PENSION_INDIVIDUAL NUMERIC(11,2)    COMMENT '养老(个人)',
	PENSION_UNITS NUMERIC(11,2)         COMMENT '养老(单位)',
	HOUSING_INDIVIDUAL NUMERIC(11,2)    COMMENT '住房(个人)',
	HOUSING_UNITS NUMERIC(11,2)         COMMENT '住房(单位)',
	MEDICAL_PERSONAL NUMERIC(11,2)      COMMENT '医疗(个人)',
	MEDICAL_UNITS NUMERIC(11,2)         COMMENT '医疗(单位)',
	INJURY_PERSONAL NUMERIC(11,2)       COMMENT '工伤(个人)',
	INJURY_UNITS NUMERIC(11,2)          COMMENT '工伤(单位)',
	UNEMPLOYMENT_PERSONAL NUMERIC(11,2) COMMENT '失业(个人)',
	UNEMPLOYMENT_UNITS NUMERIC(11,2)    COMMENT '失业(单位)',
	FERTILITY_PERSONAL NUMERIC(11,2)    COMMENT '生育(个人)',
	FERTILITY_UNITS NUMERIC(11,2)       COMMENT '生育(单位)',
	INCOME_TAX NUMERIC(11,2)            COMMENT '所得税    ',
	ATTENDANCE_CHARGEBACK NUMERIC(11,2) COMMENT '考勤扣款  ',
	REAL_WAGES NUMERIC(11,2)            COMMENT '实发工资  ',
	REMARK	 VARCHAR(500) 				COMMENT '备注',
	FIELD_COST NUMERIC(11,2)			COMMENT '外勤费用',
	MONEY_WAGES NUMERIC(11,2)			COMMENT '现金工资',
	PAYHR_COST NUMERIC(11,2)			COMMENT '人力成本'
) ENGINE=INNODB COMMENT'员工工资明细表';

-- 流程共享信息视图：status：1.初始化，2.正常，3.失效
CREATE OR REPLACE VIEW view_process_public_info
AS
select execution_id_ executionId,
			 business_key_ businessKey,
			 business_title_ businessTitle,
			 project_id_ projectId,
			 owner_ owner,
			 CASE WHEN task_status_ < 2 THEN 1 WHEN task_status_ = 2 THEN 2 WHEN task_status_ > 2 THEN 3 END status
from t_ge_execution;


-- 流程参与者视图; status:0:暂存,1:审批中2:审批通过,3:审批不通过,4:任务作废
CREATE OR REPLACE VIEW view_process_user
AS
select e.execution_id_ executionId,e.OWNER_  loginName,e.task_status_ status from t_ge_execution e
UNION	
select e.execution_id_ executionId,c.action_obj_  loginName,e.task_status_ status
	from t_ge_execution e 
	LEFT JOIN t_re_conf c  on  e.procdef_id_ = c.procdef_id_
	where c.action_obj_type_ = 1 -- 用户
UNION
select e.execution_id_ executionId,sr.login_name loginName,e.task_status_ status
	from t_ge_execution e 
	LEFT JOIN t_re_conf c  on  e.procdef_id_ = c.procdef_id_
	LEFT JOIN t_role r on c.action_obj_ = r.role_id
	LEFT JOIN t_staff_role  sr on r.role_id = sr.role_id
	where e.business_key_ = sr.project_id
	and r.role_type = 2 -- 项目角色
	and c.action_obj_type_ = 2 -- 角色
UNION
select e.execution_id_ executionId,sr.login_name loginName,e.task_status_ status
	from t_ge_execution e 
	LEFT JOIN t_re_conf c  on  e.procdef_id_ = c.procdef_id_
	LEFT JOIN t_role r on c.action_obj_ = r.role_id
	LEFT JOIN t_staff_role  sr on r.role_id = sr.role_id
	where r.role_type = 1 -- 公司角色
	and c.action_obj_type_ = 2; -- 角色
	

-- 字典配置表
CREATE TABLE T_DICTIONARY_CONFIG 
(
  FILETYPE_ID int(11) NOT NULL AUTO_INCREMENT,
  DICTIONARY_NAME varchar(150) DEFAULT NULL,
  DICTIONARY_KEY varchar(100) DEFAULT NULL,
  ORDER_NO int(2) DEFAULT NULL,
  PARENT_ID int(11) DEFAULT NULL,
  DIC_TYPE int(2) DEFAULT NULL,
  IS_VALID int(2) DEFAULT NULL,
  COMPANY_NAME varchar(30) DEFAULT NULL,
  FILE_TYPE_NO varchar(20) DEFAULT NULL,
  CONFIG_TYPE int(2) DEFAULT NULL,
  SQL_VALUE varchar(1000) DEFAULT NULL,
  LEVEL int(11) DEFAULT NULL,
  IS_PUBLIC int(2) DEFAULT NULL,
  PRIMARY KEY (FILETYPE_ID)
);



-- 附件管理表
CREATE TABLE T_FILE_MANAGER 
(
  FILE_ID VARCHAR(100) NOT NULL ,
  FILE_NUMBER varchar(50) DEFAULT NULL,
  FILE_NAME varchar(100) DEFAULT NULL,
  FILE_DISPLAYNAME varchar(100) DEFAULT NULL,
  FILE_TYPE_ID int(11) DEFAULT NULL,
  FILE_TYPE_SUB_ID int(11) DEFAULT NULL,
  FILE_ADDRESS varchar(100) DEFAULT NULL,
  IS_PUBLIC int(2) DEFAULT NULL,
  IS_VALID int(2) DEFAULT NULL,
  VERSION int(2) DEFAULT NULL,
  FILE_FIX varchar(10) DEFAULT NULL,
  CREATE_USER varchar(64) DEFAULT NULL,
  CREATE_DT datetime DEFAULT NULL,
  UPDATE_USER varchar(64) DEFAULT NULL,
  UPDATE_DT datetime DEFAULT NULL,
  UPDATE_ID VARCHAR(100) DEFAULT NULL,
  PRIMARY KEY (FILE_ID)
);

-- create unique index idx_t_file_number on T_FILE_MANAGER (FILE_NUMBER);


CREATE TABLE t_file_group
(
	GROUP_ID   VARCHAR(100)  NOT NULL,
	FILE_ID    VARCHAR(100)  NOT NULL
);


CREATE TABLE T_AFFICHE_INFO 
(
  AFFICHE_ID int(11) NOT NULL AUTO_INCREMENT,
  AFFICHE_TITLE varchar(200) DEFAULT NULL,
  AFFICHE_CONTENT text,
  AFFICHE_STATUS int(2) DEFAULT NULL,
  START_LIFEDATE datetime DEFAULT NULL,
  END_LIFEDATE datetime DEFAULT NULL,
  CREATE_USER varchar(50) DEFAULT NULL,
  CREATE_DT datetime DEFAULT NULL,
  UPDATE_USER varchar(50) DEFAULT NULL,
  UPDATE_DT datetime DEFAULT NULL,
  PRIMARY KEY (AFFICHE_ID)
);


CREATE TABLE T_CALENDAR 
(
  C_ID int(11) NOT NULL AUTO_INCREMENT,
  DATE_SET datetime DEFAULT NULL,
  HOLIDAY_TYPE int(2) DEFAULT NULL,
  HOLIDAY_DESC varchar(4000) DEFAULT NULL,
  CREATE_USER varchar(50) DEFAULT NULL,
  CREATE_DT datetime DEFAULT NULL,
  UPDATE_USER varchar(50) DEFAULT NULL,
  UPDATE_DT datetime DEFAULT NULL,
  PRIMARY KEY (C_ID)
);

CREATE TABLE T_WORK_PLAN (
  W_ID int(11) NOT NULL AUTO_INCREMENT,
  WORK_DATE datetime DEFAULT NULL,
  WORK_TIME varchar(20) DEFAULT NULL,
  WORK_DESC varchar(4000) DEFAULT NULL,
  NOTICE_TYPE varchar(50) DEFAULT NULL,
  CREATE_USER varchar(50) DEFAULT NULL,
  CREATE_DT datetime DEFAULT NULL,
  UPDATE_USER varchar(50) DEFAULT NULL,
  UPDATE_DT datetime DEFAULT NULL,
  NOTICE_ID  varchar(100) DEFAULT NULL,
  PRIMARY KEY (W_ID)
);


CREATE TABLE T_ROLE_FILETYPE
(
  ROLE_ID    INT(10),
  FILE_ID    VARCHAR(100),  
  IS_SEE     INT(2)
);	


-- -----------------------------------------------------------------------------
--  form template type
-- -----------------------------------------------------------------------------
create table T_FORM_TEMPLATE_TYPE(
	-- ID BIGINT auto_increment 	COMMENT '表单模板类型ID',
	ID	VARCHAR(64)			COMMENT 'UUID',
	SEQUENCE INT				COMMENT '表单模板类型序号' DEFAULT 0,
	TYPE VARCHAR(128)        	COMMENT '表单模板类型',
	DESCRIPTION VARCHAR(128) 	COMMENT '表单模板类型描述',
	STATUS  INT       			COMMENT '表单模板类型状态: 0(正常)/1(过期)/2(草稿)/3(禁用)',
	CREATE_USER VARCHAR(10)   	COMMENT '创建人',
	CREATE_DATE DATETIME        COMMENT '创建时间',
	UPDATE_USER VARCHAR(10)   	COMMENT '更新人',
	UPDATE_DATE DATETIME       	COMMENT '更新时间',
        CONSTRAINT PK_FORM_TEMPLATE_TYPE PRIMARY KEY(ID)
) engine=innodb COMMENT'表单模板类型表';
-- ALTER TABLE T_FORM_TEMPLATE_TYPE ADD 	UUID	VARCHAR(64)			COMMENT 'UUID';

-- -----------------------------------------------------------------------------
--  form template
-- -----------------------------------------------------------------------------
create table T_FORM_TEMPLATE(
	ID VARCHAR(64)			 	COMMENT '表单模板ID',
	SEQUENCE INT				COMMENT '表单模板序号' DEFAULT 0,
	TYPE_ID VARCHAR(64)    		COMMENT '表单模板类型ID',
	NAME VARCHAR(128)        	COMMENT '表单模板名称',
	DESCRIPTION VARCHAR(128) 	COMMENT '表单模板描述',
	STATUS  INT       			COMMENT '表单模板状态: 0(正常)/1(过期)/2(草稿)/3(禁用)',
	CONTENT TEXT		    	COMMENT '表单模板内容',
	DOC_NO_RULE VARCHAR(64)  	COMMENT '文件编号规则',
	REP_NO_RULE VARCHAR(64)  	COMMENT '报表编号规则',
	PRO_NAME_RULE	VARCHAR(64)	COMMENT '项目名称规则',
	CREATE_USER VARCHAR(10)   	COMMENT '创建人',
	CREATE_DATE DATETIME       	COMMENT '创建时间',
	UPDATE_USER VARCHAR(10)   	COMMENT '更新人',
	UPDATE_DATE DATETIME       	COMMENT '更新时间',
	VIEW_URL	VARCHAR(1024)	COMMENT '视图路径' DEFAULT '/dynamicForm/form/flow-input-form.html',
	ATTACHMENT	VARCHAR(1024)	COMMENT '附件',
	HANDLE_CLASS	VARCHAR(64)	COMMENT '处理类' DEFAULT 'dynamicFormAdapter',
	DYNAMIC_FORM	VARCHAR(1)	COMMENT '是否动态表单' DEFAULT 'T',
	PROJECTFLAG	VARCHAR(1)		COMMENT '是否立项'	   DEFAULT 'F',
	PROCESS_NAME VARCHAR(64)   	COMMENT '流程名称',
	EXPORT_FLAG	VARCHAR(1)		COMMENT '可导出标志',
        CONSTRAINT PK_FORM_TEMPLATE PRIMARY KEY(ID),
        CONSTRAINT FK_FORM_TEMPLATE FOREIGN KEY(TYPE_ID) REFERENCES T_FORM_TEMPLATE_TYPE(ID)
) engine=innodb COMMENT'表单模板表';

-- ALTER TABLE t_form_template change PROCDEF_ID PROCESS_NAME VARCHAR(64)   	COMMENT '流程名称';
-- ALTER TABLE t_form_template ADD 	VIEW_URL	VARCHAR(1024)	COMMENT '视图路径';
-- ALTER TABLE t_form_template ADD 	ATTACHMENT	VARCHAR(1024)	COMMENT '附件';
-- ALTER TABLE t_form_template ADD 	HANDLE_CLASS	VARCHAR(1024)	COMMENT '处理类';
-- ALTER TABLE t_form_template ADD 	DYNAMIC_FORM	VARCHAR(1024)	COMMENT '是否动态表单';
-- ALTER TABLE t_form_template ALTER column DYNAMIC_FORM set default 'T';
-- ALTER TABLE t_form_template ALTER column HANDLE_CLASS set default 'dynamicFormAdapter';
-- ALTER TABLE t_form_template ALTER column VIEW_URL set default '/portal/dynamicForm/form/flow-update-form.html';
-- ALTER TABLE T_FORM_TEMPLATE ADD 	PRO_NAME_RULE	VARCHAR(64)	COMMENT '项目名称规则';
-- ALTER TABLE T_FORM_TEMPLATE ADD 	EXPORT_FLAG	VARCHAR(1)	COMMENT '可导出标志';


-- -----------------------------------------------------------------------------
--  keyvalue record
-- -----------------------------------------------------------------------------
CREATE TABLE T_FORM_RECORD(
        ID VARCHAR(64) 			COMMENT '表单记录ID (根据DOC_NO_RULE 生成文件编号)',
	UUID	VARCHAR(64)			COMMENT 'UUID',
	TITLE VARCHAR(128)			COMMENT '报表主题',
	ALIAS  VARCHAR(128)			COMMENT '报表别名',
	STATUS  INT       			COMMENT '表单记录状态: 0(正常)/1(过期)/2(草稿)/3(禁用)',
	COUNT	INT					COMMENT '记录数',
	CREATE_USER VARCHAR(10)   	COMMENT '创建人',
	CREATE_DATE DATETIME       	COMMENT '创建时间',
	UPDATE_USER VARCHAR(10)   	COMMENT '更新人',
	UPDATE_DATE DATETIME       	COMMENT '更新时间',
	FORM_TEMPLATE_ID VARCHAR(64) COMMENT '表单模板ID',
        CONSTRAINT PK_FORM_RECORD PRIMARY KEY(UUID),
		CONSTRAINT FK_FORM_RECORD_TEMPLATE FOREIGN KEY(FORM_TEMPLATE_ID) REFERENCES T_FORM_TEMPLATE(ID)
) engine=innodb COMMENT'表单记录表';
-- ALTER TABLE T_FORM_RECORD ADD 	COUNT	INT	COMMENT '记录数';
CREATE UNIQUE INDEX FORM_RECORD_PID ON T_FORM_RECORD(ID);


-- -----------------------------------------------------------------------------
--  keyvalue property
-- -----------------------------------------------------------------------------
CREATE TABLE T_FORM_PROP(
        ID BIGINT auto_increment COMMENT '表单数据ID',
	NAME VARCHAR(200)            COMMENT '元素name',
	VALUE VARCHAR(200)           COMMENT '元素数据',
	INNER_NAME VARCHAR(200)     COMMENT '所属父元素 ',
	FORM_RECORD_ID VARCHAR(64)   COMMENT '表单记录ID',
        CONSTRAINT PK_KV_PROP PRIMARY KEY(ID)
       -- CONSTRAINT FK_KV_PROP_RECORD FOREIGN KEY(FORM_RECORD_ID) REFERENCES T_FORM_RECORD(UUID)
) engine=innodb COMMENT'表单数据表';
-- ALTER TABLE T_FORM_PROP ADD 	PARENT_CODE	VARCHAR(200)	COMMENT '所属父元素 ';
-- ALTER TABLE T_FORM_PROP change CODE NAME VARCHAR(200)   	COMMENT '内部name';
-- ALTER TABLE T_FORM_PROP change PARENT_CODE INNER_NAME VARCHAR(200)   	COMMENT '内部name';

