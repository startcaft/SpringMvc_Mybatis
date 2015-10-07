package com.startcaft.mybatis.po;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Integer id;
	private String username;
	private Date birthday;
	private String sex;
	private String address;
	//用户所有的订单
	private List<Orders> orderList;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<Orders> getOrderList() {
		return orderList;
	}
	public void setOrderList(List<Orders> orderList) {
		this.orderList = orderList;
	}
}
