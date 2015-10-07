package com.startcaft.ssm.dao;

import com.startcaft.ssm.po.User;

public interface UserDao {
	
	//根据id查询用户信息
	public User findUserById(int id) throws Exception;
	
}
