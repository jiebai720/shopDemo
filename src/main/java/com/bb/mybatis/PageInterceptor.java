package com.bb.mybatis;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;


/**
 * Created by admin on 2018/3/12.
 */
@Intercepts({
        @Signature(method="prepare" , type = StatementHandler.class , args = {
                Connection.class
        } )
})
public class PageInterceptor implements Interceptor {


    private String databaseType ;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        RoutingStatementHandler handler = (RoutingStatementHandler)  invocation.getTarget() ;
        StatementHandler delegate = (StatementHandler)ReflectUtil.getFieldValue( handler, "delegate");

        BoundSql boundSql = delegate.getBoundSql();
        Object obj = boundSql.getParameterObject();

        if( obj instanceof Page<?> ){
            Page<?> page = (Page<?>) obj ;
            MappedStatement mappedStatement = (MappedStatement) ReflectUtil.getFieldValue( delegate , "mappedStatement" );
            Connection connection = (Connection) invocation.getArgs()[0] ;
            String sql = boundSql.getSql();
            this.setTotalRecord( page , mappedStatement , connection );
            String pageSql = this.getPageSql( page , sql );
            ReflectUtil.setFieldValue( boundSql , "sql" , pageSql );
        }

        return invocation.proceed() ;
    }

    private String getPageSql(Page<?> page, String sql) {
        StringBuffer sqlBuffer = new StringBuffer(sql);
        if ("mysql".equalsIgnoreCase(databaseType)) {
            return getMysqlPageSql(page, sqlBuffer);
        } else if ("oracle".equalsIgnoreCase(databaseType)) {
            return getOraclePageSql(page, sqlBuffer);
        }
        return sqlBuffer.toString();
    }

    private String getMysqlPageSql(Page<?> page, StringBuffer sqlBuffer) {
        //计算第一条记录的位置，Mysql中记录的位置是从0开始的。
        int offset = (page.getPageNo() - 1) * page.getPageSize();
        sqlBuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
        return sqlBuffer.toString();
    }


    private String getOraclePageSql(Page<?> page, StringBuffer sqlBuffer) {

        //计算第一条记录的位置，Oracle分页是通过rownum进行的，而rownum是从1开始的
        int offset = (page.getPageNo() - 1) * page.getPageSize() + 1;
        sqlBuffer.insert(0, "select u.*, rownum r from (")
                .append(") u where rownum < ")
                .append(offset + page.getPageSize());

        sqlBuffer.insert(0, "select * from (")
                .append(") where r >= ")
                .append(offset);

        //上面的Sql语句拼接之后大概是这个样子：
        //select * from (select u.*, rownum r from (select * from t_user) u where rownum < 31) where r >= 16
        return sqlBuffer.toString();
    }

    private void setTotalRecord(Page<?> page,
                                MappedStatement mappedStatement, Connection connection) {

        BoundSql boundSql = mappedStatement.getBoundSql(page);
        String sql = boundSql.getSql();
        String countSql = this.getCountSql(sql);

        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappings();
        BoundSql countBoundSql = new BoundSql( mappedStatement.getConfiguration() , countSql , parameterMappingList ,countSql);
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement , page , countBoundSql);
        PreparedStatement preparedStatement = null ;
        ResultSet resultSet = null ;
        try {
            preparedStatement = connection.prepareStatement(countSql) ;
            parameterHandler.setParameters( preparedStatement );

            resultSet  = preparedStatement.executeQuery();
            if( resultSet.next() ){
                int totalRecord = resultSet.getInt(1);
                page.setTotalRecord(totalRecord);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try{
                if( resultSet != null ){
                    resultSet.close();
                }
                if( preparedStatement != null ){
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 根据原Sql语句获取对应的查询总记录数的Sql语句
     * @param sql
     * @return
     */
    private String getCountSql(String sql) {

        return "select count(1) from (" + sql + ")";
    }


    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target ,this );
    }

    @Override
    public void setProperties(Properties properties) {
        this.databaseType = properties.getProperty("databaseType");
    }

}
