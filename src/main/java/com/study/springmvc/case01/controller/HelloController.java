package com.study.springmvc.case01.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller							// 宣告為 Controller
@RequestMapping("/case01/hello")	// 設定控制器響應路徑
public class HelloController {
	
	@RequestMapping("/welcome")		// 設定頁面響應路徑
	@ResponseBody					// 回應方式(直接回應回傳內容)
	public String welcome() {
		return "Hello SpringMVC " + new Date();
	}
}