package com.startcaft.mybatis.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.startcaft.mybatis.dao.UserDao;
import com.startcaft.mybatis.po.User;

public class UserDaoImpl implements UserDao {
	
	private SqlSessionFactory sqlSessionFactory;
	
	//需要向DAO实现类中注入SqlSessionFactory
	//这里通过构造函数注入
	public UserDaoImpl(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	@Override
	public User findUserById(int id) throws Exception {
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		User user = sqlSession.selectOne("test.findUserById", id);
		
		sqlSession.close();
		
		return user;
	}

	@Override
	public void insertUser(User user) throws Exception {
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		sqlSession.insert("test.insertUser", user);
		
		sqlSession.commit();
		
		sqlSession.close();
		
	}

	@Override
	public void deleteUser(int id) throws Exception {
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		sqlSession.delete("test.deleteUser", id);
		
		sqlSession.commit();
		
		sqlSession.close();

	}

	@Override
	public List<User> findUserByName(String name) throws Exception {
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		List<User> users = sqlSession.selectOne("test.findUserByName", name);
		
		sqlSession.close();
		
		return users;
	}

}
