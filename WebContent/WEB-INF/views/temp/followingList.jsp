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
</head><!--/head-->

<body class="homepage">

    <header id="header">
        <c:import url="/include/top.jsp" />
    </header><!--/header-->

   <section>
   <div class="panel panel-default following">
  <div class="panel-heading">
    <h3 class="panel-title">팔로잉 List</h3>
  </div>
  <div class="panel-body">
  	<c:if test="${not empty followingList}">
  	<c:forEach var="following" items="${followingList}">
	    <div class="col-xs-12">
	    	<span>사용자: ${following.email}</span>
	    	<span>블로그 명: ${following.blogName}</span>
	    	<span>팔로워: ${following.followCount}</span>
	    	<a href="/blog?action=unfollowing&blogName=${following.blogName}">언팔로우</a>
	    </div>
  	</c:forEach>
  	</c:if>
  	<c:if test="${empty followingList}"><p class="h3">팔로우한 블로그가 없습니다.</p></c:if>
  </div>
</div>
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