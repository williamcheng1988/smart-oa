-- 初始化组织机构
insert into t_department(department_id,department_name,parent_id,level,status,sort,create_user,create_date,last_update_date) VALUES
('root','松特高薪','',1,1,1,'system',SYSDATE(),SYSDATE()),
('st_zjb','总经办','root',2,1,1,'system',SYSDATE(),SYSDATE()),
('st_xzzx','行政中心','root',2,1,2,'system',SYSDATE(),SYSDATE()),
('st_cwzx','财务核算中心','root',2,1,3,'system',SYSDATE(),SYSDATE()),
	('st_cwzx_001','财务会计部','st_cwzx',3,1,1,'system',SYSDATE(),SYSDATE()),
	('st_cwzx_002','采购部','st_cwzx',3,1,2,'system',SYSDATE(),SYSDATE()),
('st_yxzx','营销中心','root',2,1,4,'system',SYSDATE(),SYSDATE()),
	('st_yxzx_001','综合管理部','st_yxzx',3,1,1,'system',SYSDATE(),SYSDATE()),
	('st_yxzx_002','教仪营销部','st_yxzx',3,1,2,'system',SYSDATE(),SYSDATE()),
('st_jxzx','技术中心','root',2,1,5,'system',SYSDATE(),SYSDATE()),
	('st_jxzx_001','工程设计部','st_jxzx',3,1,1,'system',SYSDATE(),SYSDATE()),
	('st_jxzx_002','产品设计部','st_jxzx',3,1,2,'system',SYSDATE(),SYSDATE()),
('st_yjzx','硬件研发中心','root',2,1,6,'system',SYSDATE(),SYSDATE()),
	('st_yjzx_001','产品开发部','st_yjzx',3,1,1,'system',SYSDATE(),SYSDATE()),
('st_rjzx','软件平台开发中心','root',2,1,7,'system',SYSDATE(),SYSDATE()),
('st_zyzx','资源开发中心','root',2,1,8,'system',SYSDATE(),SYSDATE()),
('st_mooc','松大MOOC学院','root',2,1,9,'system',SYSDATE(),SYSDATE()),
('st_k12','K12课程开发事业部','root',2,1,10,'system',SYSDATE(),SYSDATE()),
('st_zhcs','智慧城市事业部','root',2,1,11,'system',SYSDATE(),SYSDATE()),
	('st_zhcs_001','工程施工部','st_zhcs',3,1,1,'system',SYSDATE(),SYSDATE()),
	('st_zhcs_002','售后维修部','st_zhcs',3,1,2,'system',SYSDATE(),SYSDATE()),
('st_zhxy','智慧校园事业部','root',2,1,12,'system',SYSDATE(),SYSDATE()),
	('st_zhxy_001','教仪施工部','st_zhxy',3,1,1,'system',SYSDATE(),SYSDATE()),
('st_zz','郑州分公司','root',2,1,13,'system',SYSDATE(),SYSDATE()),
	('st_zz_zjb','总经办','st_zz',3,1,1,'system',SYSDATE(),SYSDATE()),
	('st_zz_yxb','营销部','st_zz',3,1,2,'system',SYSDATE(),SYSDATE()),
	('st_zz_jsb','技术部','st_zz',3,1,3,'system',SYSDATE(),SYSDATE()),
	('st_zz_wk','万科项目部','st_zz',3,1,4,'system',SYSDATE(),SYSDATE()),
('st_nn','南宁分公司','root',2,1,14,'system',SYSDATE(),SYSDATE()),
('st_bj','北京分公司','root',2,1,15,'system',SYSDATE(),SYSDATE());

-- 初始化管理员用户 密码：888888
insert into t_staff
(login_name,real_name,password,mobile,email,status,create_user,create_date)
VALUES  
('stAdmin','系统管理员','21218cca77804d2ba1922c33e0151105','13724335201','315307864@qq.com',1,'admin',SYSDATE());

-- 初始化角色
insert into t_role
(role_name,role_type,role_desc,create_user,create_date,last_update_date,level,data_type)
values 
('董事长',1,'','admin', NOW(),NOW(),0,0),
('副董事长',1,'','admin', NOW(),NOW(),0,0),
('总经理',1,'','admin', NOW(),NOW(),0,0),
('系统管理员',1,'','admin', NOW(),NOW(),0,0),
('信息维护专员',1,'','admin', NOW(),NOW(),0,0),
('系统维护专员',1,'','admin', NOW(),NOW(),0,0),
('财务信息专员',1,'','admin', NOW(),NOW(),0,0),
('行政信息专员',1,'','admin', NOW(),NOW(),0,0),
('系统信息专员',1,'','admin', NOW(),NOW(),0,0);

-- 关联系统管理员角色
INSERT into t_staff_role(login_name,role_id)VALUES ('stAdmin',(SELECT role_id from t_role where role_name = '系统管理员'));

-- 初始化管理员权限
insert into t_role_resource
VALUES
((SELECT role_id from t_role where role_name = '系统管理员'),'role_manage'),
((SELECT role_id from t_role where role_name = '系统管理员'),'staff_manage');

