<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Micro Blogging Service</title>
</head>
<body>
<h2>블로그 만들기</h2>
<h4>자신의 블로그를 만들 수 있습니다.
		공개,비공개 여부를 설정 할 수 있으며, 여러개의 블로그를 관리 할 수 있습니다.
</h4>
<hr>
	<label>블로그 공개 여부  </label>
	<label><input type="checkbox" value="1" checked> 공개</label>
	<label><input type="checkbox" value="2"> 비공개</label>	
	<br>
	<label> 블로그 명 : </label>
	<input type="text" placeholder="블로그 명을 적어주세요.">
	<label>blog</label><br>
	<input type="button" value="블로그 만들기" onclick="#">
	<input type="button" value="이전" onclick="">
</body>
</html>