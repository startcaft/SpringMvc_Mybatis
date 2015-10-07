package com.startcaft.ssm.mapper.test;


import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.startcaft.ssm.mapper.ItemsMapper;
import com.startcaft.ssm.po.Items;
import com.startcaft.ssm.po.ItemsExample;

public class ItemsMapperTest {
	
	private ApplicationContext context;
	private ItemsMapper itemsMapper;
	
	{
		context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
		itemsMapper = context.getBean(ItemsMapper.class);
	}
	
	@Test
	public void testDeleteByPrimaryKey() {
	}

	//执行插入语句
	@Test
	public void testInsert() {
		
		Items items = new Items();
		items.setName("MacBook Pro");
		items.setPrice(9999.0f);
		
		int result = itemsMapper.insert(items);
		
		System.out.println(result);
	}
	
	
	//根据主键来查询
	@Test
	public void testSelectByPrimaryKey() {
		Items items = itemsMapper.selectByPrimaryKey(1);
		System.out.println(items);
	}
	
	//自定义条件查询
	@Test
	public void testSelectByExample(){
		
		ItemsExample itemExample = new ItemsExample();
		//通过Criteria构造 查询条件
		ItemsExample.Criteria criteria = itemExample.createCriteria();
		criteria.andNameEqualTo("IPHONE6");
		
		//可能返回多条记录
		List<Items> items = itemsMapper.selectByExample(itemExample);
		
		for (Items item : items) {
			System.out.println(item.getName());
		}
	}

	//更新
	@Test
	public void testUpdateByPrimaryKey() {
		
		Items items = itemsMapper.selectByPrimaryKey(1);
		items.setDetail("苹果公司最新出品的智能电话");
		
		//将传入的数据，对所有字段进行更新
		int result = itemsMapper.updateByPrimaryKey(items);
		//如果传入属性不为空 才更新,一般是批量更新 使用此方法
		//itemsMapper.updateByPrimaryKeySelective(null);
		
		System.out.println(result);
	}
}
