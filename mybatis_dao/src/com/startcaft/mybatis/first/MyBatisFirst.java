package com.startcaft.mybatis.first;


import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.startcaft.mybatis.po.User;

public class MyBatisFirst {

	/**
	 * 根据id查询用户信息，得到一条记录
	 * 
	 * @throws IOException
	 **/
	@Test
	public void findUserByIdTest() throws IOException {

		InputStream inputStream = Resources
				.getResourceAsStream("SqlMapConfig.xml");

		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
				.build(inputStream);
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		User user = sqlSession.selectOne("test.findUserById",1);
		
		System.out.println(user.getUsername());
		
		//释放SqlSession的资源
		sqlSession.close();
	}
	
	@Test
	public void findUserByNameTest() throws IOException{
		
		InputStream inputStream = Resources
				.getResourceAsStream("SqlMapConfig.xml");

		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
				.build(inputStream);
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		List<User> users = sqlSession.selectList("test.findUserByName","start");
		
		System.out.println(users);
		
		//释放SqlSession的资源
		sqlSession.close();
	}
	
	
	@Test
	public void inserUserTest() throws IOException{
		
		InputStream inputStream = Resources
				.getResourceAsStream("SqlMapConfig.xml");

		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
				.build(inputStream);
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		User user = new User();
		user.setUsername("xxxxx");
		user.setBirthday(new Date());
		user.setSex("1");
		user.setAddress("未知");
		
		sqlSession.insert("test.insertUser",user);
		
		//提交事务
		sqlSession.commit();
		//释放SqlSession的资源
		sqlSession.close();
		
		System.out.println(user.getId());
	}
	
	
	@Test
	public void deleteUserTest() throws IOException{
		
		InputStream inputStream = Resources
				.getResourceAsStream("SqlMapConfig.xml");

		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
				.build(inputStream);
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		
		sqlSession.delete("test.deleteUser",7);
		
		//提交事务
		sqlSession.commit();
		//释放SqlSession的资源
		sqlSession.close();
	}
	
	@Test
	public void updateUserTest() throws IOException{
		
		InputStream inputStream = Resources
				.getResourceAsStream("SqlMapConfig.xml");

		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
				.build(inputStream);
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		User user = new User();
		user.setId(6);
		user.setUsername("yyyyyy");
		user.setBirthday(new Date());
		user.setSex("0");
		user.setAddress("未知");
		
		sqlSession.update("test.updateUser",user);
		
		//提交事务
		sqlSession.commit();
		//释放SqlSession的资源
		sqlSession.close();
		
	}

}
