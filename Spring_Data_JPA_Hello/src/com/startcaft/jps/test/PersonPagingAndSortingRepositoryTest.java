package com.startcaft.jps.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.startcaft.jpa.spring.entity.Person;
import com.startcaft.jpa.startcaft.repository.PersonPagingAndSortingRepository;

public class PersonPagingAndSortingRepositoryTest {
	
	private ApplicationContext context; 
	private PersonPagingAndSortingRepository pagingRepository;
	
	{
		context = new ClassPathXmlApplicationContext("classpath:application-context.xml");
		pagingRepository = context.getBean(PersonPagingAndSortingRepository.class);
	}
	
	@Test
	public void test_paging_sorting() {
		
		//pageNo 从0开始
		int pageNo = 6;
		int pageSize = 5;
		
		//排序相关,Sort对象封装了排序信息
		//Order对象，是具体针对某一个属性进行排序
		Order order1 = new Order(Direction.DESC, "id");
		Order order2 = new Order(Direction.ASC, "email");
		Sort sort = new Sort(order1,order2);
		
		//Pageable接口 通常使用其实现类 PageRequest,其中封装了分页时需要的信息，
		//Pageable pageable;
		PageRequest pageRequest = new PageRequest(pageNo - 1, pageSize,sort);
		Page<Person> page = pagingRepository.findAll(pageRequest);
		
		System.out.println("总记录数:" + page.getTotalElements());
		System.out.println("当前第 " + (page.getNumber() + 1) + " 页");
		System.out.println("总共 " + page.getTotalPages() + " 页");
		System.out.println("当前第 " + page.getNumberOfElements() + "页");
		System.out.println("当前页 List:" + page.getContent());
	}

}
