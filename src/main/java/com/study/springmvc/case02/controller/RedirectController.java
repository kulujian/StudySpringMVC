package com.study.springmvc.case02.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/case02/redirect")
public class RedirectController {

	/*
	 * 重定向 redirect:
	 * 由 server 端發出 redirect 命令(會放在 header 中)給 client 端，並由 client 端去執行
	 * 所以 client 端的瀏覽器網址會發生改變
	 * 重定向可以指向內網與外網
	 * 通過 GET 帶資訊
	 * */

	/*
	 * http://localhost:8080/springmvc/mvc/case02/redirect/demo1
	 */
	@RequestMapping("/demo1")
	public String demo1() {
		return "redirect:/index.jsp";
	}
	/* Lab (demo2) 我要透過 redirect: 重定向到 show_time.jsp 要如何寫
	 * http://localhost:8080/springmvc/mvc/case02/redirect/demo2
	 */

	@RequestMapping("/demo2")
	public String demo2() {
//		return "redirect:/mvc/case02/time/now"; // 絕對路徑
		return "redirect:../time/now"; // 相對路徑
	}

	/*
	 * http://localhost:8080/springmvc/mvc/case02/redirect/demo3
	 */
	@RequestMapping("/demo3")
	public String demo3() {
		return "redirect:http://tw.yahoo.com";
	}
	
	/*
	 * http://localhost:8080/springmvc/mvc/case02/redirect/demo4
	 */
	// 重定向帶參務 I (使用 URL GET 傳送)
	@RequestMapping("/demo4")
	public String demo4() {
		return "redirect:/show_param.jsp?username=Kulu&age=39";
	}

	/*
	 * http://localhost:8080/springmvc/mvc/case02/redirect/demo5
	 */
	// 重定向帶參務 II (使用 RedirectAttributes addAttribute)
	@RequestMapping("/demo5")
	public String demo5(RedirectAttributes attr) {
		attr.addAttribute("username", "Jian");
		attr.addAttribute("age", "38");
		return "redirect:/show_param.jsp";
	}
	
	/*
	 *  訂單結束後，防止客戶重新整理
	 *  http://localhost:8080/springmvc/mvc/case02/redirect/saveOrder?name=iPhone&price=25000&qty=5
	 */
	// 重定向帶參務 II (使用 RedirectAttributes addFlashAttribute)
	@RequestMapping("/saveOrder")
	public String saveOrder(@RequestParam("name")String name,
							@RequestParam("price")Integer price,
							@RequestParam("qty")Integer qty,
							RedirectAttributes attr) {
		attr.addFlashAttribute("name", name);
		attr.addFlashAttribute("price", price);
		attr.addFlashAttribute("qty", qty);
		return "redirect:./success";
	}
	@RequestMapping("/success")
	public String saveOrder() {
		return "case02/order_ok";
	}
	
}
