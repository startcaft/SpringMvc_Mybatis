package com.startcaft.mybatis.po;

public class OrderDetail {
	
	private Integer id;
	private Integer ordersId;
	private Integer itemsId;
	private Integer number;
	//对应的商品
	private Items items;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getOrdersId() {
		return ordersId;
	}
	public void setOrdersId(Integer ordersId) {
		this.ordersId = ordersId;
	}
	public Integer getItemsId() {
		return itemsId;
	}
	public void setItemsId(Integer itemsId) {
		this.itemsId = itemsId;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Items getItems() {
		return items;
	}
	public void setItems(Items items) {
		this.items = items;
	}
}
