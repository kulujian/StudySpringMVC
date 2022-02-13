package com.study.springmvc.case02.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.study.springmvc.case02.service.ArrayDataService;

@Controller
// Base路徑：/case02/time
@RequestMapping("/case02/arraydata")
public class ArrayDataController {

	@Autowired
	private ArrayDataService arrayDataService;
	

	/*
	 * view 渲染器  (多筆資料)
	 * */
	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("names", arrayDataService.getNames());
		model.addAttribute("fruits", arrayDataService.getFruits());
		return "case02/show_data";
	}
}
