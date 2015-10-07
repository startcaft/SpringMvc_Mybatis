package com.startcaft.mybatis.mapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.startcaft.mybatis.po.OrderDetail;
import com.startcaft.mybatis.po.Orders;
import com.startcaft.mybatis.po.OrdersCustom;
import com.startcaft.mybatis.po.User;

public class OrdersMapperCustomTest {
	
	
	private SqlSessionFactory sqlSessionFactory;
	
	@Before
	public void setup() throws IOException{
		String resources = "SqlMapConfig.xml";
		InputStream inputStream = Resources.getResourceAsStream(resources);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}
	
	@Test
	public void testFindOrdersUser() throws Exception {
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		OrdersMapperCustom orderMapperCustom = sqlSession.getMapper(OrdersMapperCustom.class);
		
		List<OrdersCustom> orders = orderMapperCustom.findOrdersUser();
		
		System.out.println(orders);
	}
	
	//延迟加载
	@Test
	public void testFindOrderUserLoadlazy() throws Exception {
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		OrdersMapperCustom orderMapperCustom = sqlSession.getMapper(OrdersMapperCustom.class);
		
		List<Orders> orders = orderMapperCustom.findOrderUserLoadlazy();
		
		for (Orders order : orders) {
			System.out.println(order.getNumber());
			System.out.println("\t" + order.getUser().getUsername());//执行延迟加载
		}
	}
	
	@Test
	public void testFindOrdersUserByMap() throws Exception {
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		OrdersMapperCustom orderMapperCustom = sqlSession.getMapper(OrdersMapperCustom.class);
		
		List<Orders> orders = orderMapperCustom.findOrdersUserByMap();
		
		System.out.println(orders);
	}
	
	@Test
	public void testFindOrderAndDetailResultMap() throws Exception {
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		OrdersMapperCustom orderMapperCustom = sqlSession.getMapper(OrdersMapperCustom.class);
		
		List<Orders> orders = orderMapperCustom.findOrderAndDetailResultMap();
		
		for (Orders order : orders) {
			System.out.println(order.getId() + "\\\\\\");
			for (OrderDetail detail : order.getOrderDetails()) {
				System.out.println("\t" + detail.getItemsId() + ":" + detail.getNumber());
			}
		}
	}
	
	
	@Test
	public void testFindUserAndItemsResultMap() throws Exception {
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		OrdersMapperCustom orderMapperCustom = sqlSession.getMapper(OrdersMapperCustom.class);
		
		List<User> user = orderMapperCustom.findUserAndItemsResultMap();
		
		for (User u : user) {
			System.out.println(u.getUsername() + "\\\\\\");
			for (Orders order : u.getOrderList()) {
				System.out.println("\t" + order.getNumber());
				for (OrderDetail detail : order.getOrderDetails()) {
					System.out.println("\t" + detail.getNumber() + "|" + detail.getItems().getName() + "|" + detail.getItems().getPrice());
				}
			}
		}
	}
	
	//测试一级缓存
	@Test
	public void testCache1() throws Exception{
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		
		//下面的两次请求在一个SqlSession中
		//第一次发起请求，
		User user1 = userMapper.findUserById(1);
		System.out.println(user1);
		
		//如果SqlSession执行了commit(执行插入，更新，删除)，SqlSession会清空一级缓存。
		user1.setAddress("湖北武汉");
		userMapper.updateUser(user1);
		sqlSession.commit();
		
		//第二次发起请求
		User user2 = userMapper.findUserById(1);
		System.out.println(user2);
		
		sqlSession.close();
	}
	
	//测试二级缓存
	@Test
	public void testCache2() throws Exception{
		
		SqlSession sqlSession1 = sqlSessionFactory.openSession();
		SqlSession sqlSession2 = sqlSessionFactory.openSession();
		SqlSession sqlSession3 = sqlSessionFactory.openSession();
		
		UserMapper userMapper1 = sqlSession1.getMapper(UserMapper.class);
		UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);
		UserMapper userMapper3 = sqlSession3.getMapper(UserMapper.class);
		

		User user1 = userMapper1.findUserById(1);
		System.out.println(user1);
		//这里执行关闭操作，将SqlSession中的数据才能写入到二级缓存区域。
		sqlSession1.close();
		
		//执行提交操作，清空二级缓存。
		User user3 = userMapper3.findUserById(1);
		user3.setAddress("武汉");
		userMapper3.updateUser(user3);
		sqlSession3.commit();
		sqlSession3.close();
		
		User user2 = userMapper2.findUserById(1);
		System.out.println(user2);
		sqlSession2.close();
		
	}
}
