package com.startcaft.mybatis.mapper;

import java.util.List;

import com.startcaft.mybatis.po.Orders;
import com.startcaft.mybatis.po.OrdersCustom;
import com.startcaft.mybatis.po.User;

/**订单Mapper**/
public interface OrdersMapperCustom {
	
	/**查询订单，关联查询用户**/
	public List<OrdersCustom> findOrdersUser() throws Exception;
	
	/**查询订单，关联查询用户 使用resultMap映射输出**/
	public List<Orders> findOrdersUserByMap() throws Exception;
	
	/**查询订单，关联用户(一对一)，关联的明细(一对多)**/
	public List<Orders> findOrderAndDetailResultMap() throws Exception;
	
	/**查询用户，所购买的商品的信息**/
	public List<User> findUserAndItemsResultMap() throws Exception;
	
	/**查询订单，延迟加载所关联的用户信息**/
	public List<Orders> findOrderUserLoadlazy() throws Exception;
	
}
