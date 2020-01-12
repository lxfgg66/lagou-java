package com.lagou.dao;

import com.lagou.pojo.User;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface IUserDao {

    // 查询所有用户
    public List<User> findAll() throws Exception;

    // 根据条件查询用户
    public User findByCondition(User user) throws Exception;

    // 添加用户
    public Integer addUser(User user);

    // 修改用户
    public Integer updateUser(User user);

    // 删除用户
    public Integer deleteUser(User user);

}
