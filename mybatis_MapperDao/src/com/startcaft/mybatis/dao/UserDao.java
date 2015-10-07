package com.startcaft.mybatis.dao;

import java.util.List;

import com.startcaft.mybatis.po.User;

public interface UserDao {
	
	//根据id查询用户信息
	public User findUserById(int id) throws Exception;
	
	//添加用户的信息
	public void insertUser(User user) throws Exception;
	
	//删除用户的信息
	public void deleteUser(int id) throws Exception;
	
	//根据name模糊查询用户
	public List<User> findUserByName(String name) throws Exception;
}
