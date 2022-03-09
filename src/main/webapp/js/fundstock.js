google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawChart);
			
function drawChart() {
	drawChart(1);
	drawStockChart('^TWII');
}
  
function drawStockChart(symbol) {
	$.get("/springmvc/mvc/lab/price/histquotes/" + symbol, function(quotes, status) {
//	  	console.log("quotes:" + quotes);
		console.log("status:" + status);
		drawChartHist(symbol, quotes);
	});
}
  
function drawChartHist(symbol, quotes) {
	// 建立 data 欄位
	var data =  new google.visualization.DataTable();
	// 定義欄位
	data.addColumn('string', 'Date');
	data.addColumn('number', 'High');
	data.addColumn('number', 'Open');
	data.addColumn('number', 'Close');
	data.addColumn('number', 'Low');
	data.addColumn('number', 'AdjClose');
	data.addColumn('number', 'Volumn');
	// 加入資料
	$.each(quotes, function (i, item) {
	    var array = [getMD(quotes[i].date), quotes[i].high, quotes[i].open, quotes[i].close, quotes[i].low, quotes[i].adjClose, quotes[i].volume];
	    data.addRow(array);
	});
	console.log("data:" + data);
	// 設定 chart 參數
	var options = {
		title: symbol + ' 日K線圖',
		legend: 'none',
		vAxes: [
			{},
			{minValue: 1, maxValue: 6000000}
		],
		series: {
			1: {targetAxisIndex: 0, type: 'line', color: '#e7711b'},
			2: {targetAxisIndex: 1, type: 'bars', color: '#cccccc'}
		},
		candlestick: {
			fallingColor: {strokeWidth: 0, fill: '#0f9d58'}, // green
			risingColor: {strokeWidth: 0, fill: '#a52714'}   // red
		},
		chartArea: {left: 50}
	};
	// 產生 chart 物件
	var chart = new google.visualization.CandlestickChart(document.getElementById('stockchart'));
	// 繪圖
	chart.draw(data, options);
}


// -- CRUD 功能

function updateFundstock(sid) {
	document.getElementById('fundstock').action = '/springmvc/mvc/lab/fundstock/';
	document.getElementById('fundstock').submit();
}

function deleteFundstock(sid) {
	document.getElementById('_method').value = 'DELETE';
	updateFundstock(sid);
}

