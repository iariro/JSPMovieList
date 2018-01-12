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
		<h3>総視聴件数</h3>
		<div id='titlecountgraph' style='width: 1200px; height: 600px;'></div>
		<script language='JavaScript'>
		$(document).ready(function()
		{
			$('#titlecountgraph').highcharts(
				{
					chart:{type: 'area'},
					title : {text: '総視聴件数'},
					xAxis: {type: 'datetime'},
					yAxis : { title: {text: '件数'}},
					series : [ <s:property value="generateTitleCountPoints" /> ]
				});
		});
		</script>
	</body>
</html>
