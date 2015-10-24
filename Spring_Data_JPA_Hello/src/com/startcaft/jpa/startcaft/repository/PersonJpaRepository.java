package com.startcaft.jpa.startcaft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.startcaft.jpa.spring.entity.Person;

public interface PersonJpaRepository extends JpaRepository<Person, Integer> , JpaSpecificationExecutor<Person> {

}
