<%@ page isErrorPage="true"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spform"
	uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet"
			href="https://unpkg.com/purecss@2.0.6/build/pure-min.css">
		<meta charset="UTF-8">
		<title>Fundstock Form</title>
		<style type="text/css">
		.error {
			color: #FF0000
		}
		</style>
		<script
			src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
		<script src="${ pageContext.request.contextPath }/js/util.js"></script>
		<script type="text/javascript"
			src="https://www.gstatic.com/charts/loader.js"></script>
		<script type="text/javascript" src="${ pageContext.request.contextPath }/js/fundstock.js" ></script>
		<script type="text/javascript">
			function drawChart(chartId) {
				var data = google.visualization.arrayToDataTable([
					['symbol', 'share'], 
					<c:forEach var="map" items="${ groupMap }">
						['${ map.key }', ${ map.value }],
					</c:forEach>
					]);
				var options = {
					title: 'stock info'
					};
				var chart = new google.visualization.BarChart(document.getElementById('piechart'));
				switch(chartId) {
					case 2:
						chart = new google.visualization.PieChart(document.getElementById('piechart'));
						break;
					case 3:
						chart = new google.visualization.ColumnChart(document.getElementById('piechart'));
						break;
					case 4:
						chart = new google.visualization.LineChart(document.getElementById('piechart'));
			    		break;	
				    }
				chart.draw(data, options);
			}
			
		</script>
		
	</head>
<body style="padding: 15px">
	<table>
		<tr>
			<!-- Fundstock Form -->
			<td valign="top">
				<spform:form class="pure-form" method="post"
					modelAttribute="fundstock"
					action="${ pageContext.request.contextPath }/mvc/lab/fundstock/">
					<fieldset>
						<legend>
							Fundstock Form | <a
								href="${ pageContext.request.contextPath }/mvc/lab/fund/">
								Fund Form | </a> <a
								href="${ pageContext.request.contextPath }/html/fund.html">Fund
								Form???Ajax???</a>
						</legend>
						<input type="hidden" id="_method" name="_method" value="${ _method }" readonly="readonly"><br/>
						?????????
						<spform:input path="sid"  readonly="true" />
						<spform:errors path="sid" cssClass="error" />
						<p />
						?????????
						<spform:input path="symbol" />
						<spform:errors path="symbol" cssClass="error" />
						<p />
						?????????
						<spform:input path="share" />
						<spform:errors path="share" cssClass="error" />
						<p />
						?????????
						<spform:select path="fid">
							<spform:option value="">?????????</spform:option>
							<spform:options items="${ funds }" itemValue="fid"
								itemLabel="fname" />
						</spform:select>
						<p />
						<button type="submit" class="pure-button pure-button-primary" ${ _method=='POST'?'':'disabled' } >
							??????
						</button>
						<button type="button" class="pure-button pure-button-primary" ${ _method=='PUT'?'':'disabled' }  onclick="updateFundstock(${sid})">
							??????
						</button>
						<button type="button" class="pure-button pure-button-primary" ${ _method=='PUT'?'':'disabled' }  onclick="deleteFundstock(${sid})">
							??????
						</button>
						<button type="button" class="pure-button pure-button-primary" >
							??????
						</button>
						<p />
						<spform:errors path="*" cssClass="error" />
					</fieldset>
				</spform:form></td>
			<!-- Fundstock List -->
			<td valign="top">
				<form class="pure-form">
					<fieldset>
						<legend>
							Fundstock List&nbsp;|&nbsp; <a
								href="${ pageContext.request.contextPath }/mvc/lab/fundstock/page/0/">??????</a>
							&nbsp;|&nbsp;
							<c:forEach var="num" begin="1" end="${ pageTotalCount + 1 }">
								<a href="${ pageContext.request.contextPath }/mvc/lab/fundstock/page/${ num }/">
									${ num }
								</a>
							</c:forEach>
						</legend>
						<table class="pure-table pure-table-bordered">
							<thead>
								<tr>
									<th>??????</th>
									<th>??????</th>
									<th>??????</th>
									<th>??????</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="fundstock" items="${ fundstocks }">
									<tr>
										<td><!-- 
											<a href="${ pageContext.request.contextPath }/mvc/lab/fundstock/${ fundstock.sid }">
											 -->
											 <a href="./${ fundstock.sid }">
												${ fundstock.sid }
											</a>
										</td>
										<td>
											<a href="#" onclick="drawStockChart('${ fundstock.symbol }')">
												${ fundstock.symbol }
											</a>
										</td>
										<td>${ fundstock.share }</td>
										<td>${ fundstock.fund.fname }</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</fieldset>
				</form>
			</td>
			<!-- Fundstock chart -->
			<td valign="top">
				<form class="pure-form">
					<fieldset>
						<legend>
							Fundstock Chart | <a href="#" onclick="drawChart(1)">bar</a> | <a
								href="#" onclick="drawChart(2)">pie</a> | <a href="#"
								onclick="drawChart(3)">column</a> | <a href="#"
								onclick="drawChart(4)">line</a>
						</legend>
						<div id="piechart" style="width: 500px; height: 300px;"></div>
					</fieldset>
				</form>
			</td>
		</tr>
		<tr>
			<td colspan="3" valign="top">
				<form class="pure-form">
					<fieldset>
						<legend>
							Fundstock Chart | <a href="#" onclick="drawStockChart('^TWII')">????????????</a>
						</legend>
						<div id="stockchart" style="width: 1500px; height: 500px;"></div>
					</fieldset>
				</form>
			</td>
		</tr>
	</table>




</body>
</html>