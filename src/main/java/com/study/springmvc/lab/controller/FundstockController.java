package com.study.springmvc.lab.controller;

import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
		return "redirect: ./page/" + pageNumber + "/";
	}

	@GetMapping("/page/{pageNumber}/")
//	@ResponseBody
//	public List<Fundstock> page(@PathVariable("pageNumber") int pageNumber){
	public String page(@PathVariable("pageNumber") Integer pageNumber, @ModelAttribute Fundstock fundstock, Model model){
		// 1 => 0, 2 => 5, 3 => 10，....
		this.pageNumber = pageNumber;
		int offset = (pageNumber - 1) * FundstockDao.LIMIT;
		List<Fundstock> fundstocks = fundstockDao.queryPage(offset);
		List<Fund> funds = fundDao.queryAll();
		int pageTotalcount = fundstockDao.count() / FundstockDao.LIMIT;
		System.out.println("page : " + pageTotalcount);
		model.addAttribute("_method", "POST");
		model.addAttribute("fundstocks", fundstocks);
		model.addAttribute("funds", funds);
		model.addAttribute("pageTotalCount", pageTotalcount);
		model.addAttribute("groupMap", getGroupMap());
		
		return "lab/fundstock";
	}
	
	@GetMapping("/page/{pageNumber}/{sid}")
//	@ResponseBody
//	public Fundstock get(@PathVariable("sid") Integer sid){
//		return fundstockDao.get(sid);
	public String get(@PathVariable("sid") Integer sid, 
						@PathVariable("pageNumber") Integer pageNumber, 
						@ModelAttribute Fundstock fundstock, Model model){
		this.pageNumber = pageNumber;
		int offset = (pageNumber - 1) * FundstockDao.LIMIT;
		List<Fundstock> fundstocks = fundstockDao.queryPage(offset);
		List<Fund> funds = fundDao.queryAll();
		int pageTotalcount = fundstockDao.count() / FundstockDao.LIMIT;
		System.out.println("get : " + pageTotalcount);
		model.addAttribute("_method", "PUT");
		model.addAttribute("fundstocks", fundstocks);
		model.addAttribute("fundstock", fundstockDao.get(sid));
		model.addAttribute("funds", funds);
		model.addAttribute("pageTotalCount", pageTotalcount);
		model.addAttribute("groupMap", getGroupMap());
		
		return "lab/fundstock";
	}
	

	@GetMapping("/group/test")
	@ResponseBody
	private Map<String, Integer> getGroupMap() {
		/** 以下[Java 8]語法同等於此[SQL]語法
		 * 	select s.symbol, sum(s.share) as share
		 * 	from fundstock s
		 * 	group by s.symbol 
		 */
		List<Fundstock> fundstocks =  fundstockDao.queryAll();
		return fundstocks.stream()
				.collect(groupingBy(Fundstock::getSymbol, 
									summingInt(Fundstock::getShare)));
	}
	
	@PostMapping("/")  // 註意，若看到@requestBody 
	public int add(@RequestBody Fundstock fundstock) {
		System.out.println("PostMapp");
		fundstockDao.add(fundstock);
		return 0;
	}
	
	@PutMapping("/aaa/")
	public String update(@RequestBody Fundstock fundstock) {
//		System.out.println(fundstockDao.update(fundstock));
		System.out.println(fundstock.getFid());
		System.out.println("PutMapp");
		return "redircet: ./";
	}
	
	@DeleteMapping("/{fid}")
	public int delete(@PathVariable("fid") Integer sid) {
		System.out.println("DeleteMapp");
		return fundstockDao.delete(sid);
	}
}
