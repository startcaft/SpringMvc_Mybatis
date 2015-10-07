package com.startcaft.mybatis.mapper;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.startcaft.mybatis.po.User;
import com.startcaft.mybatis.po.UserCustom;
import com.startcaft.mybatis.po.UserQueryVo;

public class UserMapperTest {
	
	private SqlSessionFactory sqlSessionFactory;
	
	@Before
	public void setup() throws IOException{
		String resources = "SqlMapConfig.xml";
		InputStream inputStream = Resources.getResourceAsStream(resources);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}
	
	@Test
	public void testFindUserById() throws Exception {
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		//创建UserMapper对象。
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		
		User user = userMapper.findUserById(1);
		
		System.out.println(user.getUsername());
		
	}
	
	@Test
	public void testFindUserByIdResultMap() throws Exception {
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		//创建UserMapper对象。
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		
		User user = userMapper.findUserById(1);
		
		System.out.println(user.getUsername());
		
	}
	
	@Test
	public void testFindUserByName() throws Exception {
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		//创建UserMapper对象。
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		
		List<User> users = userMapper.findUserByName("start");
		
		System.out.println(users);
		
	}
	
	@Test
	public void testFindUserList() throws Exception {
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		//创建UserMapper对象。
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		
		UserQueryVo query = new UserQueryVo();
		
		UserCustom userCustom = new UserCustom();
//		userCustom.setSex("1");
		userCustom.setAddress("武汉");
		
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		ids.add(3);
		
		query.setUserCustom(userCustom);
		query.setIds(ids);
		
		List<UserCustom> userCustoms = userMapper.findUserList(query);
		
		int count = userMapper.findUserListCount(query);
		
		System.out.println(userCustoms);
		System.out.println(count);
	}

}
