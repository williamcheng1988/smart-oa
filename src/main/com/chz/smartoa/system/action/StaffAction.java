package com.chz.smartoa.system.action;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.util.DigestUtils;

import com.chz.smartoa.common.base.BaseAction;
import com.chz.smartoa.common.base.DataGrid;
import com.chz.smartoa.common.util.CookieUtil;
import com.chz.smartoa.common.util.LoginUtils;
import com.chz.smartoa.global.ResourceMgr;
import com.chz.smartoa.system.constant.OperateLogType;
import com.chz.smartoa.system.exception.PasswordMustChangeException;
import com.chz.smartoa.system.exception.StaffLockException;
import com.chz.smartoa.system.exception.StaffLoginNameErrorException;
import com.chz.smartoa.system.exception.StaffPasswordErrorException;
import com.chz.smartoa.system.pojo.Permission;
import com.chz.smartoa.system.pojo.Resource;
import com.chz.smartoa.system.pojo.Role;
import com.chz.smartoa.system.pojo.Staff;
import com.chz.smartoa.system.service.OperateLogBiz;
import com.chz.smartoa.system.service.StaffBiz;
import com.chz.smartoa.system.service.impl.StaffBizImpl;
import com.chz.smartoa.task.pojo.HiTaskVo;
import com.chz.smartoa.task.pojo.RuTaskVo;

public class StaffAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(StaffAction.class);

	private String loginName;

	private Staff staff = new Staff();

	private List<Staff> staffs;

	private List<Resource> resources = new ArrayList<Resource>();

	private List<Role> roles;

	private StaffBiz staffBiz;

	private String roleIds;

	private String password;

	private String newPassword;

	private OperateLogBiz operateLogBiz;

	//待办任务列表
	private List<RuTaskVo> todoList;
	private int todoCount;
	// 跟踪任务列表
	private List<HiTaskVo> historyList;

	/**
	 * 登录
	 * @return
	 * @throws IOException
	 */
	public String login() throws IOException {
		// 参数检查
		if (StringUtils.isEmpty(this.staff.getLoginName())|| StringUtils.isEmpty(this.staff.getPassword())) {
			StaffAction.logger.error("用户名或密码为空");
			this.operateLogBiz.info(OperateLogType.STAFF_LOGIN,this.staff.getLoginName(),this.staff.getLoginName(), "登录失败");
			this.operateResult = new OperateResult(-4, "用户名或密码为空!");
			return BaseAction.OPER_RESULT;
		}
		// 登录
		try {
			this.staff = this.staffBiz.login(this.staff.getLoginName(), this.staff.getPassword());
		} catch (StaffLoginNameErrorException e) {
			String name = this.staff.getLoginName();
			this.operateLogBiz.info(OperateLogType.STAFF_LOGIN, name, name, "登录失败，用户名错误!");
			this.operateResult = new OperateResult(-1, "登录失败，用户名错误!");
			return BaseAction.OPER_RESULT;
		} catch (StaffPasswordErrorException e) {
			int cnt = this.staffBiz.updatePwdErrCount(this.staff.getLoginName());
			this.operateLogBiz.info(OperateLogType.STAFF_LOGIN, this.staff.getLoginName(),this.staff.getLoginName(), "登录失败，密码错误!");
			String msg = "密码错误!";
			if(cnt >= 2){
				msg += "还可尝试登录"+(StaffBizImpl.PWD_LOCK_COUNT-cnt)+"次，"+(StaffBizImpl.PWD_LOCK_COUNT-cnt)+"次后账号将被锁定!";
			}
			this.operateResult = new OperateResult(-1, msg);
			return BaseAction.OPER_RESULT;
		} catch (StaffLockException e) {
			String msg = "";
			if("0".equals(e.getCode())){
				msg = "账号审核中，请耐心等待...";
			}else if("2".equals(e.getCode())){
				msg = "登录错误次数超限，账号已被锁定，请联系管理员!";
			}else if("3".equals(e.getCode())){
				msg = "账号已被注销!";
			}else{
				msg = "未知用户状态!";
			}
			this.operateLogBiz.info(OperateLogType.STAFF_LOGIN, this.staff.getLoginName(),this.staff.getLoginName(), "登录失败，"+msg);
			this.operateResult = new OperateResult(-2,msg);
			return BaseAction.OPER_RESULT;
		} catch (PasswordMustChangeException e) {
			// 用户信息保存在会话
			this.staff = this.staffBiz.findStaffByLoginName(this.staff.getLoginName());
			// 不写进session
			// setLoginStaff(staff);
			this.operateLogBiz.info(OperateLogType.STAFF_LOGIN, this.staff.getLoginName(),this.staff.getLoginName(), "登录失败");
			// 跳转到修改密码页
			return "beforeChangePassword";
		} catch (Exception e) {
			StaffAction.logger.error("系统繁忙，请稍后再试...", e);
			this.operateResult = new OperateResult(-3, "系统繁忙，请稍后再试...");
			return BaseAction.OPER_RESULT;
		}
		this.setLoginStaff(this.staff);
		if ("true".equals(this.getHttpServletRequest().getParameter("rememberPass"))) {
			//写入cookie
			CookieUtil.saveLoginCookie(this.staff, this.getHttpServletResponse());
		}
		// 写业务日志
		this.operateLogBiz.info(OperateLogType.STAFF_LOGIN, this.staff.getLoginName(),this.staff.getRealName(), "登录成功");
		this.operateResult = new OperateResult(1, "sucess");
		return BaseAction.OPER_RESULT;
	}


	public String main() throws SQLException, IOException, ClassNotFoundException{
		initMenu();
		return "index";
	}

	public String basicHome() throws SQLException, IOException, ClassNotFoundException{
		initMenu();
		return "basic_home";
	}
	
	private void initMenu() throws SQLException, IOException, ClassNotFoundException{
		// 得到登录用户信息
		this.staff = this.getLoginStaff();
		// 查询系统所有操作资源
		this.resources = ResourceMgr.getInstance().deepCopy();

		String loginName = LoginUtils.getLoginStaff().getLoginName();
		// 查询用户可操作资源ID
		List<String> resourceIds = this.staffBiz.listResourceIds(LoginUtils.getLoginStaff().getLoginName());
		// 过滤用户权限
		this.filterResource(this.resources, resourceIds);

		// 查询用户数据权限
//		int permission = this.staffBiz.getHasAllDataPermission(loginName);
//		if (permission == 1) {// 查看所有数据权限
//			LoginUtils.getLoginStaff().getPermission().getDataPermissions()
//					.put(Permission.ALL_DATA, 1);
//		} else {
//			List<String> projectIds = this.staffBiz.getOnwerProjectId(loginName);
//			if ((projectIds != null) && (projectIds.size() > 0)) {
//				LoginUtils.getLoginStaff().getPermission().getDataPermissions().put(Permission.PROJECT_IDS, projectIds);
//			}
//			LoginUtils.getLoginStaff().getPermission().getDataPermissions().put(Permission.LOGIN_NAME, loginName);
//		}
	}

	private void filterResource(List<Resource> resources,List<String> resourceIds){
		if((resources != null) && (resourceIds != null)){
			for (Resource r : resources) {
				if(!"1".equals(r.getResourceType())){
					continue;
				}
				if(resourceIds.contains(r.getResourceId())||!Resource.AUTH_TYPE_AUTH.equals(r.getAuthType())){
					r.setChecked(true);
					this.filterResource(r.getResources(),resourceIds);
				}else if((r.getResources() != null) &&(r.getResources().size() > 0)){
					this.filterResource(r.getResources(),resourceIds);
				}
			}
		}
	}

	/**
	 * 退出登录
	 *
	 * @return
	 */
	public String logout() {
		// 删除会话信息
		this.getSession().invalidate();// 清空session
		if (this.getHttpServletRequest().getCookies() != null) {
			for (Cookie cookie : this.getHttpServletRequest().getCookies()) {
				if ("JSESSIONID".equals(cookie.getName().toUpperCase())) {
					cookie.setMaxAge(0);// 让cookie过期
					break;
				}
			}
		}
		CookieUtil.clearCookie(this.getHttpServletResponse());
		// 跳转到首页
		return "logout";
	}

	/**
	 * 修改密码准备
	 *
	 * @return
	 */
	public String beforeChangePassword() {
		// 得到登录用户信息
		this.staff = this.getLoginStaff();
		// 跳转到首页
		return "beforeChangePassword";
	}

	/**
	 * 修改密码.
	 * @return
	 */
	public String resetPwd() {
		// 得到登录用户信息
		this.staff = this.getLoginStaff();
		if(this.staff.getPassword().equals(DigestUtils.md5DigestAsHex(this.password.getBytes()))){//允许修改密码
			Staff s = new Staff();
			s.setLoginName(this.staff.getLoginName());
			String md5Password = DigestUtils.md5DigestAsHex(this.newPassword.getBytes());
			s.setPassword(md5Password);
			this.staffBiz.updateStaff(s);
			this.operateLogBiz.info(OperateLogType.STAFF_MANAGE, this.staff.getLoginName(),this.staff.getRealName(), "修改密码成功!");
			this.operateResult = new OperateResult(1, "修改密码成功!");
		}else{
			this.operateResult = new OperateResult(-1, "[当前密码]验证不通过!");
		}
		return BaseAction.OPER_RESULT;
	}

	/**
	 * 列表用户
	 *
	 * @return
	 */
	public String list() {
		// 如果是分页查询，调用基类方法设置分页属性（start,limit,sort,order等）
		this.setPagination(this.staff);
		// 得到分页结果
		this.staffs = this.staffBiz.listStaff(this.staff);
		// 封装数据
		this.dataGrid = new DataGrid(this.staffBiz.listStaffCount(this.staff), this.staffs);
		return BaseAction.DATA_GRID;
	}

	/**
	 * 通讯录
	 * @return
	 */
	public String contacts() {
		this.staffs = this.staffBiz.listStaff(this.loginName);
		// 封装数据
		this.dataGrid = new DataGrid(this.staffs.size(), this.staffs);
		return BaseAction.DATA_GRID;
	}

	public String beforeInsert(){
		this.roles = this.staffBiz.getRoles(new Role());
		return "beforeInsert";
	}

	/**
	 * 用户注册
	 * @return
	 * @throws Exception
	 */
	public String regist() throws Exception {
		if ((null == this.staff) || StringUtils.isBlank(this.staff.getLoginName())) {
			this.operateResult = new OperateResult(-1, "参数非法!");
			return BaseAction.OPER_RESULT;
		}
		try {
			// 检查名称是否重复
			Staff tmpStaff = this.staffBiz.findStaffByLoginName(this.staff.getLoginName());
			if (tmpStaff != null) {
				this.operateLogBiz.info(OperateLogType.STAFF_MANAGE, tmpStaff.getLoginName(),tmpStaff.getLoginName(), "用户注册失败：该登录名己存在!");
				this.operateResult = new OperateResult(-2, "登录名己存在!");
				return BaseAction.OPER_RESULT;
			}
			this.staff.setCreateUser("regist");
			this.staff.setStatus(Staff.STAFF_STATUS_INACTIVE);

			// 增加用户
			this.staffBiz.insertStaff(this.staff,null);
		} catch (Exception e) {
			StaffAction.logger.error(e);
			this.operateResult = new OperateResult(-3, "用户注册失败!");
			return BaseAction.OPER_RESULT;
		}
		this.operateResult = new OperateResult(1, "注册成功!请耐心等待审核，审核结果将以邮件告知，请注意查收!");
		return BaseAction.OPER_RESULT;
	}


	/**
	 * 增加用户
	 * @return
	 * @throws Exception
	 */
	public String insert() throws Exception {
		if(this.isdo != 1){
			return this.beforeInsert();
		}
		if ((null == this.staff) || StringUtils.isBlank(this.staff.getLoginName())) {
			this.operateResult = new OperateResult(-1, "参数非法!");
			return BaseAction.OPER_RESULT;
		}
		try {
			// 检查名称是否重复
			Staff tmpStaff = this.staffBiz.findStaffByLoginName(this.staff.getLoginName());
			if (tmpStaff != null) {
				this.operateLogBiz.info(OperateLogType.STAFF_MANAGE, tmpStaff.getLoginName(),tmpStaff.getLoginName(), "新增用户失败：该登录名己存在!");
				this.operateResult = new OperateResult(-2, "成员名称己存在!");
				return BaseAction.OPER_RESULT;
			}
			Staff loginStaff = this.getLoginStaff();
			this.staff.setPassword("good888");
			this.staff.setCreateUser(loginStaff.getLoginName());
			// 增加用户
			this.staffBiz.insertStaff(this.staff,this.roleIds);
		} catch (Exception e) {
			StaffAction.logger.error(e);
			this.operateResult = new OperateResult(-3, "增加用户失败!");
			return BaseAction.OPER_RESULT;
		}
		this.operateResult = new OperateResult(1, "新增用户成功!");
		return BaseAction.OPER_RESULT;
	}

	/**
	 * 修改成员
	 * @return
	 */
	public String beforeUpdate() {
		// 查询成员
		this.staff = this.staffBiz.findStaffByLoginName(this.loginName);
		if (this.staff == null) {
			this.operateResult = new OperateResult(1, "用户不存在!");
			return BaseAction.OPER_RESULT;
		}
		Role role_temp = new Role();
		role_temp.setLimit(50);
		this.roles = this.staffBiz.getRoles(role_temp);
		if((this.roles!=null) && (this.staff.getRoles()!=null)){
			for (Role role : this.roles) {
				if(this.staff.getRoles().contains(role)){
					role.setChecked(true);
				}
			}
		}
		return "beforeUpdate";
	}

	/**
	 * 修改成员
	 * @return
	 * @throws Exception
	 */
	public String update() throws Exception {
		if(this.isdo != 1){
			return this.beforeUpdate();
		}
		try {
			// 修改成员
			if(this.roleIds==null){
				this.roleIds = "";
			}
			this.staffBiz.updateStaff(this.staff,this.roleIds);
		} catch (Exception e) {
			this.operateLogBiz.info(OperateLogType.STAFF_MANAGE, this.staff.getLoginName(),this.staff.getRealName(), "用户修改失败!");
			this.operateResult = new OperateResult(-1, "修改用户失败!");
			return BaseAction.OPER_RESULT;
		}
		this.operateResult = new OperateResult(1, "修改用户成功!");
		return BaseAction.OPER_RESULT;
	}

	/**
	 * 查看个人资料
	 * @return
	 * @throws Exception
	 */
	public String staffInfo() throws Exception {

		// 查询成员
		this.staff = LoginUtils.getLoginStaff();
		if (this.staff == null) {
			this.operateResult = new OperateResult(1, "用户不存在!");
			return BaseAction.OPER_RESULT;
		}
		Role role_temp = new Role();
		role_temp.setLimit(50);
		List<Role> roleList = this.staffBiz.getRoles(role_temp);
		if ((roleList != null) && (this.staff.getRoles() != null)) {
			this.roles = new ArrayList<Role>();
			for (Role role : roleList) {
				if (this.staff.getRoles().contains(role)) {
					this.roles.add(role);
				}
			}
		}
		return "view";
	}

	/**
	 * 删除成员
	 *
	 * @return
	 */
	public String delete() {
		String[] loginNames;
		// 修改组织或员工角色
		if((this.loginName!=null)&&!"".equals(this.loginName)){
			loginNames = this.loginName.split(",");
		}else{
			this.operateResult = new OperateResult(-1, "请选择要注销的用户!");
			return BaseAction.OPER_RESULT;
		}
		this.staffBiz.deleteStaffs(loginNames);
		this.operateResult = new OperateResult(1, "用户注销成功!");
		//this.operateLogBiz.info(OperateLogType.STAFF_MANAGE, this.loginName,this.loginName, "用户注销成功！");
		return BaseAction.OPER_RESULT;
	}

	/**
	 * 重置密码
	 * @return
	 */
	public String reset() {
		String[] loginNames;
		if((this.loginName!=null)&&!"".equals(this.loginName)){
			loginNames = this.loginName.split(",");
		}else{
			this.operateResult = new OperateResult(-1, "请选择要重置密码的账户！");
			return BaseAction.OPER_RESULT;
		}
		String result  = this.staffBiz.resetPwds(loginNames);
		this.operateLogBiz.info(OperateLogType.STAFF_MANAGE,this.loginName,this.loginName, "重置密码成功！");
		this.operateResult = new OperateResult(1, "密码重置成功！新密码如下：\n"+result);
		return BaseAction.OPER_RESULT;
	}

	/**
	 * 加锁
	 * @return
	 */
	public String lock() {

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setHeader("Content-Type", "text/html;charset=UTF-8");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		// 查询成员
		this.staff = this.staffBiz.findStaffByLoginName(this.loginName);
		if (this.staff == null) {
			this.operateLogBiz.info(OperateLogType.STAFF_MANAGE, this.loginName,
					this.loginName, "用户锁定成功！");
			try {
				response.getWriter().print("成员不存在！");
			} catch (IOException ex) {
				StaffAction.logger.error("输出数据到页面时出错！");
			}
			return null;
		}

		// 判断用户状态是否正常
		if (Staff.STAFF_STATUS_NORMAL.equals(this.staff.getStatus())) {
			this.staff.setStatus(Staff.STAFF_STATUS_LOCK);

			Date now = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String lockDate = df.format(now);
			this.staff.setLockDate(lockDate);

			this.staffBiz.updateStaff(this.staff,null);
			this.operateLogBiz.info(OperateLogType.STAFF_MANAGE, this.loginName, this.staff.getRealName(), "锁定成功");
			this.operateLogBiz.info(OperateLogType.STAFF_MANAGE, this.loginName,
					this.loginName, "用户注销成功！");
		} else {
			// setErrorMsg("成员状态不是正常状态，不能加锁!");
			this.operateLogBiz.info(OperateLogType.STAFF_MANAGE, this.loginName, this.staff.getRealName(), "锁定失败");
			try {
				response.getWriter().print("成员状态不是正常状态，不能加锁！");
			} catch (IOException ex) {
				StaffAction.logger.error("输出数据到页面时出错！");
			}
			return null;
		}

		try {
			response.getWriter().print("成员加锁成功！");
		} catch (IOException ex) {
			StaffAction.logger.error("输出数据到页面时出错！");
		}

		return null;
	}

	/**
	 * 解锁
	 *
	 * @return
	 */
	public String unlock() {

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setHeader("Content-Type", "text/html;charset=UTF-8");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		// 查询成员
		this.staff = this.staffBiz.findStaffByLoginName(this.loginName);
		if (this.staff == null) {
			this.operateLogBiz.info(OperateLogType.STAFF_MANAGE, this.loginName, this.loginName, "解锁失败");
			try {
				response.getWriter().print("成员不存在！");
			} catch (IOException ex) {
				StaffAction.logger.error("输出数据到页面时出错！");
			}
			return null;
		}

		// 判断用户状态是否正常
		if (Staff.STAFF_STATUS_LOCK.equals(this.staff.getStatus())) {
			this.staff.setStatus(Staff.STAFF_STATUS_NORMAL);
			this.staff.setPwdErrCount("0");
			this.staffBiz.updateStaff(this.staff,this.roleIds);
			this.operateLogBiz.info(OperateLogType.STAFF_MANAGE, this.loginName, this.staff.getRealName(), "解锁成功");
		} else {
			this.operateLogBiz.info(OperateLogType.STAFF_MANAGE, this.loginName, this.staff.getRealName(), "解锁失败");
			try {
				response.getWriter().print("成员状态不是加锁状态，不能解锁！");
			} catch (IOException ex) {
				StaffAction.logger.error("输出数据到页面时出错！");
			}
			return null;
		}

		try {
			response.getWriter().print("成员解锁成功！");
		} catch (IOException ex) {
			StaffAction.logger.error("输出数据到页面时出错！");
		}
		return null;
	}

	public String redirect() {
		return "main";
	}

	public Staff getStaff() {
		return this.staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public void setStaffBiz(StaffBiz staffBiz) {

		this.staffBiz = staffBiz;
	}
	public List<Staff> getStaffs() {

		return this.staffs;
	}
	public List<Resource> getResources() {
		return this.resources;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public void setOperateLogBiz(OperateLogBiz operateLogBiz) {
		this.operateLogBiz = operateLogBiz;
	}
	public OperateResult getResult() {
		return this.operateResult;
	}
	public void setResult(OperateResult result) {
		this.operateResult = result;
	}
	public List<Role> getRoles() {
		return this.roles;
	}
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	public List<RuTaskVo> getTodoList() {
		return this.todoList;
	}
	public int getTodoCount() {
		return this.todoCount;
	}
	public List<HiTaskVo> getHistoryList() {
		return this.historyList;
	}
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getNewPassword() {
		return this.newPassword;
	}
}

