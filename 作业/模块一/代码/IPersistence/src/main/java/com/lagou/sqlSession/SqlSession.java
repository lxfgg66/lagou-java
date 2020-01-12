package com.lagou.sqlSession;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface SqlSession {

    // 查询所有
    public <E> List<E> selectList(String statementid,Object... params) throws Exception;

    // 根据条件，查询单个
    public <T> T selectOne(String statementid,Object... params) throws Exception;

    // 添加用户
    public void insert(String statementid,Object... params) throws Exception;

    // 修改用户
    public void update(String statementid,Object... params) throws Exception;

    // 删除用户
    public void delete(String statementid,Object... params) throws Exception;

    //为Dao接口生成代理实现类
    public <T> T getMapper(Class<?> mapperClass);
}
