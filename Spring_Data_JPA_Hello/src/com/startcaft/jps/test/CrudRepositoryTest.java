package com.startcaft.jps.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.startcaft.jpa.spring.entity.Person;
import com.startcaft.jpa.startcaft.service.PersonService;

public class CrudRepositoryTest {
	
	private ApplicationContext context; 
	private PersonService personService;
	
	{
		context = new ClassPathXmlApplicationContext("classpath:application-context.xml");
		personService = context.getBean(PersonService.class);
	}
	
	@Test
	public void test_save_persons(){
		
		List<Person> persons = new ArrayList<Person>();
		
		for (int i = 'a'; i <= 'z'; i++) {
			
			Person person = new Person();
			person.setLastName((char)i + "" + (char)i);
			person.setBirthday(new Date());
			person.setEmail((char)i + "" + (char)i + "@163.com");
			person.setAddressId(i + 1);
			
			persons.add(person);
		}
		
		personService.savePersons(persons);
	}
}
