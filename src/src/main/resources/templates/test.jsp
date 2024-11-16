<%@ page pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Index</title>
	</head>
	<body>
		<h1>Max</h1>
		<form action="/tinh" method="post">
		1<input type="number" name="n">
		2<input type="number" name="n">
		3<input type="number" name="n">
		4<input type="number" name="n">
		<input type="submit" value="Submit">
		</form>
		<h3>Value Max: ${max}</h3>
	</body>
</html>