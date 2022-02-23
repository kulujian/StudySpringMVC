package com.study.springmvc.case02.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.study.springmvc.case02.service.LottoService;

@Controller
@RequestMapping("/case02/lotto")
public class LottoController {

	@Autowired
	private LottoService lottoService;
	
	// lotto 主畫面
	@RequestMapping("/")
	public String index(Model model) {
		// 取號後顯示
		model.addAttribute("lottos", lottoService.getLottos());
		
		return "case02/show_lotto";
	}
	
	// 電腦選號
	@RequestMapping("/add")
	public String add() {
		lottoService.addLotto();
		return "redirect:./";
	}
	
	// 修改紀錄
	@RequestMapping("/update/{index}")
	public String update(@PathVariable("index") int index) {
		lottoService.updateLotto(index);
		return "redirect:../";
	}
	
	// 刪除紀錄
	@RequestMapping("/delete/{index}")
	public String delete(@PathVariable("index") int index) {
		lottoService.deleteLotto(index);
		return "redirect:../";
	}
	
	// 統計
	@RequestMapping("/aa")
	public String aa (Model model) {
//		System.out.println("有連到");
		model.addAttribute("numbers", lottoService.showLottoNumbers());
		model.addAttribute("lottos", lottoService.getLottos());
		
		
		
		return "case02/show_lotto";
	}
	
	
}
