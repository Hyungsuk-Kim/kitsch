<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Micro Blogging Service</title>
</head>
<body>
<h2>블로그 수정</h2>
<hr>
<label>블로그 배경 사진 변경</label>
<input type="file">
<br>
<div id="preview">
	<img id="imagePreview" src="#" width="200" height="200"/>
    </div>
    
	<label>블로그 공개 여부  </label>
	<label><input type="checkbox" value="1" checked> 공개</label>
	<label><input type="checkbox" value="2"> 비공개</label>	
	<br>
	
	<label> 블로그 명 : </label>
	<input type="text" placeholder="새로운 블로그 명">
	<label> blog</label>
	
	<a href="#"><span class="glyphicon glyphicon-ok" ></span></a>
	<a href="#"><span class="glyphicon glyphicon-arrow-left" ></span></a>
</body>
</html>