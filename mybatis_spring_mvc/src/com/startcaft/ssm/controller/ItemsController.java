package com.startcaft.ssm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.portlet.ModelAndView;

import com.startcaft.ssm.po.ItemsCustom;
import com.startcaft.ssm.service.ItemsService;

@Controller
@RequestMapping("/items")
public class ItemsController {
	
	@Autowired
	private ItemsService service;
	
	@RequestMapping(value="/queryItems",method=RequestMethod.GET)
	public ModelAndView itemsList(){
		
		List<ItemsCustom> items = null;
		try {
			items = service.findItemsList(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ModelAndView mv = new ModelAndView("items/itemsList");
		mv.addObject("itemsList", items);
		
		return mv;
	}
}
