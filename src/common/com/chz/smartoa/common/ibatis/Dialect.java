package com.chz.smartoa.common.ibatis;

public interface Dialect {
    
    public boolean supportsLimit();
    /**
     * 
     * @param sql
     * @param hasOffset
     * @return
     */
    public String getLimitString(String sql, boolean hasOffset);

    public String getLimitString(String sql, int offset, int limit);
}
