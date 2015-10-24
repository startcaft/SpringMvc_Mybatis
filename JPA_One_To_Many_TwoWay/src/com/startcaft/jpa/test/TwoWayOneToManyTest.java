package com.startcaft.jpa.test;


import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.startcaft.jpa.entity.Customer;
import com.startcaft.jpa.entity.Order;

public class TwoWayOneToManyTest {
	
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
	 * 在双向 1-n 关联关系中，执行保存时
	 * 1，若先保存 n 的一端，再保存 1 的一端，默认情况下，会多出 2*n(n等于多的一端的数量)条update语句，因为两端都在维护关联关系。
	 * 2，若先保存 1 的一端，则会多出  1*n 条update语句。
	 * 
	 * 【建议在进行双向 1-n 关联关系时，使用 n 的一端维护关联关系，而 1 的一方不要维护关联关系。这样会有效的减少SQL语句。】
	 * 【在 1 的一端使用@OneToMany(mappedBy="customer")来放弃关联关系。】
	 * 【mappedBy的属性值代表了，由 1 的一端指向 n 的一端，并且 这个属性值等于 n 的一端中包含的 1 的一端对象的属性名。】
	 * 【注意：mappedBy属性不能与@JoinColumn注解一起使用，不然会报错。】
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
		
		order1.setCustomer(customer);
		order2.setCustomer(customer);
		order3.setCustomer(customer);
		
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
