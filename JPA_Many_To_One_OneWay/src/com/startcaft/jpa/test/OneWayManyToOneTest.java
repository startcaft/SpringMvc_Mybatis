package com.startcaft.jpa.test;


import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.startcaft.jpa.entity.Customer;
import com.startcaft.jpa.entity.Order;

public class OneWayManyToOneTest {
	
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
	 * 保存多对一时，建议先保存一的一段，后保存多的一段，这样才不会多出额外的update语句。
	 */
	@Test
	public void test_many_to_one_oneWay_persist(){
		
		Customer customer = new Customer();
		customer.setAge(20);
		customer.setBirthday(new Date());
		customer.setCreateTime(new Date());
		customer.setLastName("json");
		customer.setEmail("json@163.com");
		
		Order order1 = new Order();
		order1.setOrderName("1号订单");
		
		Order order2 = new Order();
		order2.setOrderName("2号订单");
		
		Order order3 = new Order();
		order3.setOrderName("3号订单");
		
		//设置关联关系
		order1.setCustomer(customer);
		order2.setCustomer(customer);
		order3.setCustomer(customer);
		
		//执行持久化操作
		entityManager.persist(customer);//先保存的一的一方，不然会有Order会有多余的update语句。
		entityManager.persist(order1);
		entityManager.persist(order2);
		entityManager.persist(order3);
	}
	
	/**默认情况下，使用 左外连接的方式来获取关联的一的一方的对象**/
	/**ManyToOne(fetch=FetchType.LAZY)设置为懒加载，再使用到一方的对象的属性时，发送查询语句**/
	@Test
	public void test_many_to_one_oneWay_find(){
		
		//默认获取一的一方使用 左外 链接一起加载。
		Order order = entityManager.find(Order.class, 1);
		
		System.out.println(order.getOrderName());
		
		System.out.println(order.getCustomer().getLastName());
	}
	
	/**不能直接删除一的一端，因为有外键约束**/
	@Test
	public void test_many_to_one_oneWay_remove(){
		
//		Order order = entityManager.find(Order.class, 1);
//		entityManager.remove(order);
		
		//不能直接删除一的一端，因为有外键约束。
//		Customer customer = entityManager.find(Customer.class, 4);
//		entityManager.remove(customer);
	}
	
	@Test
	public void test_many_to_one_oneWay_update(){
		
		Order order = entityManager.find(Order.class, 3);
		order.getCustomer().setLastName("startcaft");
	}
}
