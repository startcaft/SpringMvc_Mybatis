package com.startcaft.jpa.test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.hibernate.ejb.QueryHints;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.startcaft.jpa.entity.Customer;
import com.startcaft.jpa.entity.Order;

public class JpqlTest {

	private EntityManagerFactory entityFactory;
	private EntityManager entityManager;
	private EntityTransaction transaction;

	@Before
	public void init() {

		entityFactory = Persistence.createEntityManagerFactory("JPA_Core_API");
		entityManager = entityFactory.createEntityManager();
		transaction = entityManager.getTransaction();
		transaction.begin();
	}

	@After
	public void destory() {

		transaction.commit();
		entityManager.close();
		entityFactory.close();
	}

	/**
	 * 默认情况下，若只查询部分属性，则将返回Object[] 类型的结果，或者Object[]类型的List。
	 * 【也可以创建对应的实体类的构造器，然后再jpql语句中利用对应的构造器返回实体类的对象。】
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void test_jpql_partly_properties() {

		// String jpql =
		// "select c.lastName,c.age from Customer c where c.id > ?";
		// List list = entityManager.createQuery(jpql).setParameter(1,
		// 0).getResultList();

		String jpql = "select new Customer(c.lastName,c.age) from Customer c where c.id > ?";
		List list = entityManager.createQuery(jpql).setParameter(1, 0)
				.getResultList();

		System.out.println(list);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test_jpql_hello() {

		String jpql = "from Customer c where c.age >= ?";
		Query query = entityManager.createQuery(jpql);

		// 注意，这里的占位符从 1 开始，而hibernate的query是从 0 开始。
		query.setParameter(1, 18);

		List<Customer> list = query.getResultList();

		System.out.println(list.size());
	}

	/**
	 * createNamedQuery 方法适用于在实体类上使用@NamedQuery 标记的查询语句
	 */
	@Test
	public void test_jpql_nameQuery() {

		Query query = entityManager.createNamedQuery("testNamedQuery");
		query.setParameter(1, 1);
		// List<Customer> lists = query.getResultList();
		Customer customer = (Customer) query.getSingleResult();

		System.out.println(customer);
	}

	/**
	 * createNativeQuery 方法适用于 本地SQL查询
	 */
	@Test
	public void test_native_query() {

		String sql = "select age from jpa_customers where id = ?";
		Query query = entityManager.createNativeQuery(sql).setParameter(1, 1);

		Object result = query.getSingleResult();

		System.out.println(result);
	}

	/**
	 * 使用 Hibernate 的查询缓存，前提是启用查询缓存。
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void test_query_cache() {

		String jpql = "from Customer c where c.age >= ?";
		Query query = entityManager.createQuery(jpql).setHint(
				QueryHints.HINT_CACHEABLE, true);
		query.setParameter(1, 18);
		List<Customer> list = query.getResultList();
		System.out.println(list.size());

		query = entityManager.createQuery(jpql).setHint(
				QueryHints.HINT_CACHEABLE, true);
		query.setParameter(1, 18);
		list = query.getResultList();
		System.out.println(list.size());
	}

	/**
	 * 可以使用order by 进行排序
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void test_order_by() {

		String jpql = "from Customer c where c.age >= ? order by c.age desc";
		Query query = entityManager.createQuery(jpql).setHint(
				QueryHints.HINT_CACHEABLE, true);
		query.setParameter(1, 18);
		List<Customer> list = query.getResultList();
		System.out.println(list.size());
	}

	/**
	 * 使用group by having 进行分组过滤
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void test_group_having() {

		String jpql = "select o.customer from Order o group by o.customer having count(o.id) > 2";
		List<Customer> customers = entityManager.createQuery(jpql)
				.getResultList();

		System.out.println(customers);
	}
	
	/**
	 * jpql的关联查询，同Hibernate 的hql关联查询。
	 * 关联查询建议把 fetch 加上，这样的话，得到的才是一个对象，而不是一个Object[]。而且这个对象的关联集合对象已经初始化成功了。
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void test_left_outer_join_fetch() {

		//String jpql = "from Customer c where c.id = ?";
		String jpql = "from Customer c left outer join  c.orders where c.id = ?";
		
		/*
		String jpql = "from Customer c left outer join fetch c.orders where c.id = ?";

		Customer customer = (Customer) entityManager.createQuery(jpql)
				.setParameter(1, 1).getSingleResult();
				
		System.out.println(customer);
		System.out.println(customer.getOrders().size());
		 */
		
		List<Object[]> result = entityManager.createQuery(jpql).setParameter(1, 1).getResultList();
		System.out.println(result);
		
	}
	
	/**
	 * jpql支持子查询
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void test_jpql_sub_query(){
		
		//查询Customer的 lastName为 startcaft 的Order对象。
		String jpql = "select o from Order o where"
				+ " o.customer = (select c from Customer c where c.lastName = ?)";
		
		List<Order> orders = entityManager.createQuery(jpql).setParameter(1, "startcaft").getResultList();
		
		System.out.println(orders);
		
	}
	
	/**
	 * 使用jpql内置的函数。
	 * 算术函数主要有：abs，mod，sprt，size等。
	 * size用于求集合的元素个数。
	 * 
	 * 日期函数主要有三个，即current_date，current_time，current_timestamp，
	 * 它们不需要参数，返回服务器上的当前日期，时间和时间戳。
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void test_jpql_function(){
		
		String jpql = "select upper(c.email) from Customer c";
		
		List<String> emails = entityManager.createQuery(jpql).getResultList();
		
		System.out.println(emails);
	}
	
	/**
	 * 可以使用jpql完成 update和 delete操作。
	 */
	@Test
	public void test_jpql_update_delete(){
		
		String jpql = "update Customer c set c.lastName = ? where c.id = ?";
		
		Query query = entityManager.createQuery(jpql).setParameter(1, "lisi").setParameter(2, 2);
		
		int result = query.executeUpdate();
		
		System.out.println(result);
	}
}
