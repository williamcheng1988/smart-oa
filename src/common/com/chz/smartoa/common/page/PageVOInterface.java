package com.chz.smartoa.common.page;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * <p>分页对象VO的构造和赋值接口</p>
 * @author huangdhui
 */
public interface PageVOInterface
{

    /**
     * 创建VO对象
     *
     * @return VOObject
     */
    public Object createObject () ;

    /**
     * 从ResultSet值赋给VO对象
     *
     * @param vo VOObject
     * @param rs ResultSet
     * @throws  SQLException
     */
    public void CopyValFromResultSet (Object vo, ResultSet rs) throws SQLException ;
}
