package com.startcaft.ssm.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.startcaft.ssm.po.User;


@Repository
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	@Override
	public User findUserById(int id) throws Exception {
		
		SqlSession sqlSession = this.sqlSessionFactory.openSession();
		
		User user = sqlSession.selectOne("test.findUserById", id);
		
		return user;
	}
}
