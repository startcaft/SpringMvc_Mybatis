package com.startcaft.jpa.test;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.Test;

import com.startcaft.jpa.entity.Customer;

public class HelloTest {

	@Test
	public void test() {

		// 1，创建EntityManagerFactory(相当于Hibernate的SessionFactory)
		String persitenceUntiName = "JPA_Hello";

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("hibernate.format_sql", false);

		// EntityManagerFactory entityManagerFactory = Persistence
		// .createEntityManagerFactory(persitenceUntiName);
		
		//EntityManagerFactory的第二种创建方式
		EntityManagerFactory entityManagerFactory = Persistence
				.createEntityManagerFactory(persitenceUntiName, properties);

		// 2，创建EntityManager(相当于Hibernate的Session)
		EntityManager entityManager = entityManagerFactory
				.createEntityManager();

		// 3，开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		// 4，进行持久化操作
		Customer customer = new Customer();
		customer.setAge(20);
		customer.setLastName("Tom");
		customer.setEmail("tom@163.com");

		entityManager.persist(customer);

		// 5，提交事务
		transaction.commit();

		// 6，关闭EntityManager
		entityManager.close();

		// 7，关闭EntitymanagerFactory
		entityManagerFactory.close();
	}
}
