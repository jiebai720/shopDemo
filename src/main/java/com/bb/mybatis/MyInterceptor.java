package com.bb.mybatis;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;
import java.util.Properties;

/**
 * Created by admin on 2018/3/12.
 */
@Intercepts({
    @Signature( method="query" , type = Executor.class , args = {
            MappedStatement.class , Object.class , RowBounds.class , ResultHandler.class
    }),
    @Signature( method = "prepare" , type = StatementHandler.class , args = { Connection.class } )
})
public class MyInterceptor implements Interceptor {


    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        Object result = invocation.proceed();
        System.out.println( ":::: invocation.proceed() :::::" );
        return null;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target , this );
    }

    @Override
    public void setProperties(Properties properties) {

        String prop1 = properties.getProperty("prop1");
        String prop2 = properties.getProperty("prop2");
        System.out.println(prop1 + "------" + prop2);
    }


}
