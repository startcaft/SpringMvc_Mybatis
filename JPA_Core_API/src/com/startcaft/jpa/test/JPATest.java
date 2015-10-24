package com.startcaft.jpa.test;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.startcaft.jpa.entity.Customer;

public class JPATest {
	
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

	/**类似于Hibernate中的get方法**/
	@Test
	public void test_find_api(){
		
		Customer customer = entityManager.find(Customer.class, 1);	//find方法直接发送sql语句
		
		System.out.println("------------------------------");
		
		System.out.println(customer);
	}
	
	/**类似于Hibernate的load方法**/
	@Test
	public void test_getReference_api(){
		
		Customer customer = entityManager.getReference(Customer.class, 1);	//注意懒加载异常
		System.out.println(customer.getLastName());
		
		System.out.println("------------------------------");
		
//		transaction.commit();
//		entityManager.close();
		
		System.out.println(customer);								
	}
	
	/**类似于Hibernate中的save方法，使对象由临时状态变为持久化状态，和save方法的不同之处在于：**/
	/**若对象由id，则不能执行persist操作。而save方法则不会抛出异常**/
	@Test
	public void test_persist_api(){
		
		Customer customer = new Customer();
		customer.setAge(25);
		customer.setLastName("jim");
		customer.setEmail("jim@163.com");
		
		entityManager.persist(customer);
		
		System.out.println("-----------------------");
		
		System.out.println(customer);
	}
	
	/**类似于Hibernate的delete方法，把对象对应的记录从数据库中删除。与delete方法不同之处在于：**/
	/**只能删除持久化状态的对象，而Hibernate的delete方法还可以删除游离状态下的对象**/
	@Test
	public void test_remove_api(){
		
//		Customer customer = new Customer();
//		customer.setId(2);
		
		Customer customer = entityManager.find(Customer.class, 2);
		
		entityManager.remove(customer);
		
	}
	
	
	/**类似于Hibernate的 Session 中的 saveOrUpdate方法，**/
	/*
	 * merge第一种情况：若传入的是一个临时对象(没有id)
	 * 会创建一个新的对象，把临时对象的属性赋值到新的对象中，然后对新的对象执行持久化操作。
	 * 所以，新的对象中有id，而以前的临时对象中没有id。
	 */
	@Test
	public void test_merge_api_1(){
		
		Customer customer = new Customer();
		customer.setAge(18);
		customer.setLastName("lily");
		customer.setEmail("lily@163.com");
		
		Customer customer2 = entityManager.merge(customer);
		
		System.out.println("临时对象merge后的id：" + customer.getId());
		System.out.println("meger后返回的对象的id：" + customer2.getId());
	}
	
	/*
	 * merge第二种情况：若传入的是一个游离对象(有id，但和上下文没关系)
	 * 1，若在EntityManager缓存中没有该对象。
	 * 2，【若在数据库中也没有对应的记录】。
	 * 3，JPA会创建一个新的对象，并把游离对象的属性赋值给新的对象。
	 * 4，对新创建的对象执行持久化操作。
	 */
	@Test
	public void test_merge_api_2(){
		
		Customer customer = new Customer();
		customer.setAge(30);
		customer.setLastName("jackson");
		customer.setEmail("jackson@163.com");
		
		//设置了主键id，缓存中没有，数据库中也没有，所以创建新的对象，并给新对象赋值，并尝试执行持久化操作。
		customer.setId(100);				
		
		Customer customer2 = entityManager.merge(customer);
		
		System.out.println("游离对象merge之前的id：" + customer.getId());
		System.out.println("游离对象meger后返回的对象的id：" + customer2.getId());
	}
	
	/*
	 * merge第三种情况：若传入的是一个游离对象(有id，但和上下文没关系)
	 * 1，若在EntityManager缓存中没有该对象。
	 * 2，【数据库中有对应的记录】。
	 * 3，JPA会查询对应的记录，然后返回该记录对应的对象，再然后把游离对象的属性赋值到查询出来的对象中。
	 * 4，对查询到的对象执行update操作。
	 */
	@Test
	public void test_merge_api_3(){
		
		Customer customer = new Customer();
		customer.setAge(30);
		customer.setLastName("json");
		customer.setEmail("json@163.com");
		
		//数据库中有对应的记录。
		customer.setId(4);
		
		Customer customer2 = entityManager.merge(customer);
		
		System.out.println(customer == customer2);
	}
	
	/*
	 * merge第三种情况：若传入的是一个游离对象(有id，但和上下文没关系)
	 * 1，【若在EntityManager缓存中有对应的对象】。
	 * 2，JPA会把游离对象的属性赋值到EntityManager缓存中的对象中。
	 * 3，EntityManager 缓存中的对象执行update。
	 */
	@Test
	public void test_merge_api_4(){
		
		Customer customer = new Customer();
		customer.setAge(30);
		customer.setLastName("lindan");
		customer.setEmail("lindan@163.com");
		
		customer.setId(4);
		
		//通过find方法获取到id为4的记录，这时EntityManager缓存中4的对应的对象了。
		Customer customer2 = entityManager.find(Customer.class, 4);
		
		entityManager.merge(customer);
		
		System.out.println(customer == customer2);
	}
	
	/**类似于Hibernate 中的 Session的 flush 方法**/
	@Test
	public void test_flush_api(){
		
		Customer customer = entityManager.find(Customer.class, 1);
		
		//更改了lastname，在事务commit的时候会同步更新数据库，因为里面commmit执行了flush。
		customer.setLastName("startcaft");
		
		//这里就会发送SQL语句，但是没有提交事务。
		entityManager.flush();
	}
	
	/**类似于Hibernate中的 session 的 refresh方法**/
	@Test
	public void test_refresh_api(){
		
		Customer customer = entityManager.find(Customer.class, 1);//这里会发送一条SQL语句
		customer = entityManager.find(Customer.class, 1);//这里有缓存，不会发送SQL语句。
		
		entityManager.refresh(customer);//这里会发送一条sql
		
	}
}
