package com.startcaft.ssm.mapper.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.startcaft.ssm.mapper.UserMapper;
import com.startcaft.ssm.po.User;

public class UserMapperTest {
	
	private ApplicationContext context;
	private UserMapper userMapper;
	
	{
		context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
		userMapper = (UserMapper) context.getBean("userMapper");
	}

	@Test
	public void testFindUserById() throws Exception {
		User user = userMapper.findUserById(1);
		System.out.println(user);
	}

}
