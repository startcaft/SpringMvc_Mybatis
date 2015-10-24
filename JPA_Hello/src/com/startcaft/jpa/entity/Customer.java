package com.startcaft.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name="JPA_CUSTOMERS")
public class Customer {
	
	public Integer id;
	private String lastName;
	private String email;
	private Integer age;
	
	@TableGenerator(name="ID_TABLE_GENERATOR",		//主键策略生成器的名字
					table="jpa_id_generators",		//保存主键的表名
					pkColumnName="PK_NAME",			//保存主键的字段名
					pkColumnValue="CUSTOMER_ID",	//保存主键的名称
					valueColumnName="PK_VALUE",		//保存主键的种子值的字段的名称
					allocationSize=100)				//每次增长的数值
	/**使用Table策略来生成主键,将一个主键种子存在一张表中，然后根据种子计算每次的主键值是多少**/
	@GeneratedValue(strategy=GenerationType.TABLE,generator="ID_TABLE_GENERATOR")
	@Id
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="last_name")
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
}
