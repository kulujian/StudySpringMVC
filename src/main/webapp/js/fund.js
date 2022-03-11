	// 頁面載入完成後所要執行的程序
	$(document).ready(function() {
		// 驗證註冊之註冊
		$('#myForm').validate({
			onsubmi: false,
			onkeyup: false,   // false 執行驗證時才跳提醒 , true 成為焦點之後持續提醒
			rules:{
				fname: { // fname 指的是 input Tag的 name 而不是 id
					//required: true,  // 如何Tag端的打，這邊可以不打
					rangelength: [2,50]
				}
			},
			messages: { // 自訂錯誤訊息
				fname: { // fname 指的是 input Tag的 name 而不是 id
					required: "請輸入基金名稱",
					rangelength: "基金名稱長度必介於 {0}~{1} 之間"
				}
			}
		});
		// 用呼叫的方式執行方法，比較好
		// Fund List 的資料列表
		table_list();
		// 註冊相關事件
		$('#add').on('click', function() {
			addOrUpdate('POST');
		});
		// click tr 時 { 傳 this}
		$('#myTable').on('click', 'tr', function() {
			getItem(this);
		});
		$('#upt').on('click', function() {
			addOrUpdate('PUT');
			btnAttr(0);
		});
		$('#del').on('click', function() {
			deleteItem();
			btnAttr(0);
		});
		$('#rst').on('click', function() {
			btnAttr(0);
			// From reset
			$('#myForm').trigger('reset');
			//$('#myForm').trigger('submit'); 會吃表單 Action 路徑
		});
		
	});
	
	function queryFund(pageNumber){
		var path = "../mvc/lab/fund/";
		if(pageNumber > 0){
			path = "../mvc/lab/fund/page/" + pageNumber;
		}

		// 取得所有 fund 資料
		$.get(path, function(datas, status) {
			console.log(Object.keys(datas).length);
			console.log(status);
			var pageNumber = Object.keys(datas).length;
			if(pageNumber % 5 > 0){
				alert("進來了")	;
				for(var i=1; i<(pageNumber/5)+1; i++){
					console.log(i);
					var html = "<samp class='mylink' onclick='queryPage({0})'>{1}</samp>&nbsp;";
					$('#pagesLink').append(String.format(html, i, i));
				}
			}else{
			}
			// 清除目前 myTable 上的舊有資料
			$('#myTable tbody').empty();
			//$('#myTable tbody').remove();
			// 將資料 datas 將 myTable 中
			$.each(datas, function(i, item) {
				var html = '<tr><td>{0}</td><td>{1}</td><td>{2}</td><td>{3}</td></tr>';
				$('#myTable').append(String.format(html, item.fid, item.fname, item.createtime, item.fundstocks));
			});
			
		});
	}

	function getItem(elem) {
		var fid = $(elem).find('td').eq(0).text().trim();
		console.log(fid);
		var path = '../mvc/lab/fund/' + fid;
		var func = function(fund, status) {
			console.log(fund);
			// 將資料配置到 myForm 表單中
			$('#myForm').find('#fid').val(fund.fid);
			$('#myForm').find('#fname').val(fund.fname);
			// 修改 btn 狀態
			btnAttr(1);
			// 該筆資料是否能刪除，取決於 fund 物件下面是否有 fundstock 陣列物件
			console.log(fund.fundstocks.length);
			if(fund.fundstocks.length > 0) {
				$('#myForm').find('#del').attr('disabled', true);
			}
		};
		$.get(path, func);
	}
	
	function addOrUpdate(method) {
		// 驗證註冊之驗證, 註冊訊息會放在 valid()內。
		console.log($('#myForm').valid());
		if(!$('#myForm').valid()) { // 判斷是否表單驗證成功
			return;
		}
		// 將表單欄位資料 json 物件序列化
		var jsonObject = $('#myForm').serializeObject();
		// 將 json 物件轉為 json 字串
		var jsonString = JSON.stringify(jsonObject);
		
		console.log(jsonObject);
		// 將資料傳遞到後端
		$.ajax({
			url: "../mvc/lab/fund/",
			type: method,
			contentType: 'application/json;charset=utf-8',
			data: jsonString,
			success: function (respData) {
				console.log(respData);
				// 列表資料更新
				table_list();
				// rst 狀態
				btnAttr(0);
				// From reset
				$('#myForm').trigger('reset');
				//$('#myForm').trigger('submit'); 會吃表單 Action 路徑
				//iptEmpty();
			},
			error: function (textStatus, errorThrown, http){
				var errorInfoText = JSON.stringify(http);
				console.log(errorInfoText.includes('REFERENCES'));
				if(errorInfoText.includes('REFERENCES')) {
					alert('該筆資料無法刪除，原因：因為此基金下有成分股的參照');
				}else{
					alert('該筆資料無法刪除，原因：' + textStatus);
				}
				console.log(textStatus);
			}
		});
	}
	
	function deleteItem() {
		var fid = $('#myForm').find('#fid').val();
		$.ajax({
			url: '../mvc/lab/fund/' + fid,
			type: 'DELETE',
			contentType: 'application/json;charset=utf-8',
			success: function (respData) {
				console.log(respData);
				// 列表資料更新
				table_list();
				// rst 狀態
				btnAttr(0);
				// From reset
				$('#myForm').trigger('reset');
				//$('#myForm').trigger('submit'); 會吃表單 Action 路徑
			},
			error: function (textStatus, errorThrown, http) {
				console.log(textStatus);
			}
		});
	}
	
	
	// Fund List 的資料列表
	function table_list() {
		queryFund(0);
	}
	
	
	function btnAttr(status) {
		// 修改，刪除狀態
		$('#myForm').find('#add').attr('disabled', status != 0);
		$('#myForm').find('#upt').attr('disabled', status == 0);
		$('#myForm').find('#del').attr('disabled', status == 0);
	}
	