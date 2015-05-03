package com.chz.smartoa.common.ibatis;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.mapping.statement.RowHandlerCallback;
import com.ibatis.sqlmap.engine.scope.StatementScope;

public class LimitSqlExecutor extends SqlExecutor {

    private static final Log logger = LogFactory.getLog(LimitSqlExecutor.class);
    
    private Dialect dialect;

    private boolean enableLimit = true;

    public Dialect getDialect() {
        return dialect;
    }

    public void setDialect(Dialect dialect) {
        this.dialect = dialect;
    }

    public boolean isEnableLimit() {
        return enableLimit;
    }

    public void setEnableLimit(boolean enableLimit) {
        this.enableLimit = enableLimit;
    }

    @Override
    public void executeQuery(StatementScope statementScope, Connection conn, String sql,
            Object[] parameters, int skipResults, int maxResults,
            RowHandlerCallback callback) throws SQLException {
    	
        if ((skipResults != NO_SKIPPED_RESULTS || maxResults != NO_MAXIMUM_RESULTS)
                && supportsLimit()) {
            sql = dialect.getLimitString(sql, skipResults, maxResults);
            if(logger.isDebugEnabled()){
                logger.debug(sql);
            }
            skipResults = NO_SKIPPED_RESULTS;
            maxResults = NO_MAXIMUM_RESULTS;            
        }
        super.executeQuery(statementScope, conn, sql, parameters, skipResults,
                maxResults, callback);
    }

    public boolean supportsLimit() {
        if (enableLimit && dialect != null) {
            return dialect.supportsLimit();
        }
        return false;
    }

}