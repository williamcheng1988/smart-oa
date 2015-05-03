package com.chz.smartoa.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.chz.smartoa.system.constant.OperateLogType;
import com.chz.smartoa.system.dao.DepartmentDao;
import com.chz.smartoa.system.dao.RoleDao;
import com.chz.smartoa.system.dao.StaffDao;
import com.chz.smartoa.system.exception.DepartmentHasChildException;
import com.chz.smartoa.system.exception.DepartmentHasStaffException;
import com.chz.smartoa.system.exception.DepartmentNotFoundException;
import com.chz.smartoa.system.pojo.Department;
import com.chz.smartoa.system.pojo.Staff;
import com.chz.smartoa.system.service.DepartmentBiz;
import com.chz.smartoa.system.service.OperateLogBiz;

public class DepartmentBizImpl implements DepartmentBiz {
    /**
     * logger.
     */
    private static final Logger logger = Logger.getLogger(DepartmentBizImpl.class);
    
	DepartmentDao departmentDao;
	StaffDao staffDao;
	RoleDao roleDao;
	OperateLogBiz operateLogBiz;

	public void setOperateLogBiz(OperateLogBiz operateLogBiz) {
		this.operateLogBiz = operateLogBiz;
	}

	public void setStaffDao(StaffDao staffDao) {
		this.staffDao = staffDao;
	}

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	public void assignRole(String id, String[] roleIds) {
		departmentDao.assignRole(id, roleIds);
	}

	public void deleteDepartment(String id)  
			throws DepartmentHasChildException, 
			DepartmentHasStaffException ,
			DepartmentNotFoundException{
		// 检查是否有下级组织
		Department department = new Department();
		department.setParentId(id);
		int count = departmentDao.listDepartmentCount(department);
		if (count > 0) {
			throw new DepartmentHasChildException("有下级组织,不允许删除");
		}
		
		// 检查是否有成员
		Staff staff = new Staff();
		staff.setDepartmentId(id);
		count = staffDao.listStaffCount(staff);
		if (count > 0) {
			throw new DepartmentHasStaffException("有成员，不允许删除");
		}
		
		// 查询组织存不存在
		department = departmentDao.findDepartment(id);
		
		// 不存在
		if (department == null) {
			throw new DepartmentNotFoundException("组织不存在");			
		}
		
		
		// 删除相应的组织
		departmentDao.deleteDepartment(id);
		
		// 删除关联角色
		roleDao.deleteDepartmentRole(id);
		
		// 写业务日志
		operateLogBiz.info(OperateLogType.DEPARTMENT_MANAGE, 
				department.getDepartmentId(), 
				department.getDepartmentName(), "删除成功");		
	}

	public Department findDepartment(String id) {
		Department department =  departmentDao.findDepartment(id);
		if (department != null) {
			Department parentDepartment =  departmentDao.findDepartment(department.getParentId());
//			department.setParentDepartment(parentDepartment);
		}
		return department;
	}

	public Department findDepartmentByName(String name) {
		return departmentDao.findDepartmentByName(name);
	}

	public String insertDepartment(Department department) {
		String id =  departmentDao.insertDepartment(department);
		
		// 写业务日志
		operateLogBiz.info(OperateLogType.DEPARTMENT_MANAGE, id, department.getDepartmentName(), "新增成功");
		
		return id;
	}

	public List listDepartment(Department department) {
		return departmentDao.listDepartment(department);
	}
	
	public List listRecursiveDepartment(Department department) {
		List departments =  departmentDao.listRecursiveDepartment(department);

		if (departments == null) {
			return null;
		}
		
		// 建立父组织与子组织的关系
        // 得到第一级组织
        List firstDepartments = splitDepartmentByParentId(departments, department.getParentId());
        if (logger.isDebugEnabled()) {
        	logger.debug("拆分组织开始时间:" + System.currentTimeMillis()  + "ms");
        }
        setChildDepartments(firstDepartments, departments);
        if (logger.isDebugEnabled()) {
        	logger.debug("拆分组织结束时间:" + System.currentTimeMillis()  + "ms");
        }
		return firstDepartments;
	}	

	public int listDepartmentCount(Department department) {
		return departmentDao.listDepartmentCount(department);
	}

	public void updateDepartment(Department department) {
		departmentDao.updateDepartment(department);
		
		// 写业务日志
		operateLogBiz.info(OperateLogType.DEPARTMENT_MANAGE,
				department.getDepartmentId(),
				department.getDepartmentName(), "修改成功");		
	}
	
    /**
     * 拆分组织数组
     * @param 组织数组
     * @return 相同父组织ID的数组
     */
    private List splitDepartmentByParentId(List departments, String parentId) {
        if (departments == null || departments.size() == 0) {
            return departments;
        }
        
        List tmpDepartments = new ArrayList();
        int size = departments.size();
        for (int i = size - 1; i >= 0; i--) {
        	Department tmpDepartment = (Department) departments.get(i);
            if (StringUtils.equals(parentId, tmpDepartment.getParentId())) {
                // 如果父组织id相同，从数组移出
            	tmpDepartments.add(tmpDepartment);
            	departments.remove(i);
            }
        }
        
        if (tmpDepartments == null || tmpDepartments.size() == 0) {
            return null;
        }

        return tmpDepartments;
    }
    
    /**
     * 建立父组织与子组织的关系
     * @param parentDepartments 父组织列表
     * @param childDepartments 子组织列表
     */
    private void setChildDepartments(List parentDepartments, List childDepartments) {
        if (parentDepartments == null || parentDepartments.size() == 0 || childDepartments == null || childDepartments.size() == 0) {
            return;
        }
        
        int size = parentDepartments.size();
        for (int i = size - 1; i >=0; i--) {
        	Department parentDepartment = (Department) parentDepartments.get(i);
            List tmpChildDepartments = splitDepartmentByParentId(childDepartments, parentDepartment.getDepartmentId());
//            parentDepartment.setChildDepartments(tmpChildDepartments);
            setChildDepartments(tmpChildDepartments, childDepartments);
        }
    }

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}    
	
}
