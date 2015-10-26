<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset=UTF-8>
<title>micro Blogging Service</title>
</head>
<body>
	<div>
		<form>
		<!-- 블로그 이름은 현재 자신의 블로그 이름이 출력 되야 한다. -->
		<!-- 부트스트립 적용하면 드롭다운 뜰거임 -->
			<ul class="nav navbar-nav navbar-right">
                      <li class="dropdown">
                      선택된 블로그 이름
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="glyphicon glyphicon-user"></i></a>
                        <ul class="dropdown-menu">
                          <li><a href="">More</a></li>
                          <li><a href="">More</a></li>
                          <li><a href="">More</a></li>
                          <li><a href="">More</a></li>
                          <li><a href="">More</a></li>
                        </ul>
                      </li>
                    </ul>
			
			<input type="text" placeholder="제목"><br>
			<textarea rows="10" cols="50" placeholder="내용을 입력해 주세요"></textarea><br>
			<input type="button" value="포스팅 쓰기" onclick="#">
			<input type="button" value="닫기" onclick="close">
		</form>
	</div>
	
	
</body>
</html>