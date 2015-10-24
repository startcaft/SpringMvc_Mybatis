package com.startcaft.jpa.startcaft.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import com.startcaft.jpa.spring.entity.Person;

/*
 * 1，org.springframework.data.repository.Repository<T, ID>是一个空接口，即使一个标记接口
 * 2，若自定义接口继承自Repository<T, ID>接口，则该自定义接口会被IOC 容器识别为一个 Repository Bean，
 * 		纳入到IOC 容器中，进而可以在自定义接口中定义满足一定规范的方法
 * 
 * 3，实际上也可以通过@RepositoryDefinition 注解来替代继承Repository<T, ID>接口，该注解中有两个参数：
 * 		domainClass属性指定操作实体类的类型，idClass属性指定主键的类型
 * 
 * 4，基础的Repository提供了最基本的数据访问功能，其几个子接口则扩展了一些功能，他们的继承关系如下：
 * 		Repository接口仅仅是一个标识接口，表明任何继承自它的均为仓储接口；
 * 		CrudRepository接口继承自Repository,实现了一组CRUD相关的方法；
 * 		PagingAndSortingRepository接口继承自CrudRepository，并实现了一组分页排序相关的方法；
 * 		JpaRepository接口继承自PagingAndSortingRepository，实现了一组jpa规范相关的方法；
 * 
 * 		自定义的XxxxxRepository接口需要继承JpaRepository，这样的自定义的XxxxxRepository
 * 			就具备了通用的数据访问控制层的能力；
 *		
 *		JpaSpeciflcationExecutor：不属于Repository体系，实现了一组JPA的 Criteria 查询相关的方法；
 */
//@RepositoryDefinition(domainClass=Person.class,idClass=Integer.class)
public interface PersonRepository extends Repository<Person, Integer>{
	
	/*
	 * 在Repository子接口中声明方法遵循的规范：
	 * 1，按照 Spring Data 的规范，查询方法必须以 read|find|get 开头;
	 * 2，涉及查询条件时，条件的属性用条件关键字连接;
	 * 3，涉及到属性时，属性的首字母大写。
	 * 
	 * Spring Data 查询支持的关键字如下：
	 * And		Or		Between		LessThan	GreaterThan
	 * After	Before	IsNull		IsNotNull	NotNull
	 * Like		NotLike	OrderBy		EndingWith	StartingWith	
	 * Not		In		NotIn		True		False
	 * IgnoreCase		Containing
	 * 
	 * 如果方法签名是符合Spring Date规范的，是可以不用写实现的
	 * 但是在实际开发中，这种方法并不好用，开发中还是手写jpql，使用@Query注解来完成查询。
	 */
	
	/**根据lastname获取对应的Person**/
	Person getByLastName(String lastName);
	
	//where lastName like ?% and id < ?
	List<Person> findByLastNameStartingWithAndIdLessThan(String lastName,Integer id);
	
	//where email in (?,?,?) and birthday < ?
	List<Person> findByEmailInOrBirthdayLessThan(List<String> emails,Date birthday);
	
	//支持属性的级联查询
	//where address.id > ?
	//List<Person> getByAddressIdGreaterThan(Integer id);//查询到自己的addressId属性了
	List<Person> getByAddress_IdGreaterThan(Integer id);
	
	
	/*******************************************************************************/
	
	
	/*
	 * 使用 @Query 注解可以自定义jpql语句，以实现更为灵活复杂的查询
	 */
	//查询id 值最大的Person
	@Query(value="select p from Person p where p.id = (select max(p2.id) from Person p2)")
	Person getMaxIdPerson();
	
	/*
	 * 为 @Query 注解传递参数的第一种方式：使用占位符，方法形参必须按照占位符的参数顺序指定
	 */
	@Query(value="select p from Person p where p.lastName =? and p.email = ?")
	List<Person> getByQueryWithParam(String lastName,String email);
	
	/*
	 * 为 @Query 注解传递参数的第一种方式：命名参数，方法参数无法按照顺序指定，只要参数名匹配即可
	 */
	@Query(value="select p from Person p where p.lastName =:lastName and p.email = :email")
	List<Person> getByQueryWithParam2(@Param("email")String email,
									@Param("lastName")String lastName);
	
	/*
	 * Spring 允许在 占位符  命令参数 上使用%%，以完成 like 查询，但是这时的占位符必须明确指定顺序。
	 */
	@Query("select p from Person p where p.lastName like %?1% or p.email like %?2%")
	List<Person> getByQueryLikeParam(String lastName,String email);
	
	@Query("select p from Person p where p.lastName like %:lastName% or p.email like %:email%")
	List<Person> getByQueryLikeParam2(@Param("lastName")String lastName,
								@Param("email")String email);
	
	/*
	 * 设置 @Query 注解的 nativeQuery属性为true，就可以使用原生的SQL查询
	 */
	@Query(value="select count(id) from JPA_PERSONS",nativeQuery=true)
	long getTotalCount();
	
	/*
	 * 可以通过自定义的jpql完成delete，update等操作，但是jpql不支持insert操作
	 * 在 @Query 注解中编写jpql，但是必须使用@Modifying 注解进行修饰，以通知SpringData，这是一个update/delete操作
	 * 注意：update/delete操作，需要使用事务，此时需要定义Service层，在Service层的方法上添加事务
	 * 
	 * 默认情况下，SpringData 每个方法上都有事务，但都是一个只读事务，它们不能完成update/delete操作(需要非只读事务)
	 */
	@Modifying
	@Query("update Person p set p.email = ? where p.id = ?")
	void updatePersonEmail(String email,Integer id);
}
