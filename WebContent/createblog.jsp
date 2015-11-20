<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Home | Corlate</title>
	
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
    <script src="<c:url value='/js/kitsch.js' />" ></script>
</head><!--/head-->

<body class="homepage cb_align">

    <header id="header">
        <c:import url="/include/top.jsp" />
    </header><!--/header-->

   <section class="create_blog">
   <h2 class="cb_align">블로그 만들기</h2>
<h4>자신의 블로그를 만들 수 있습니다.
		공개,비공개 여부를 설정 할 수 있으며, 여러개의 블로그를 관리 할 수 있습니다.
</h4>
<hr>
	<form action="blog?action=create" method="POST">
		<label> 블로그 명 : 
		<input type="text" class="form-control" name="newBlogName" placeholder="블로그 명을 적어주세요.">
		</label><br>
		<input type="submit" value="블로그 만들기" >
		<input type="button" value="이전" onclick="">
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