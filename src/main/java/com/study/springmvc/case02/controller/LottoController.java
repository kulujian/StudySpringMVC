package com.study.springmvc.case02.controller;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.jasper.tagplugins.jstl.core.ForEach;
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
	public String aa () {
		System.out.println("有連到");
		System.out.println(lottoService.getLottos());
		
		
		
//		lottoService.getLottos()
//					.stream()
//					.flatMap(lotto1 -> lotto1.stream())
//					.filter(numAll -> lottoService.getLottos()
//												.stream()
//												.flatMap(lotto2 -> lotto2.stream())
//												.distinct()
//												.sorted()
//												.anyMatch(num -> {
//													System.out.println("anyMatch : " + num);
//													return num == numAll;
//												})
//					)
//					.forEach(new Consumer<Integer>() {
//						int i = 0;
//						@Override
//						public void accept(Integer t) {
//							i++;
//							System.out.println("");
//							System.out.println("====================");
//							System.out.printf("%d  foreach : %d\n",i, t);
//							System.out.println("====================");
//							System.out.println("");
//						}
//					});
		List<Integer> lottoNum =
		lottoService.getLottos().stream()
					.flatMap(lotto1 -> lotto1.stream())
					.distinct()
					.sorted()
					.collect(Collectors.toList());
		System.out.println(lottoNum);
		
		
		
		return "redirect:./";
	}
	
	
}
