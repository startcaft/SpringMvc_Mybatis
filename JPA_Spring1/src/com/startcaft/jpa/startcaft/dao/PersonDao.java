package com.startcaft.jpa.startcaft.dao;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import com.startcaft.jpa.spring.entity.Person;


@Repository
public class PersonDao {
	
	//如何获取到和当前事务关联的EntityManager对象呢？
	//通过@PersistenceContext 注解来标记成员变量
	@PersistenceContext
	private EntityManager entityManager;
	
	public void save(Person person){
		
		entityManager.persist(person);
	}
}
