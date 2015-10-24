package com.startcaft.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="JPA_DEPARTMENTS")
public class Department {
	
	private Integer id;
	private String departName;
	
	private Manager manager;

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="DEPART_NAME")
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	
	/**使用@OneToOne 注解来映射 1-1 关联关系**/
	/**若需要在当前表中添加 外键，则使用@JoinColumn 注解，【并且unique属性要为true】**/
	/**双向 1-1 基于外键的关联关联，外键随便放在哪张表上都是ok的**/
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="MGR_ID",unique=true)
	public Manager getManager() {
		return manager;
	}
	public void setManager(Manager manager) {
		this.manager = manager;
	}
}
