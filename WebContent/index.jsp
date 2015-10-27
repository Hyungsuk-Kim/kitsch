<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>micro Blogging Service</title>
</head>
<body>
	<header>
		<nav>
			<ul>
				<li><a href="#home">HOME</a></li>
				<li><a href="#">둘러보기</a></li>
				<li><a href="#">회원가입</a></li>
			</ul>
		</nav>
	</header>
	<section id="home">
		<img src="images/logo.png" alt="kitsch logo" /><br />
		<form id="slick-login" action="member?action=register" method="POST">

			<c:if test="${not empty errorMsgs}">
				<div class="error">
					<c:forEach items="${errorMsgs}" var="msgs">
						<li>${msgs}</li>
					</c:forEach>
				</div>
			</c:if>

			<label for="email">email</label> 
			<input type="email" name="email" id="email" onkeyup="idcheck()"
				class="placeholder" placeholder="이메일"> <span id="idcheckLayer"></span>
				<label for="username">name</label>
			<input type="text" name="name" id="name" class="placeholder" onkeyup="namechecked()" placeholder="닉네임">
			<span id="namecheckLayer"></span>
			
			<label for="password">password</label> 
			<input type="password" id="password" onkeyup="passwordcheck()" name="password" class="placeholder" placeholder="비밀번호">
			<span id="passwordcheckLayer"></span>
				
			<input type="password" id="chkpassword" onkeyup="chkpasswordcheck()" name="chkpassword" class="placeholder" placeholder="비밀번호 확인">
			<span id="chkpasswordcheckLayer"></span>
			<input type="submit" id="join" value="회원가입">
			<input type="hidden" name="${errorMsgs}" value="${errorMsgs}" />

		</form>
		<br /> <br /> <br /> <a href="explore?action=trend">Kitsch 둘러보기</a>
	</section>
	<%--
	<section>
		<img src="images/logo.png">
		<div>
			<label for="email">이메일</label>
			<input type="email" name="email" class="placeholder" placeholder="이메일">
			<label for="password">비밀번호</label>
			<input type="password" name="password" class="placeholder" placeholder="비밀번호">
		</div>
		<div>
			<input type="button" value="로그인" onclick="#">
			<input type="button" value="아이디/비밀번호 찾기" onclick="#">
		</div>
	</section>
	--%>
	<section>
		<h2>About Kitsch</h2>
			<p>
				관심사가 같은 다른 사용자와 정보를 공유해보세요.<br>
				기존의 SNS에서 기대하기 힘든 감성적인,<br> 
				개인에 특화된 웹 기반의 마이크로 블로깅 서비스를 누려보세요.<br>
			</p>
	</section>

</body>
</html>