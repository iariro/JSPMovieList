<%@ page contentType="text/html; charset=utf-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<meta charset='UTF-8' />
		<script src='http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js'></script>
		<script src='http://code.highcharts.com/highcharts.js'></script>
		<title>映画視聴情報</title>
	</head>

	<body>
		<h3>映画視聴情報</h3>
		<div id='watchMonthHistoHisto' style='width: 1200px; height: 500px;'></div>
		<div id='releaseYearHisto' style='width: 1200px; height: 500px;'></div>
		<table><tr>
			<td><div id='acquisitionTypeHisto' style='width: 400px; height: 300px;'></div></td>
			<td><div id='categoryDataHisto' style='width: 400px; height: 300px;'></div></td>
			<td><div id='chromeTypeHisto' style='width: 400px; height: 300px;'></div></td>
		<tr></table>
		<script language='JavaScript'>
		$(document).ready(function()
		{
			$('#watchMonthHistoHisto').highcharts(
					{
						chart : {type: 'column'},
						title : {text: '月ごと視聴数'},
						xAxis : {categories: [ '１月','２月','３月','４月','５月','６月','７月','８月','９月','１０月','１１月','１２月' ],title: {text: null}},
						yAxis : { min: 0, title: {text: '件数', align: 'high'}, labels: {overflow: 'justify'}},
						series : [ <s:property value="watchMonthHistoHistoPoints" /> ],
						plotOptions : {bar: {dataLabels: {enabled: true}},series: {stacking: 'normal'}},
						credits : {enabled: false}
					});
			$('#acquisitionTypeHisto').highcharts(
				{
					chart : {type: 'column'},
					title : {text: '視聴方法'},
					xAxis : {categories: [ <s:property value="acquisitionTypeHistoXLabel" /> ],title: {text: null}},
					yAxis : { min: 0, title: {text: '件数', align: 'high'}, labels: {overflow: 'justify'}},
					series : [ <s:property value="acquisitionTypeHistoPoints" /> ],
					plotOptions : {bar: {dataLabels: {enabled: true}},series: {stacking: 'normal'}},
					credits : {enabled: false}
				});
			$('#categoryDataHisto').highcharts(
				{
					chart : {type: 'column'},
					title : {text: '洋画／邦画区分'},
					xAxis : {categories: [ <s:property value="categoryDataHistoXLabel" /> ],title: {text: null}},
					yAxis : { min: 0, title: {text: '件数', align: 'high'}, labels: {overflow: 'justify'}},
					series : [ <s:property value="categoryDataHistoPoints" /> ],
					plotOptions : {bar: {dataLabels: {enabled: true}},series: {stacking: 'normal'}},
					credits : {enabled: false}
				});
			$('#chromeTypeHisto').highcharts(
				{
					chart : {type: 'column'},
					title : {text: 'カラー／モノクロ区分'},
					xAxis : {categories: [ <s:property value="chromeTypeHistoXLabel" /> ],title: {text: null}},
					yAxis : { min: 0, title: {text: '件数', align: 'high'}, labels: {overflow: 'justify'}},
					series : [ <s:property value="chromeTypeHistoPoints" /> ],
					plotOptions : {bar: {dataLabels: {enabled: true}},series: {stacking: 'normal'}},
					credits : {enabled: false}
				});
			$('#releaseYearHisto').highcharts(
				{
					chart : {type: 'column'},
					title : {text: '作品年代'},
					xAxis : {categories: [ <s:property value="releaseYearHistoXLabel" /> ],title: {text: null}},
					yAxis : { min: 0, title: {text: '件数', align: 'high'}, labels: {overflow: 'justify'}},
					series : [ <s:property value="releaseYearHistoPoints" /> ],
					plotOptions : {bar: {dataLabels: {enabled: true}},series: {stacking: 'normal'}},
					credits : {enabled: false}
				});
		});
		</script>
	</body>
</html>
