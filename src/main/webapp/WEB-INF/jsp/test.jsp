<html>
<head><title>First JSP</title></head>
<body>
  <div>
		<form method="POST" enctype="multipart/form-data" action="/uploadFile">
			<table>
				<tr><td>File to upload:</td><td><input type="file" name="file" /></td></tr>
				<tr><td></td><td><input type="submit" value="Upload to Database" /></td></tr>
			</table>
		</form>
	</div>

	<div>
		<ul>
			<li th:each="file : ${files}">
				<a th:href="${file}" th:text="${file}" />
			</li>
		</ul>
	</div>
</body>
</html>