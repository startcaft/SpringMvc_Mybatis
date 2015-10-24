package com.startcaft.jpa.test;


import java.beans.Customizer;
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

import com.startcaft.jpa.entity.Category;
import com.startcaft.jpa.entity.Item;

public class TwoWayManyToManyTest {
	
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
	
	@Test
	public void test_many_to_many_twoWay_persist(){
		
		Item item1 = new Item();
		item1.setItemName("iphone6s");
		
		Item item2 = new Item();
		item2.setItemName("ipad air2");
		
		Category cate1 = new Category();
		cate1.setCategoryName("C-1");
		
		Category cate2 = new Category();
		cate1.setCategoryName("C-2");
		
		//设置关联关系
		item1.getCategories().add(cate1);
		item1.getCategories().add(cate2);
		
		item2.getCategories().add(cate1);
		item2.getCategories().add(cate2);
		
		
		cate1.getItems().add(item1);
		cate1.getItems().add(item2);
		
		cate2.getItems().add(item1);
		cate2.getItems().add(item2);
		
		//执行持久化操作
		entityManager.persist(item1);
		entityManager.persist(item2);
		entityManager.persist(cate1);
		entityManager.persist(cate2);
	}
	
	/**
	 * 若获取维护关联关系的一方，对于关联的集合对象，默认使用懒加载的策略。
	 */
	@Test
	public void test_many_to_many_twoWay_find(){
		
		Item item = entityManager.find(Item.class, 1);
		System.out.println(item.getItemName());
		System.out.println(item.getCategories().size());
	}
	
	/**
	 * 若获取不维护关联关系的一方，对于关联集合对象，默认也是使用懒加载的策略。
	 * 所以获取哪一方都无所谓，因为存在一个中间表。
	 */
	@Test
	public void test_many_to_many_twoWay_find2(){
		
		Category cate = entityManager.find(Category.class, 1);
		System.out.println(cate.getCategoryName());
		System.out.println(cate.getItems().size());
	}
	
	@Test
	public void test_jps_second_level_cache(){

		//只会发送一条Sql语句，因为处于使用的是同一个EntityManager的一级缓存。
//		Category cate1 = entityManager.find(Category.class, 1);
//		System.out.println(cate1);
//		Category cate2 = entityManager.find(Category.class, 1);
//		System.out.println(cate2);
		
		Category cate1 = entityManager.find(Category.class, 1);
		System.out.println(cate1);
		
		transaction.commit();
		entityManager.close();
		
		//重新开启一个EntityManager
		entityManager = entityFactory.createEntityManager();
		transaction = entityManager.getTransaction();
		transaction.begin();
		
		Category cate2 = entityManager.find(Category.class, 1);
		System.out.println(cate2);
	}
}
