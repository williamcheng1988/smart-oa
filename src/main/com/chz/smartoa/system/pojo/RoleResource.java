package com.chz.smartoa.system.pojo;


/**
 * RoleResource对象.
 * @author huangdhui
 *
 */
public class RoleResource {
        /**
     * 资源key.
     */
    private String resourceKey;
        /**
     * 角色ID.
     */
    private String roleId;


        public String getResourceKey() {
        return this.resourceKey;
    }	
  
    public void setResourceKey(String resourceKey) {
        this.resourceKey = resourceKey;
    }
        public String getRoleId() {
        return this.roleId;
    }	
  
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

public String toString() {
		StringBuffer sb=new StringBuffer();
		sb.append("{");
                    sb.append("resourceKey:").append(resourceKey).append(",");
    sb.append("roleId:").append(roleId).append(",");

		
		sb.append("}");
		return sb.toString();
}


}
