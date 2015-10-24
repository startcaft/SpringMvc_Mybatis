package com.startcaft.jpa.startcaft.repository;

import org.springframework.data.repository.CrudRepository;
import com.startcaft.jpa.spring.entity.Person;

/*
 * CrudRepository 提供了一些CRUD的基本操作，它是Repository的子接口
 */
public interface PersonCrudRepository extends CrudRepository<Person,Integer> {
	
}
