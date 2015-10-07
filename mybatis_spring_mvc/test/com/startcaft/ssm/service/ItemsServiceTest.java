package com.startcaft.ssm.service;


import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.startcaft.ssm.po.ItemsCustom;
import com.startcaft.ssm.po.ItemsDto;


public class ItemsServiceTest {
	
	private ApplicationContext context;
	private ItemsService service;
	
	{
		context = new ClassPathXmlApplicationContext("classpath:spring/spring-mybatis.xml");
		service = context.getBean(ItemsService.class);
	}
	
	@Test
	public void testFindItemsById() throws Exception {
		
		ItemsDto items = service.findItemsById(1);
		System.out.println(items.getName());
	}
	
	@Test
	public void testFindItemsList() throws Exception{
		
		List<ItemsCustom> items = service.findItemsList(null);
		
		for (ItemsCustom itemsCustom : items) {
			System.out.println(itemsCustom.getName());
		}
	}
	
	@Test
	public void testFindItemsListCount() throws Exception{
		
		int count = service.findItemsListCount(null);
		
		System.out.println(count);
	}

}
