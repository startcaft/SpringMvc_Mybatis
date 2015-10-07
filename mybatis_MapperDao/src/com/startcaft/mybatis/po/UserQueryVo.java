package com.startcaft.mybatis.po;

import java.util.List;

/**
 * 用户包装类，用于扩展User类，主要包装查询条件
 */
public class UserQueryVo {
	
	//在这里包装需要的查询条件
	private List<Integer> ids;
	
	//包装用户查询条件
	private UserCustom userCustom;
	
	public UserCustom getUserCustom() {
		return userCustom;
	}
	public void setUserCustom(UserCustom userCustom) {
		this.userCustom = userCustom;
	}
	public List<Integer> getIds() {
		return ids;
	}
	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
	//包装其他查询条件
}
