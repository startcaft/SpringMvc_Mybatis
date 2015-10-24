package com.startcaft.jps.test;


import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.startcaft.jpa.spring.entity.Person;
import com.startcaft.jpa.startcaft.repository.PersonRepository;
import com.startcaft.jpa.startcaft.service.PersonService;


public class JpaSpringTest {
	
	private ApplicationContext context; 
	private PersonRepository personRepository;
	private PersonService personService;
	
	{
		context = new ClassPathXmlApplicationContext("classpath:application-context.xml");
		personRepository = context.getBean(PersonRepository.class);
		personService = context.getBean(PersonService.class);
	}
	
	@Test
	public void test() throws SQLException {
		
		DataSource dataSource = context.getBean(DataSource.class);
		
		System.out.println(dataSource);
		System.out.println(dataSource.getConnection());
	}
	
	@Test
	public void test_Person_Repository(){
		
		System.out.println(personRepository.getClass().getName());
		
		Person person = personRepository.getByLastName("aa");
		
		System.out.println(person);
	}
	
	@Test
	public void test_Repository_keywords(){
		
		List<Person> persons = personRepository.findByLastNameStartingWithAndIdLessThan("a", 10);
		System.out.println(persons);
		
		persons = personRepository.findByEmailInOrBirthdayLessThan(
					Arrays.asList("aa@163.com","bb@163.com"), new Date());
		System.out.println(persons);
	}
	
	@Test
	public void test_Repository_keywords2(){
		
		//List<Person> persons = personRepository.getByAddressIdGreaterThan(1);
		List<Person> persons = personRepository.getByAddress_IdGreaterThan(1);
		System.out.println(persons);
	}
	
	@Test
	public void test_Repository_Query_Annotation(){
		
		Person p = personRepository.getMaxIdPerson();
		
		System.out.println(p);
	}
	
	@Test
	public void test_Repository_Query_Annotation_Param(){
		
		List<Person> persons = personRepository.getByQueryWithParam("aa", "aa@163.com");
		System.out.println(persons);
	}
	
	@Test
	public void test_Repository_Query_Annotation_Param2(){
		
		List<Person> persons = personRepository.getByQueryWithParam2("aa@163.com","aa");
		System.out.println(persons);
	}
	
	@Test
	public void test_Repository_Query_Annotation_Like_Param(){
		
//		List<Person> persons = personRepository.getByQueryLikeParam("%a%", "%aa%");
//		System.out.println(persons);
		
		List<Person> persons = personRepository.getByQueryLikeParam("a", "aa");
		System.out.println(persons);
		
		persons = personRepository.getByQueryLikeParam2("a", "aa");
		System.out.println(persons);
	}
	
	@Test
	public void test_Repository_Query_Native_SQL(){
		
		long count = personRepository.getTotalCount();
		System.out.println(count);
	}
	
	@Test
	public void test_Repository_Modifing_Annotation(){
		
//		personRepository.updatePersonEmail("startcaft@163.com", 1);
		
		personService.updatePersonEmail("startcaft@163.com", 1);
	}
	
	/*
	@Test
	public void test_PersonService_save(){
		
		PersonService service = context.getBean(PersonService.class);
		
		Person p1 = new Person();
		p1.setLastName("zhangsan");
		p1.setAge(12);
		p1.setEmail("aa@163.com");
		
		Person p2 = new Person();
		p2.setLastName("lisi");
		p2.setAge(11);
		p2.setEmail("bb@163.com");
		
		
		//如果是个代理对象的话，事务就是ok的
		System.out.println(service.getClass().getName());
		
		service.savePersons(p1, p2);
	}
	*/
}
