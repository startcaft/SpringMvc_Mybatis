package com.startcaft.jpa.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamedQuery(name="testNamedQuery",query="select c from Customer c where c.id=?")
@Entity
@Cacheable(true)
@Table(name="JPA_CUSTOMERS")
public class Customer {
	
	public Integer id;
	private String lastName;
	private String email;
	private Integer age;
	private Date birthday;
	private Date createTime;
	
	private Set<Order> orders = new HashSet<Order>();
	
	@Id
	@GeneratedValue
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
	
	@Temporal(TemporalType.DATE)
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	/**使用OneToMany 注解来映射 一对多 的关联关系**/
	/**使用JoinColumn 注解来映射 外键列的名称**/
	@OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.REMOVE})
	@JoinColumn(name="CUSTOMER_ID")
	public Set<Order> getOrders() {
		return orders;
	}
	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
	@Override
	public String toString() {
		return "Customer [id=" + id + ", lastName=" + lastName + ", email="
				+ email + ", age=" + age + ", birthday=" + birthday
				+ ", createTime=" + createTime + ", orders=" + orders + "]";
	}
	
	public Customer(String lastName, Integer age) {
		super();
		this.lastName = lastName;
		this.age = age;
	}
	
	public Customer() {
		// TODO Auto-generated constructor stub
	}
}
