package com.startcaft.jps.test;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;

import com.startcaft.jpa.spring.entity.Person;
import com.startcaft.jpa.startcaft.repository.PersonJpaRepository;

public class PersonJpaRepositoryTest {
	
	private ApplicationContext context; 
	private PersonJpaRepository jpaRepository;
	
	{
		context = new ClassPathXmlApplicationContext("classpath:application-context.xml");
		jpaRepository = context.getBean(PersonJpaRepository.class);
	}
	
	@Test
	public void testSaveAndFlush() {
		
		Person person = new Person();
		person.setEmail("abcd@163.com");
		person.setLastName("abcd");
		person.setBirthday(new Date());
		person.setId(27);//id最后添加，然后再执行就是更新了
		
		
		//相当于 jpa中的merge
		Person person2 = jpaRepository.saveAndFlush(person);
		
		System.out.println(person == person2);
	}
	
	/*
	 * 实现带查询条件的分页
	 * 调用JpaSpecificationExecutor 接口的 findAll(Specification<T> spec, Pageable pageable)方法
	 * Specification<T> 封装了 Jpa Criteria 查询的查询条件
	 * Pageable 封装了 分页所需要的信息 以及 排序信息
	 */
	@Test
	public void testJpaSpecificationExecutor(){
		
		int pageNo = 3 - 1;
		int pageSize = 5;
		
		//通常使用Specification 的匿名内部类
		Specification<Person> specification = new Specification<Person>() {
			
			/**
			 * @param root 	代表查询的实体类
			 * @param query	可以从中得到Root对象，即告知Jpa Criteria 查询要查询哪一个实体类，
			 * 				还可以结合EntityManager对象，得到最终查询的TypeQuery对象
			 * @param cb	CriteriaBuilder 对象，用于创建Criteria 相关对象的工厂，当然可以从中获取到Predicate 对象
			 * 
			 * @return Predicate 代表一个查询对象
			 */
			@Override
			public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				
				Path path = root.get("id");
				
				Predicate predicate = cb.gt(path, 5);
				
				return predicate;
			}
		};
		
		Order order1 = new Order(Direction.DESC, "id");
		Order order2 = new Order(Direction.ASC, "email");
		Sort sort = new Sort(order1,order2);
		
		//Pageable接口 通常使用其实现类 PageRequest,其中封装了分页时需要的信息，
		//Pageable pageable;
		PageRequest pageRequest = new PageRequest(pageNo - 1, pageSize,sort);
		Page<Person> page = jpaRepository.findAll(specification, pageRequest);
		
		System.out.println("总记录数:" + page.getTotalElements());
		System.out.println("当前第 " + (page.getNumber() + 1) + " 页");
		System.out.println("总共 " + page.getTotalPages() + " 页");
		System.out.println("当前第 " + page.getNumberOfElements() + "页");
		System.out.println("当前页 List:" + page.getContent());
	}

}
