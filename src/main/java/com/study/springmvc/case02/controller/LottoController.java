package com.study.springmvc.case02.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

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
	public String aa (Model model) {
		System.out.println("有連到");
		System.out.println(lottoService.getLottos());
		
		
		
		/*
		 *  先有功能
		 *  1. Map 最終輸出容器
		 *  2. List 1,去重留下所有出現過的號碼
		 *  3. List 2,保留所有出現過的號碼，比對List若重覆出現則數量加1
		 *  4. for巢狀比對兩個List並put至Map容器，重覆則對應Key的value值初始為零依次加1
		 *  5. Map轉流 forEach 輸出
		 */
		Map<Integer, Integer> amountMap1 = new HashMap<Integer, Integer>();
		List<Integer> lottoNum1 =
				lottoService.getLottos().stream()
					.flatMap(lottoStr1 -> lottoStr1.stream())
					.distinct()
//					.sorted()
					.collect(Collectors.toList());
		List<Integer> lottoNum2 =
				lottoService.getLottos().stream()
					.flatMap(lottoStr2 -> lottoStr2.stream())
//					.sorted()
					.collect(Collectors.toList());
		
		for (Integer numOnly : lottoNum1) {
			Integer i = 0;
			amountMap1.put(numOnly, 0);
			for (Integer numAll : lottoNum2) {
				if(numOnly == numAll) {
					i++;
					amountMap1.put(numOnly, i);
				}
			}
		}		
		amountMap1.entrySet().stream()
		.sorted((Entry<Integer, Integer> o1, Entry<Integer, Integer> o2) -> o1.getKey() - o2.getKey())
		.sorted((Entry<Integer, Integer> o1, Entry<Integer, Integer> o2) -> o2.getValue() - o1.getValue())
		.forEach(t -> System.out.printf("%2d(%d) ", t.getKey(), t.getValue()));
		System.out.println();
		System.out.println("=========================================");
		
		// 優化一
		Map<Integer, Integer> amountMap2 = new HashMap<Integer, Integer>();
		lottoService.getLottos().stream()
					.flatMap(lottoStr -> lottoStr.stream())
					.distinct()
					.forEach(num01 -> { 
						amountMap2.put(num01, 0);
						lottoService.getLottos().stream()
									.flatMap(lottoStr -> lottoStr.stream())
									.forEach(num02 -> {
										if(num01 == num02) {
											amountMap2.put(num01, amountMap2.get(num01)+1);
										}
									});
					});
		amountMap2.entrySet().stream()
		.sorted((o1,  o2) -> o1.getKey() - o2.getKey())
		.sorted((Entry<Integer, Integer> o1, Entry<Integer, Integer> o2) -> o2.getValue() - o1.getValue())
		.forEach(t -> System.out.printf("%2d(%d) ", t.getKey(), t.getValue()));
		System.out.println();
		System.out.println("=========================================");
		
		
		//優化二
		
		Map<Integer,Integer> amountMap3 = lottoService.getLottos().stream()
					.flatMap(lottoStr -> lottoStr.stream())
					.sorted()
					.collect(
								Collectors.toMap(
									new Function<Integer, Integer>() {
										@Override
										public Integer apply(Integer num_O) {
											return num_O;
										}
									}, 
									new Function<Integer, Integer>() {
										int i = 1;
										int temp = 0;
										@Override
										public Integer apply(Integer num_A) {
											System.out.println(i);
											System.out.println(temp);
											if(temp == num_A) {
												temp =num_A;
												return i+1;
											}else {
												i = 1;
												return i;
											}
										}
									},
									(oldValue, newValue) -> newValue
								)
							);
		
		
		
		
		
		amountMap3.entrySet().stream()
		.sorted((Entry<Integer, Integer> o1, Entry<Integer, Integer> o2) -> o1.getKey() - o2.getKey())
		.sorted((Entry<Integer, Integer> o1, Entry<Integer, Integer> o2) -> o2.getValue() - o1.getValue())
		.forEach(t -> System.out.printf("%2d(%d) ", t.getKey(), t.getValue()));
		System.out.println();
		System.out.println("=========================================");
		
		

		model.addAttribute("amount", lottoService.getLottos());
		
		return "redirect:./";
	}
	
	
}
