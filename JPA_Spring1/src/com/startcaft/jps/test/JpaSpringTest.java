package com.startcaft.jps.test;


import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.startcaft.jpa.spring.entity.Person;
import com.startcaft.jpa.startcaft.service.PersonService;

/*
 * Spring整合JPA有三种方式：
 * 1，LocalEntityManagerFactoryBean：适用于那些仅使用JPA进行数据访问的项目，该FactoryBean将根据JPA
 * 		presistenceProvider自动检测配置文件进行工作，一般从META-INF/presistence.xml读取配置信息，这种方式最为简单，
 * 		【但不能设置Spring中定义的DataSource，且不支持Spring管理的全局事务。】
 * 
 * 2，从JNDI中获取：用于从Java EE服务器获取指定的EntityManagerFactory，
 * 		【这种方式在进行Spring事务管理时一般要使用JTA事务管理。】
 * 
 * 3，LocalContainerEntityManagerFactoryBean：适用于所有环境的FactoryBean，
 * 		能全面控制EntityManagerFactory配置，如指定Spring定义的DataSource等等。
 */
public class JpaSpringTest {
	
	private ApplicationContext context; 
	
	{
		context = new ClassPathXmlApplicationContext("classpath:application-context.xml");
		
	}
	
	@Test
	public void test() throws SQLException {
		
		DataSource dataSource = context.getBean(DataSource.class);
		
		System.out.println(dataSource);
		System.out.println(dataSource.getConnection());
	}
	
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
}
