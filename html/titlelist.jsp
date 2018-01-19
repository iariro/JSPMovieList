<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>映画視聴情報</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>
		<h1>映画視聴情報 - タイトル一覧</h1>

		<div class=hatena-body>
		<div class=main>
		<div class=day>

			<br>

			<s:iterator value="recordsCollection">
				<h2><s:property value='year' />年</h2>
				<table>
					<tr><th>洋／邦</th><th>カラー／白黒</th><th>視聴方法</th><th>鑑賞日</th><th>タイトル</th></tr>
					<s:iterator>
						<tr>
							<td><s:property value='category' /></td>
							<td><s:property value='chromeType' /></td>
							<td><s:property value='acquisitionType' /></td>
							<td><s:property value='watchDate' /></td>
							<td><s:property value='title' /></td>
						</tr>
					</s:iterator>
				</table>
			</s:iterator>

		</div>
		</div>
		</div>

	</body>
</html>
