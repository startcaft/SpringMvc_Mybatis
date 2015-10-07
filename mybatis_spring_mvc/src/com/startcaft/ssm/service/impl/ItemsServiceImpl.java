package com.startcaft.ssm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.startcaft.ssm.mapper.ItemsCustomMapper;
import com.startcaft.ssm.mapper.ItemsDtoMapper;
import com.startcaft.ssm.po.ItemsCustom;
import com.startcaft.ssm.po.ItemsDto;
import com.startcaft.ssm.po.ItemsQueryVo;
import com.startcaft.ssm.service.ItemsService;

@Service
public class ItemsServiceImpl implements ItemsService {
	
	@Autowired
	private ItemsDtoMapper mapper;
	
	@Autowired
	private ItemsCustomMapper customMapper;

	@Override
	public ItemsDto findItemsById(Integer id) throws Exception {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public List<ItemsCustom> findItemsList(ItemsQueryVo query) throws Exception {
		return this.customMapper.findItemsList(query);
	}

	@Override
	public int findItemsListCount(ItemsQueryVo query) throws Exception {
		
		return this.customMapper.findItemsListCount(query);
	}
}
