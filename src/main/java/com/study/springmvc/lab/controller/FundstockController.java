package com.study.springmvc.lab.controller;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.springmvc.lab.entity.Fund;
import com.study.springmvc.lab.entity.Fundstock;
import com.study.springmvc.lab.repository.FundDao;
import com.study.springmvc.lab.repository.FundstockDao;

@Controller
@RequestMapping("/lab/fundstock")
public class FundstockController {

	@Autowired
	private FundstockDao fundstockDao;
	
	@Autowired
	private FundDao fundDao;

	private int pageNumber = -1;
	
	@GetMapping("/")
//	@ResponseBody
//	public List<Fundstock> index(){
	public String index(@ModelAttribute Fundstock fundstock, Model model) {
//		List<Fundstock> fundstocks = fundstockDao.queryAll();
//		List<Fund> funds = fundDao.queryAll();
//		int pageTotalcount = fundstocks.size() / FundstockDao.LIMIT;
//		model.addAttribute("_method", "POST");
//		model.addAttribute("fundstocks", fundstocks);
//		model.addAttribute("funds", funds);
//		model.addAttribute("pageTotalCount", pageTotalcount);
//		return "lab/fundstock";
		return "redirect: ./page/" + pageNumber;
	}

	@GetMapping("/page/{pageNumber}")
//	@ResponseBody
//	public List<Fundstock> page(@PathVariable("pageNumber") int pageNumber){
	public String page(@PathVariable("pageNumber") int pageNumber, @ModelAttribute Fundstock fundstock, Model model){
		// 1 => 0, 2 => 5, 3 => 10，....
		this.pageNumber = pageNumber;
		int offset = (pageNumber - 1) * FundstockDao.LIMIT;
		List<Fundstock> fundstocks = fundstockDao.queryPage(offset);
		List<Fund> funds = fundDao.queryAll();
		int pageTotalcount = fundstocks.size() / FundstockDao.LIMIT;
		model.addAttribute("_method", "POST");
		model.addAttribute("fundstocks", fundstocks);
		model.addAttribute("funds", funds);
		model.addAttribute("pageTotalCount", pageTotalcount);
		model.addAttribute("groupMap", getGroupMap());
		
		return "lab/fundstock";
	}
	
	@GetMapping("/{sid}")
	@ResponseBody
	public Fundstock page(@PathVariable("sid") Integer sid){
		return fundstockDao.get(sid);
	}
	

	@GetMapping("/group/test")
	@ResponseBody
	private Map<String, Integer> getGroupMap() {
		/**
		 * select s.symbol, sum(s.share) as share
			from fundstock s
			group by s.symbol 
		 */
		List<Fundstock> fundstocks =  fundstockDao.queryAll();
		return fundstocks.stream()
				.collect(groupingBy(Fundstock::getSymbol, 
						summingInt(Fundstock::getShare)));
//				.entrySet().stream()
//				.sorted((Entry<Integer, Integer> o1, Entry<Integer, Integer> o2) -> o2.getValue() - o1.getValue())
//				.collect(groupingBy(Fundstock::getSymbol, 
//						summingInt(Fundstock::getShare)));
	}
}
