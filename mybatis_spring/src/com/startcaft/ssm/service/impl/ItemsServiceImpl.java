package com.startcaft.ssm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.startcaft.ssm.mapper.ItemsMapper;
import com.startcaft.ssm.po.Items;
import com.startcaft.ssm.service.ItemsService;

@Service
public class ItemsServiceImpl implements ItemsService {
	
	@Autowired
	private ItemsMapper mapper;
	
	@Override
	public Items findItemsById(Integer id) {
		
		return mapper.selectByPrimaryKey(id);
	}

}
