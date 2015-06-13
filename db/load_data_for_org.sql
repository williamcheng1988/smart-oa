-- 添加菜单
insert into t_resource
(resource_id,parent_id,resource_name,address_url,resource_desc,resource_type,auth_type,sort_num)
VALUES
('department_manager','system_setting','部门管理','department/list.html',null,1,'AUTH',9);

insert into t_resource
(resource_id,parent_id,resource_name,address_url,resource_desc,resource_type,auth_type,sort_num)
VALUES
('post_manager','system_setting','岗位管理','departmentPost/list.html',null,1,'AUTH',10);

-- 加载部门菜单
insert into t_resource
(resource_id,parent_id,resource_name,address_url,resource_desc,resource_type,auth_type,sort_num)
VALUES
('department_menu','other','加载部门配置菜单','departmentManager!genDepartmentTree.do',null,1,'LOGIN_AUTH',1);


update t_resource set address_url='staff_dept/list.html' where resource_name='用户管理';

-- 重新插入用户管理
-- insert into t_resource
-- (resource_id,parent_id,resource_name,address_url,resource_desc,resource_type,auth_type,sort_num)
-- values
-- ('staff_manage','system_setting','用户管理','staff_dept/list.html',null,1,'AUTH',1);


-- 添加访问权限sql
-- insert into t_resource_option values('department_manager','加载部门配置菜单','departmentManager!genDepartmentTree.do');
insert into t_resource_option values('department_manager','点击左侧部门菜单获取子项列表','departmentManager!queryDepartment.do');
insert into t_resource_option values('department_manager','跳转新增部门界面','departmentManager!insetDepartment.do');
insert into t_resource_option values('department_manager','跳转修改部门界面','departmentManager!toEditPage.do');
insert into t_resource_option values('department_manager','保存新增部门信息','departmentManager!saveDepartment.do');
insert into t_resource_option values('department_manager','保存修改后的部门信息','departmentManager!saveUpdate.do');
insert into t_resource_option values('department_manager','获取所有部门信息','departmentManager!getAllDepartment.do');
insert into t_resource_option values('department_manager','获取部门信息','departmentManager!getDepartmentInfo.do');

insert into t_resource_option values('post_manager','获取用户所属部门列表','departmentPostRelation!queryDepartmentPost.do');
insert into t_resource_option values('post_manager','保存新定义的岗位','postManager!savePost.do');
insert into t_resource_option values('post_manager','获取所有岗位名称','postManager!getAllPost.do');
insert into t_resource_option values('post_manager','新增用户岗位界面','departmentPostRelation!insetDepartmentPostPage.do');
insert into t_resource_option values('post_manager','保存用户岗位','departmentPostRelation!saveDepartmentPost.do');
insert into t_resource_option values('post_manager','修改用户岗位信息','departmentPostRelation!toEditPage.do');
insert into t_resource_option values('post_manager','保存修改后的用户岗位信息','departmentPostRelation!saveUpdate.do');



-- 初始化部门sql
INSERT into t_department(department_id,department_name,department_desc,parent_id,level,status,create_user,create_date,last_update_date)
VALUES('root','松大科技','','',1,1,'system',SYSDATE(),SYSDATE());


update t_staff set department_id='root';