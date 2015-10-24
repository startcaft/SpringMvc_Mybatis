package com.startcaft.jpa.startcaft.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.startcaft.jpa.spring.entity.Person;
import com.startcaft.jpa.startcaft.repository.PersonCrudRepository;
import com.startcaft.jpa.startcaft.repository.PersonRepository;

@Service
public class PersonService {
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private PersonCrudRepository crudRepository;
	
	@Transactional
	public void updatePersonEmail(String email,Integer id){
		
		personRepository.updatePersonEmail(email, id);
	}
	
	@Transactional
	public void savePersons(List<Person> person){
		
		crudRepository.save(person);
	} 
}
