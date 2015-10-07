package com.startcaft.ssm.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.startcaft.ssm.mapper.ItemsMapper;
import com.startcaft.ssm.po.Items;

public class ItemsServiceImplTest {
	
	private ApplicationContext context;
	private ItemsService service;
	
	{
		context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
		service = context.getBean(ItemsService.class);
	}
	
	@Test
	public void testFindItemsById() {

		Items items = service.findItemsById(1);
		
		System.out.println(items.getName());
	}

}
