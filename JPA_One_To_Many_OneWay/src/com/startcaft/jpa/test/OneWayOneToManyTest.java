package com.startcaft.jpa.test;


import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.startcaft.jpa.entity.Customer;
import com.startcaft.jpa.entity.Order;

public class OneWayOneToManyTest {
	
	private EntityManagerFactory entityFactory;
	private EntityManager entityManager;
	private EntityTransaction transaction;
	
	@Before
	public void init(){
		
		entityFactory = Persistence.createEntityManagerFactory("JPA_Core_API");
		entityManager = entityFactory.createEntityManager();
		transaction = entityManager.getTransaction();
		transaction.begin();
	}
	
	@After
	public void destory(){
		
		transaction.commit();
		entityManager.close();
		entityFactory.close();
	}
	
	/**
	 * 单向一对多 执行保存时，一定会多出update语句。
	 * 因为多的一端在insert时，不会同时插入外键列。
	 */
	@Test
	public void test_one_to_many_oneWay_persist(){
		
		Customer customer = new Customer();
		customer.setAge(20);
		customer.setBirthday(new Date());
		customer.setCreateTime(new Date());
		customer.setLastName("startcaft");
		customer.setEmail("startcaft@163.com");
		
		Order order1 = new Order();
		order1.setOrderName("1号订单");
		
		Order order2 = new Order();
		order2.setOrderName("2号订单");
		
		Order order3 = new Order();
		order3.setOrderName("3号订单");
		
		//设置关联关系
		customer.getOrders().add(order1);
		customer.getOrders().add(order2);
		customer.getOrders().add(order3);
		
		//执行持久化操作
		//这里保存的顺序无关紧要，因为是在一的一端维护关联关系，所以多的一方在保存时不会维护外键的值
		//所以，只能会多发出update语句来更新外键的值。
		entityManager.persist(customer);
		entityManager.persist(order1);
		entityManager.persist(order2);
		entityManager.persist(order3);
	}
	
	/**
	 * 默认对关联的 多的一方 使用懒加载的加载策略。
	 * 使用OneToMany(fetch=FetchType.EAGER)，改变加载关联的 多的一方 为立即加载。
	 */
	@Test
	public void test_one_to_many_oneWay_find(){
		
		Customer customer = entityManager.find(Customer.class, 2);//先查一的一端。
		
		System.out.println(customer.getLastName());
		
		System.out.println(customer.getOrders().size());//再根据外键查询多的一端。
	}
	
	/**
	 * 默认情况下，若删除一的一端，则会把关联的 多的一端的外键置空，然后再进行删除一的一端。
	 * 使用@OneToMany(cascade=CascadeType.REMOVE) 可以级联删除关联的 多的一方的数据。
	 */
	@Test
	public void test_one_to_many_oneWay_remove(){
		
		Customer customer = entityManager.find(Customer.class, 2);
		entityManager.remove(customer);
	}
	
	@Test
	public void test_one_to_many_oneWay_update(){
		
		Customer customer = entityManager.find(Customer.class, 2);
		customer.getOrders().iterator().next().setOrderName("XXX订单");
		
	}
}
