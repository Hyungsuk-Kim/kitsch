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
    
    <script src="<c:url value='/js/kitsch.js' />"></script>
</head><!--/head-->

<body class="homepage index_bg">

    <header id="header">
        <jsp:include page="/include/top.jsp"></jsp:include>
    </header><!--/header-->

   <section class="main_contents">
   <%--
   <div class="container-fluid" >
   	<img src="images/main_img.png">
   </div>
   --%>
   <div class="index_contents">
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
		</div>
</section>

<section class="index_con2">
	<div>
		<img src="images/index_con.png">
	</div>
</section>

<!-- 메인 3번째  -->
<%-- 
<section class="index_con3">
	<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
  <!-- Indicators -->
  <ol class="carousel-indicators">
    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
    <li data-target="#carousel-example-generic" data-slide-to="2"></li>
  </ol>

  <!-- Wrapper for slides -->
  <div class="carousel-inner" role="listbox">
    <div class="item active">
      <img src="images/s-1.jpg" alt="...">
      <div class="carousel-caption">
        
      </div>
    </div>
    <div class="item">
      <img src="images/s-2.jpg" alt="...">
      <div class="carousel-caption">
       
      </div>
    </div>
     <div class="item">
      <img src="images/s-3.jpg" alt="...">
      <div class="carousel-caption">
       
      </div>
    </div>
    
     <div class="item">
      <img src="images/s-4.jpg" alt="...">
      <div class="carousel-caption">
        
      </div>
    </div>    
  </div>

  <!-- Controls -->
  <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
    <span class="sr-only">Previous</span>
  </a>
  <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
    <span class="sr-only">Next</span>
  </a>
</div>
	
</section>
 --%>
    <footer id="footer" class="midnight-blue all_footer">
        <jsp:include page="/include/footer.jsp"></jsp:include>
    </footer><!--/#footer-->

    <script src="js/jquery.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery.prettyPhoto.js"></script>
    <script src="js/jquery.isotope.min.js"></script>
    <script src="js/main.js"></script>
    <script src="js/wow.min.js"></script>
</body>
</html>