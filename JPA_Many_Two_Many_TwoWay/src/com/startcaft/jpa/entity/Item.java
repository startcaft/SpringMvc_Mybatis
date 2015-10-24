package com.startcaft.jpa.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@Table(name="JPA_ITEMS")
public class Item {
	
	private Integer id;
	private String itemName;
	
	private Set<Category> categories = new HashSet<Category>();

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="ITEM_NAME")
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**使用@ManyToMany 注解来映射 n-n 关联关系**/
	/**使用@JoinTable 注解来映射中间表**/
	/**1，joinColumns属性代表当前表在中间表的外键字段，并使用referencedColumnName属性指向当前表的主键字段**/
	/**2，inverseJoinColumns属性关联表在中间表的外键字段，并使用referencedColumnName属性指向关联表的主键字段**/
	@ManyToMany
	@JoinTable(name="JPA_CATEGORY_ITEM_MIDDLE",
				joinColumns={@JoinColumn(name="ITEM_ID",referencedColumnName="id")},
				inverseJoinColumns={@JoinColumn(name="CATEGORY_ID",referencedColumnName="id")}
				)
	public Set<Category> getCategories() {
		return categories;
	}
	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}
	
}
