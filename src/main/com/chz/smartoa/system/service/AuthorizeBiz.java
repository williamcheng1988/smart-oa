package com.chz.smartoa.system.service;
/**
 * 鉴权接口.
 * @author huangdhui
 *
 */
public interface AuthorizeBiz {
    /**
     * 地址鉴权.
     * @param loginName 登录用户名
     * @param addressUrl 地址url
     * @param parameterMap 地址参数map
     * @param isLogin 是否登录 
     * @return 成功|失败
     */
    boolean authorizeAddress(String loginName, String addressUrl,boolean isLogin);
}
