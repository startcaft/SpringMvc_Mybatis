package com.startcaft.mybatis.mapper;

import java.util.List;

import com.startcaft.mybatis.po.User;
import com.startcaft.mybatis.po.UserCustom;
import com.startcaft.mybatis.po.UserQueryVo;

/**Mapper接口，相当于Dao接口**/
public interface UserMapper {
	
	//用户综合查询
	public List<UserCustom> findUserList(UserQueryVo queryVo) throws Exception;
	
	//用户综合查询总数
	public int findUserListCount(UserQueryVo queryVo) throws Exception;
	
	//根据id查询用户信息
	public User findUserById(int id) throws Exception;
	
	public User findUserByIdResultMap(int id) throws Exception;
	
	//根据name模糊查询用户
	public List<User> findUserByName(String name) throws Exception;
	
	//添加用户的信息
	public void insertUser(User user) throws Exception;
	
	//删除用户的信息
	public void deleteUser(int id) throws Exception;
	
	//更新用户的信息
	public void updateUser(User user) throws Exception;

}
