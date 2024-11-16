<%@ page pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Index</title>
	</head>
	<body>
		<h1>Địa chỉ sang tọa độ</h1>
		<form action="/search" method="post">
		Address: <input type="text" name="address">
		<input type="submit" value="Submit">
		</form>
		
		<h1>Tọa độ sang địa chỉ</h1>
		<form action="/location" method="post">
		Address: <input type="text" name="location">
		<input type="submit" value="Submit">
		</form>
		
		<h1>Khoảng cách theo tọa độ</h1>
		<form action="/khoangcach" method="post">
		Address: <input type="text" name="location">
		<input type="submit" value="Submit">
		</form>
		
		<h1>Value: ${value}</h1>
	</body>
</html>