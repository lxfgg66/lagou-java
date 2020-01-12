package com.lagou.sqlSession;

import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;

import java.beans.IntrospectionException;
import java.lang.reflect.*;
import java.sql.SQLException;
import java.util.List;

public class DefaultSqlSession implements SqlSession {

    Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementid, Object... params) throws Exception {
        //将要去完成对simpleExecutor里的query方法的调用
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementid);
        List<Object> list = simpleExecutor.query(configuration, mappedStatement, params);
        return (List<E>) list;
    }

    @Override
    public <T> T selectOne(String statementid, Object... params) throws Exception {
        List<Object> objects = selectList(statementid, params);
        if(objects.size() == 1){
            return (T) objects.get(0);
        }else {
            throw new RuntimeException("返回结果为空，或返回结果过多");
        }
    }

    @Override
    public void insert(String statementid, Object... params) throws Exception {
        this.update(statementid,params);
    }

    @Override
    public void update(String statementid, Object... params) throws Exception {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementid);
        simpleExecutor.update(configuration,mappedStatement,params);
    }

    @Override
    public void delete(String statementid, Object... params) throws Exception {
        this.update(statementid,params);
    }

    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        //使用jdk动态代理来为Dao接口生成代理对象，并返回
        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(),new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //底层都还是去执行jdbc代码//根据不同的情况来调用selectList或者selectOne
                //准备参数 1：statementid：sql语句的唯一标识：namespace.id=接口全限定名.方法名
                //方法名：findAll
                String methodName = method.getName();
                String className = method.getDeclaringClass().getName();

                String statementId = className + "." + methodName;

                //准备参数 2： params：args
                //获取被调用方法的返回值类型
                Type genericReturnType = method.getGenericReturnType();
                //判断是否进行了泛型类型参数化
                if(genericReturnType instanceof ParameterizedType){
                    return selectList(statementId,args);
                } else if(genericReturnType.getTypeName().equals("java.lang.Integer")){
                    update(statementId,args);
                    return 0;
                }
                return selectOne(statementId,args);
            }
        });

        return (T) proxyInstance;
    }
}
