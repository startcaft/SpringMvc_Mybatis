package com.startcaft.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="JPA_MANAGERS")
public class Manager {
	
	private Integer id;
	private String managerName;
	
	private Department department;

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="MGR_NAME")
	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	
	/**对于不需要维护关联关系的一方，使用@OneToOne 注解，需要设置mappedBy属性**/
	/**注意，但凡是双向关联关系，不需要维护关系的一方都需要设置mappedBy属性**/
	@OneToOne(mappedBy="manager")
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
}
