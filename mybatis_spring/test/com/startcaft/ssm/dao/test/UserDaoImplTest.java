package com.startcaft.ssm.dao.test;


import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.startcaft.ssm.dao.UserDao;
import com.startcaft.ssm.po.User;

public class UserDaoImplTest {
	
	private ApplicationContext context;
	private UserDao userDao;
	
	{
		context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
		userDao = context.getBean(UserDao.class);
	}
	
	@Test
	public void testFindUserById() throws Exception {
		
		User user = userDao.findUserById(1);
		
		System.out.println(user);
	}

}
