package com.startcaft.ssm.mapper;

import java.util.List;

import com.startcaft.ssm.po.ItemsCustom;
import com.startcaft.ssm.po.ItemsQueryVo;

public interface ItemsCustomMapper {
	
	/**综合查询商品信息**/
	public List<ItemsCustom> findItemsList(ItemsQueryVo query) throws Exception;
	
	/**综合查询商品的总数**/
	public int findItemsListCount(ItemsQueryVo query) throws Exception;
}
