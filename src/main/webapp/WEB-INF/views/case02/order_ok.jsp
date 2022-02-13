<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Order OK</title>
</head>
<body>
	<!-- 以下不一樣的地方，是查詢物件是放在哪裡傳送 -->
	不加任何 Scope <p/> 
	name：${ name }<p/>
	price：${ price }<p/>
	qty：${ qty }<p/>
	<hr/>
	用requestScope<p/>
	name：${ requestScope.name }<p/>
	price：${ requestScope.price }<p/>
	qty：${ requestScope.qty }<p/>
	<hr/>
	以下是三種 Scope
	requestScope<p/>
	name：${ requestScope.name }<p/>
	sessionScope<p/>
	price：${ sessionScope.price }<p/>
	applicationScope<p/>
	qty：${ applicationScope.qty }<p/>
</body>
</html>