package com.startcaft.jpa.startcaft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.startcaft.jpa.spring.entity.Person;
import com.startcaft.jpa.startcaft.dao.PersonDao;

@Service
public class PersonService {
	
	@Autowired
	private PersonDao personDao;
	
	@Transactional
	public void savePersons(Person p1,Person p2){
		
		personDao.save(p1);
		
		int i = 10/0;//手动制造一个异常。
		
		personDao.save(p2);
	}
}
