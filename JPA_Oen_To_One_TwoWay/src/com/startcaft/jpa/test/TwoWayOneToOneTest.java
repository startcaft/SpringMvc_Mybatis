package com.startcaft.jpa.test;



import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.startcaft.jpa.entity.Department;
import com.startcaft.jpa.entity.Manager;


public class TwoWayOneToOneTest {
	
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
	 * 双向 1-1 的关联关系，在执行持久化时，建议先保存不维护关联关系的一方【即没有外键的一方】，这样不会多出update语句。
	 */
	@Test
	public void test_one_to_one_twoWay_persist(){
		
		Manager mgr = new Manager();
		mgr.setManagerName("startcaft");
		
		Department department = new Department();
		department.setDepartName("技术部");
		
		//设置关联关系
		mgr.setDepartment(department);
		department.setManager(mgr);
		
		//执行保存操作
		entityManager.persist(mgr);			//注意，先保存不维护关联关系的一方，不然会有多余的update语句。
		entityManager.persist(department);
	}
	
	/**
	 * 默认情况下，若获取维护关联关系的一方【即有外键的一方】，则会通过左外连接获取其关联的对象。
	 * 但可以通过@OneToOne(fetch=FetchType.LAZY)来修改加载策略为延迟加载
	 */
	@Test
	public void test_one_to_one_twoWay_find(){
		
		//先获取维护关联关系的一方【即有外键的一方】
		Department depart = entityManager.find(Department.class, 1);
		System.out.println(depart.getDepartName());
		System.out.println(depart.getManager().getClass().getName());//延迟加载后，这个就是代理对象了
	}
	
	/**
	 * 默认情况下，若获取维护不关联关系的一方【即没有外键的一方】，也是会通过左外连接获取其关联的对象。
	 * 但可以通过@OneToOne(fetch=FetchType.LAZY)来修改加载策略为延迟加载 。
	 * 但依然会再发送一条SQL语句来初始化其关联对象。、
	 * 【这说明在不维护关联关系的一方，不建议修改fetch属性】
	 */
	@Test
	public void test_one_to_one_twoWay_find2(){
		
		//先获取不维护关联关系的一方【即没有外键的一方】
		Manager mgr = entityManager.find(Manager.class, 1);
		System.out.println(mgr.getManagerName());
		System.out.println(mgr.getDepartment().getClass().getName());
	}
}
