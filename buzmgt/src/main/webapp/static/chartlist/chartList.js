/**
 * 
 */

var chartList = {

	URL : {

		now : function(regionId,date) {
			var nowUrl;
			if(regionId && date){
				 nowUrl = '/chart/outboundChart'+'?regionId='+regionId+'&date='+date;
			}else{
				 nowUrl = '/chart/outboundChart';
			}
			
			return nowUrl;
		},
		
		cash : function(regionId,date){
			var cashUrl;
			if(regionId && date){
				cashUrl = '/chart/cashChart'+'?regionId='+regionId+'&date='+date;
			}else{
				cashUrl = '/chart/cashChart';
			}
			
			return cashUrl;
		},
		
		report : function(regionId,date){
			var reportUrl;
			if(regionId && date){
				reportUrl = '/chart/reportChart'+'?regionId='+regionId+'&date='+date;
			}else{
				reportUrl = '/chart/reportChart';
			}
			
			return reportUrl;
		},
		unReport : function(regionId,date){
			var cashUrl;
			if(regionId && date){
				unReport = '/chart/unReportChart'+'?regionId='+regionId+'&date='+date;
			}else{
				unReport = '/chart/unReportChart';
			}
			
			return unReport;
		},
		refused : function(regionId,date){
			var refusedUrl;
			if(regionId && date){
				refusedUrl = '/chart/refusedChart'+'?regionId='+regionId+'&date='+date;
			}else{
				refusedUrl = '/chart/refusedChart';
			}
			
			return refusedUrl;
		},
		statement : function(regionId,date){
			var refusedUrl;
			if(regionId && date){
				statementUrl = '/chart/statementChart'+'?regionId='+regionId+'&date='+date;
			}else{
				statementUrl = '/chart/statement';
			}
			
			return refusedUrl;
		},
		paidStatement : function(regionId,date){
			var paidStatement;
			if(regionId && date){
				paidStatementUrl = '/chart/paidStatementChart'+'?regionId='+regionId+'&date='+date;
			}else{
				paidStatementUrl = '/chart/paidStatementChart';
			}
			
			return paidStatementUrl;
		}

	},

	outboundChart : function(regionId,darte) {
		
		
		$.get(chartList.URL.now(regionId,darte), {},
				function(result) {

					if (result && result['success']) {
						var data = result['result'];
						$("#orderPercent").html(
								'<div class="chart" data-percent="'
										+ data['orderPercent'] + '"  ><span >'
										+ data['orderPercent'] 
										+ '</span></div>');
						$("#orders").html(data['orders'] + '单');

						$("#amountPercent").html(
								'<div class="chart" data-percent="'
										+ data['amountPercent'] + '"  ><span >'
										+ data['amountPercent'] 
										+ '</span></div>');

						$("#amount").html(data['amount'] + '元');

						chartList.easyPieChart();
					
					} else {
						console.log(result['errorMsg']);
					}
				})
	},
	
	cashChart : function(regionId,darte){
		$.get(chartList.URL.cash(regionId,darte), {},
				function(result) {

					if (result && result['success']) {
						var data = result['result'];
						$("#cash_orderPercent").html(
								'<div class="chart" data-percent="'
										+ data['orderPercent'] + '"  ><span >'
										+ data['orderPercent'] 
										+ '</span></div>');
						$("#cash_orders").html(data['orders'] + '单');

						$("#cash_amountPercent").html(
								'<div class="chart" data-percent="'
										+ data['amountPercent'] + '"  ><span >'
										+ data['amountPercent'] 
										+ '</span></div>');

						$("#cash_amount").html(data['amount'] + '元');
						
						
						$("#cash_personPercent").html(
								'<div class="chart" data-percent="'
										+ data['personPercent'] + '"  ><span >'
										+ data['personPercent'] 
										+ '</span></div>');

						$("#cash_person").html(data['persons'] + '人');

						chartList.easyPieChart();
					
					} else {
						console.log(result['errorMsg']);
					}
				})
	},
	
	reportChart : function(regionId,darte){
		$.get(chartList.URL.report(regionId,darte), {},
				function(result) {

					if (result && result['success']) {
						var data = result['result'];
						$("#report_orderPercent").html(
								'<div class="chart" data-percent="'
										+ data['orderPercent'] + '"  ><span >'
										+ data['orderPercent'] 
										+ '</span></div>');
						$("#report_orders").html(data['orders'] + '单');

						$("#report_amountPercent").html(
								'<div class="chart" data-percent="'
										+ data['amountPercent'] + '"  ><span >'
										+ data['amountPercent'] 
										+ '</span></div>');

						$("#report_amount").html(data['amount'] + '元');
						
						
						$("#report_personPercent").html(
								'<div class="chart" data-percent="'
										+ data['personPercent'] + '"  ><span >'
										+ data['personPercent'] 
										+ '</span></div>');

						$("#report_person").html(data['persons'] + '人');

						chartList.easyPieChart();
					
					} else {
						console.log(result['errorMsg']);
					}
				})
	},
	unReportChart : function(regionId,darte){
		$.get(chartList.URL.unReport(regionId,darte), {},
				function(result) {

					if (result && result['success']) {
						var data = result['result'];
						$("#unReport_orderPercent").html(
								'<div class="chart" data-percent="'
										+ data['orderPercent'] + '"  ><span >'
										+ data['orderPercent'] 
										+ '</span></div>');
						$("#unReport_orders").html(data['orders'] + '单');

						$("#unReport_amountPercent").html(
								'<div class="chart" data-percent="'
										+ data['amountPercent'] + '"  ><span >'
										+ data['amountPercent'] 
										+ '</span></div>');

						$("#unReport_amount").html(data['amount'] + '元');
						
						    
						$("#unReport_personPercent").html(
								'<div class="chart" data-percent="'
										+ data['personPercent'] + '"  ><span >'
										+ data['personPercent'] 
										+ '</span></div>');

						$("#unReport_person").html(data['persons'] + '人');

						chartList.easyPieChart();
					
					} else {
						console.log(result['errorMsg']);
					}
				})
	},
	refusedChart : function(regionId,darte){
		$.get(chartList.URL.refused(regionId,darte), {},
				function(result) {

					if (result && result['success']) {
						var data = result['result'];
						$("#refused_orderPercent").html(
								'<div class="chart" data-percent="'
										+ data['orderPercent'] + '"  ><span >'
										+ data['orderPercent'] 
										+ '</span></div>');
						$("#refused_orders").html(data['orders'] + '单');

						$("#refused_amountPercent").html(
								'<div class="chart" data-percent="'
										+ data['amountPercent'] + '"  ><span >'
										+ data['amountPercent'] 
										+ '</span></div>');

						$("#refused_amount").html(data['amount'] + '元');
						
						
						$("#refused_personPercent").html(
								'<div class="chart" data-percent="'
										+ data['personPercent'] + '"  ><span >'
										+ data['personPercent'] 
										+ '</span></div>');

						$("#refused_person").html(data['persons'] + '人');

						chartList.easyPieChart();
					
					} else {
						console.log(result['errorMsg']);
					}
				})
	},
	statementChart : function(regionId,darte){
		$.get(chartList.URL.statement(regionId,darte), {},
				function(result) {

					if (result && result['success']) {
						var data = result['result'];
						$("#statement_orderPercent").html(
								'<div class="chart" data-percent="'
										+ data['orderPercent'] + '"  ><span >'
										+ data['orderPercent'] 
										+ '</span></div>');
						$("#statement_orders").html(data['orders'] + '单');

						$("#statement_amountPercent").html(
								'<div class="chart" data-percent="'
										+ data['amountPercent'] + '"  ><span >'
										+ data['amountPercent'] 
										+ '</span></div>');

						$("#statement_amount").html(data['amount'] + '元');
						
						
						$("#statement_personPercent").html(
								'<div class="chart" data-percent="'
										+ data['personPercent'] + '"  ><span >'
										+ data['personPercent'] 
										+ '</span></div>');

						$("#statement_person").html(data['persons'] + '人');

						chartList.easyPieChart();
					
					} else {
						console.log(result['errorMsg']);
					}
				})
			},
			StatementAndPaidChart : function(regionId,darte){
				$.get(chartList.URL.PaidStatement(regionId,darte), {},
						function(result) {

							if (result && result['success']) {
								var data = result['result'];
								$("#paidStatement_orderPercent").html(
										'<div class="chart" data-percent="'
												+ data['orderPercent'] + '"  ><span >'
												+ data['orderPercent'] 
												+ '</span></div>');
								$("#paidStatement_orders").html(data['orders'] + '单');

								$("#paidStatement_amountPercent").html(
										'<div class="chart" data-percent="'
												+ data['amountPercent'] + '"  ><span >'
												+ data['amountPercent'] 
												+ '</span></div>');

								$("#paidStatement_amount").html(data['amount'] + '元');
								
								
								$("#paidStatement_personPercent").html(
										'<div class="chart" data-percent="'
												+ data['personPercent'] + '"  ><span >'
												+ data['personPercent'] 
												+ '</span></div>');

								$("#paidStatement_person").html(data['persons'] + '人');

								chartList.easyPieChart();
							
							} else {
								console.log(result['errorMsg']);
							}
						})
					},
	
	easyPieChart : function(){
		$('.chart').easyPieChart({
			animate : 2000
		});
	},
	
	

	chart : {
		init : function(regionId,darte) {

			chartList.outboundChart(regionId,darte);
			chartList.cashChart(regionId,darte);
			chartList.unReportChart(regionId,darte);
			chartList.reportChart(regionId,darte);
			chartList.refusedChart(regionId,darte);
			chartList.statementChartChart(regionId,darte);
		},
		queryChartByParam : function(){
			var regionId = $("#regionId").val();
			var darte = $("#date").val();
			chartList.chart.init(regionId,darte);
		},
	}
}

