package com.startcaft.mybatis.test;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.startcaft.mybatis.dao.UserDao;
import com.startcaft.mybatis.dao.impl.UserDaoImpl;
import com.startcaft.mybatis.po.User;

public class UserDaoImplTest {
	
	private SqlSessionFactory sqlSessionFactory;
	
	@Before
	public void setup() throws IOException{
		String resources = "SqlMapConfig.xml";
		InputStream inputStream = Resources.getResourceAsStream(resources);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}
	
	@Test
	public void testFindUserById() throws Exception {
		UserDao userDao = new UserDaoImpl(sqlSessionFactory);
		User user = userDao.findUserById(1);
		System.out.println(user.getUsername());
	}

	@Test
	public void testInsertUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindUserByName() {
		fail("Not yet implemented");
	}

}
