package com.lagou.test;

import com.lagou.dao.IUserDao;
import com.lagou.io.Resources;
import com.lagou.pojo.User;
import com.lagou.sqlSession.SqlSession;
import com.lagou.sqlSession.SqlSessionFactory;
import com.lagou.sqlSession.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;

public class IPersistenceTest {

    @Test
    public void test() throws Exception {
        InputStream resourceAsString = Resources.getResourceAsSteam("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsString);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //调用
        User user = new User();
        user.setId(1);
        user.setUsername("张三");
        /*User user2 = sqlSession.selectOne("user.selectOne", user);
        System.out.println(user2);*/
        /*List<User> users = sqlSession.selectList("user.selectList", user);
        for (User user1 : users) {
            System.out.println(user1);
        }*/


        IUserDao mapper = sqlSession.getMapper(IUserDao.class);
        /*List<User> all = mapper.findAll();
        for (User user1 : all) {
            System.out.println(user1);
        }*/
        User user1 = mapper.findByCondition(user);
        System.out.println(user1);
    }

    @Test
    public void testAddUser() throws Exception {
        InputStream resourceAsString = Resources.getResourceAsSteam("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsString);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //调用
        User user = new User();
        user.setId(3);
        user.setUsername("lol");

        IUserDao mapper = sqlSession.getMapper(IUserDao.class);
        mapper.addUser(user);
    }

    @Test
    public void testUpdateUser() throws Exception {
        InputStream resourceAsString = Resources.getResourceAsSteam("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsString);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //调用
        User user = new User();
        user.setId(3);
        user.setUsername("王者荣耀");

        IUserDao mapper = sqlSession.getMapper(IUserDao.class);
        mapper.updateUser(user);
    }

    @Test
    public void testDeleteUser() throws Exception {
        InputStream resourceAsString = Resources.getResourceAsSteam("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsString);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //调用
        User user = new User();
        user.setId(3);

        IUserDao mapper = sqlSession.getMapper(IUserDao.class);
        mapper.deleteUser(user);
    }

}
