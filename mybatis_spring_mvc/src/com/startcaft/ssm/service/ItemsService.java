package com.startcaft.ssm.service;

import java.util.List;

import com.startcaft.ssm.po.ItemsCustom;
import com.startcaft.ssm.po.ItemsDto;
import com.startcaft.ssm.po.ItemsQueryVo;


public interface ItemsService {
	
	public ItemsDto findItemsById(Integer id) throws Exception;
	
	public List<ItemsCustom> findItemsList(ItemsQueryVo query) throws Exception;
	
	public int findItemsListCount(ItemsQueryVo query) throws Exception;
}
