<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spform" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://unpkg.com/purecss@2.0.6/build/pure-min.css">
<script type="text/javascript" src="${ pageContext.request.contextPath }/js/exam.js"></script>
<meta charset="UTF-8">
<title>Show Exam</title>
</head>
<body style="padding: 15px">
	<table width="100%">
		<tr>
			<!-- Exam Form -->
			<td valign="top">
				<%@ include file="exam_form.jspf" %>
			</td>
			<!-- Exam List -->
			<td valign="top">
				<%@ include file="exam_list.jspf" %>
			</td>
		</tr>
	</table>
	
</body>
</html>