<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Home | Kitsch</title>
	
	<!-- core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/font-awesome.min.css" rel="stylesheet">
    <link href="css/animate.min.css" rel="stylesheet">
    <link href="css/prettyPhoto.css" rel="stylesheet">
    <link href="css/main.css" rel="stylesheet">
    <link href="css/responsive.css" rel="stylesheet">
	<link href="css/kitsch.css" rel="stylesheet">
         
    <link rel="shortcut icon" href="images/ico/favicon.ico">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="images/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="images/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="images/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="images/ico/apple-touch-icon-57-precomposed.png">
</head><!--/head-->

<body class="homepage">

    <header id="header">
        <c:import url="/include/top.jsp" />
    </header><!--/header-->

   <section class="main_contents">
   <%--
   <div class="container-fluid" >
   	<img src="images/main_img.png">
   </div>
   --%>
   	<img src="images/logo2.png"><br>
   			
	<form id="slick-login" action="member?action=register" method="POST">
			<c:if test="${not empty errorMsgs}">
				<div class="error">
					<c:forEach items="${errorMsgs}" var="msgs">
						<li>${msgs}</li>
					</c:forEach>
				</div>
			</c:if>
			
			<table class="table_padding">
				<tr>
					<td class="table_label3"><label>E-mail : </label></td>
					<td>
					<input type="email" placeholder="E-mail" name="email">
					</td>
				</tr>
				<tr>
					<td class="table_label3"><label>닉네임 : </label></td>
					<td><input type="text" placeholder="닉네임" name="name"></td>
				</tr>
				<tr>
					<td class="table_label3"><label>비밀번호 : </label></td>
					<td><input type="password" placeholder="비밀번호" name="password"></td>
				</tr>
				<tr>
					<td class="table_label3"><label>비밀번호 확인 : </label></td>
					<td><input type="password" placeholder="비밀번호 확인" name="confirmPassword"></td>
				</tr>
				<tr>
					<td colspan="2" class="table_label2"><input type="submit" class="btn btn-default" value="회원가입"></td>
				</tr>
			</table>
		</form>
</section>

    <footer id="footer" class="midnight-blue">
        <c:import url="/include/footer.jsp" />
    </footer><!--/#footer-->

    <script src="js/jquery.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery.prettyPhoto.js"></script>
    <script src="js/jquery.isotope.min.js"></script>
    <script src="js/main.js"></script>
    <script src="js/wow.min.js"></script>
</body>
</html>