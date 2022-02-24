<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	Index <%= new Date() %>
	<ol>
		<li>
			<a href="./mvc/case01/hello/webcome">/webcome</a>
		</li>
		<li>
			<a href="./mvc/case01/hello/sayhi?name=kulu&age=39">/sayhi?name=kulu&age=39(帶參數(?xxx=xxx 配置 @RequestParam))</a>
		</li>
		<li>
			<a href="./mvc/case01/hello/bmi?h=170.0&w=60.0">/bmi?h=170.0&w=60.0(?後帶入參數並計算)</a>
		</li>
		<li>
			<a href="./mvc/case01/hello/exam/75">/exam/75(路徑參數使用)</a>
		</li>
		<li>
			<a href="./mvc/case01/hello/calc/add?x=30&y=20">/calc/add?x=30&y=20(路徑參數與帶入參數混合運用)</a>
		</li>
		<li>
			<a href="./mvc/case01/hello/path/namejo/java8">/path/namejo/java8(萬用字元： *=任意多字；?=任意一字)</a>
		</li>
		<li>
			<a href="./mvc/case01/hello/age?age=18&age=19&age=20">/age?age=18&age=19&age=20(多筆參數資料運用一)</a>
		</li>
		<li>
			<a href="./mvc/case01/hello/javaexam?score=80&score=100&score=50">/javaexam?score=80&score=100&score=50(多筆參數資料運用二)</a>
		</li>
		<li>
			<a href="./mvc/case01/hello/user?name=John&age=18">/user?name=John&age=18(pojo(entity)參數自動匹配)</a>
		</li>
		<li>
			<a href="./mvc/case01/hello/person?name=John&score=100&age=18&pass=true">/person?name=John&score=100&age=18&pass=true(Map 參數，常用於各 form 表單傳來的不統一參數)</a>
		</li>
		<li>
			<a href="./mvc/case01/hello/sessionIfno">/sessionIfno(獲取原生 HttpSession 的資料(傳統方式獲得))</a>
		</li>
		<li>
			<a href="./mvc/case02/time/now">view 渲染器 (渲染單一資料-方法一、ModelAndView.addObject(view),ModelAndView.setViewName("time", time))</a>
		</li>
		<li>
			<a href="./mvc/case02/time/now2">view 渲染器 (渲染單一資料-方法二、new ModelAndView(view, "time", time))</a>
		</li>
		<li>
			<a href="./mvc/case02/time/now3">view 渲染器 (渲染單一資料-方法三、new ModelAndView(view).addObject("time", time))</a>
		</li>
		<li>
			<a href="./mvc/case02/time/now4">view 渲染器 (渲染單一資料-方法四、model.addAttribute("time", time)</a>
		</li>
		<li>
			<a href="./mvc/case02/arraydata/">view 渲染器 (渲染多筆資料、List及Map渲染方式)</a>
		</li>
		<li>
			<a href="./mvc/case02/lotto/">view 渲染器 (增刪查改，統計)</a>
		</li>
		<li>
			<a href="./mvc/case03/exam/">表單綁定呈現及Tag使用介紹 (form表值綁定entity)</a>
		</li>
	</ol>
</body>
</html>