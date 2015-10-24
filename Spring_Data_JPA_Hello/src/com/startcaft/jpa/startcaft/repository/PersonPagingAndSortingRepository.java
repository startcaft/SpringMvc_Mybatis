package com.startcaft.jpa.startcaft.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.startcaft.jpa.spring.entity.Person;

/*
 * PagingAndSortingRepository 接口是 CrudRepository的子接口，它提供了分页以及排序的功能
 */
public interface PersonPagingAndSortingRepository extends PagingAndSortingRepository<Person, Integer> {

}
