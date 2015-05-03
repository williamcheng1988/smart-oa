package com.chz.smartoa.common.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.impl.ExtendedSqlMapClient;

/***
 * 
 * @author huangdhui
 *
 */
public abstract class BaseDaoiBatis extends SqlMapClientDaoSupport
{
	
    private SqlExecutor sqlExecutor;

    public SqlExecutor getSqlExecutor()
    {

        return sqlExecutor;
    }

    public void setSqlExecutor(SqlExecutor sqlExecutor)
    {

        this.sqlExecutor = sqlExecutor;
    }

    public void setEnableLimit(boolean enableLimit)
    {

        if (sqlExecutor instanceof LimitSqlExecutor)
        {
            (( LimitSqlExecutor ) sqlExecutor).setEnableLimit(enableLimit);
        }
    }

    @SuppressWarnings("deprecation")
	public void initialize() throws Exception
    {

        if (sqlExecutor != null)
        {
            SqlMapClient sqlMapClient = getSqlMapClientTemplate().getSqlMapClient();
            if (sqlMapClient instanceof ExtendedSqlMapClient)
            {
            	ReflectUtil.setFieldValue((( ExtendedSqlMapClient ) sqlMapClient).getDelegate(), "sqlExecutor", SqlExecutor.class, sqlExecutor);
            }
        }
    }
}
