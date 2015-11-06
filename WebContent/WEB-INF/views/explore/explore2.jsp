<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="microblog.kitsch.business.domain.Posting" %>
<%@ page import="microblog.kitsch.business.domain.PostingContent" %>

<!--A Design by W3layouts
Author: W3layout
Author URL: http://w3layouts.com
License: Creative Commons Attribution 3.0 Unported
License URL: http://creativecommons.org/licenses/by/3.0/
-->
<!DOCTYPE HTML>
<html>
<head>
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
    
<title>Tourplan an Travel Category Flat Bootstarp responsive Website Template| Portfolio :: w3layouts</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="Tourplan Responsive web template, Bootstrap Web Templates, Flat Web Templates, Andriod Compatible web template, 
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyErricsson, Motorola web design" />
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<%--<link href="<c:url value='/css/bootstrap.css' />" rel='stylesheet' type='text/css' />--%> 
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<!-- Custom Theme files -->
<link href="<c:url value='/css/style.css' />" rel='stylesheet' type='text/css' />
<!-- Custom Theme files -->
<!--webfont-->
<link href='//fonts.googleapis.com/css?family=Pacifico' rel='stylesheet' type='text/css'>
<link href='//fonts.googleapis.com/css?family=Open+Sans:100,200,300,400,500,600,700,800,900' rel='stylesheet' type='text/css'>
<script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
<!----requred-js-files---->
<script src="<c:url value='/js/bootstrap.min.js' />"></script>
<script src="<c:url value='/js/isotope.pkgd.min.js' />"></script>
<!----//requred-js-files---->
<!------ light-box-script ----->
<script src="<c:url value='/js/jquery.chocolat.js' />"></script>
<link rel="stylesheet" href="<c:url value='/css/chocolat.css' />" type="text/css" media="screen" charset="utf-8" />
<script type="text/javascript" charset="utf-8">
	$(function() {
		$('.folio a').Chocolat({linkImages:false});
	});
	
	$('.grid').isotope({
		itemSelector: '.grid-item',
		layoutMode: 'fitRows',
		masonry: {
			columnWidth: '.col-sm-4'
		}
	});
</script>
<!------ light-box-script ----->


</head>
<body class="homepage">

	<header id="header" class="header">
		<jsp:include page="/include/top.jsp" />
		<%-- <c:import url="/include/top.jsp" /> --%>
	</header>

<div class="postlist posting_form">
   	   	<div class="container">
   	   	<div class="grid js-isotope" data-isotope-options='{ "itemSelector": ".grid-item", "masonry": { "columnWidth": ".col-sm-4"} }'>
	   	   	    <c:if test="${not empty postings}">
	   	   	    <c:forEach var="posting" items="${postings}">
	   	   	    <div class="grid-item col-sm-4 post">
	   	   	    	<h3><a class="postTitle" href="#">${posting.title}</a></h3>
	   	   	    	<hr>
					<a class="postMedia" href="images/a1.jpg" title="name" rel="title2">
				    <div class="view view-first">
				    <c:forEach var="file" items="${posting.contents.filePaths}">
		            	<img src="${posting.contents.filePaths}" class="img-responsive" alt=""/>
				    </c:forEach>
		            </div>
					</a>
					<h3 class="postWriter"><a href="#">${posting.writer}</a></h3>
					<p class="service_desc postText">${posting.contents.textContent}</p>
		            <a href="#" class="btn_4">more</a>						
			    </div>
			    <%--
			    
	   	   		<div class="grid-item col-sm-4 post">
					<a href="images/a2.jpg" title="name" rel="title2">
				    <div class="view view-first">
		              <img src="images/a2.jpg" class="img-responsive" alt=""/>
		            </div>
					</a>
					<h3><a href="#">tincidunt</a></h3>
					<p class="service_desc">Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna. yyyy yy y yyy yyy yyy yyyyyy yyyy yyyyy yyyyyy yyyyyyy yyyyyy yyyyyyy yyyyy yy yyyyyyy yyyyyyy</p>
		            <a href="#" class="btn_4">more</a>						
			    </div>
	   	   		<div class="grid-item col-sm-4 post">
					<a href="images/a3.jpg" title="name" rel="title2">
				    <div class="view view-first">
		              <img src="images/a3.jpg" class="img-responsive" alt=""/>
		            </div>
					</a>
					<h3><a href="#">nonummy nibh</a></h3>
					<p class="service_desc">Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna.</p>
		            <a href="#" class="btn_4" title="Lewis Tompson">more</a>						
			    </div>
	   	   		<div class="grid-item col-sm-4 post">
					<a href="images/a4.jpg" title="name" rel="title2">
				    <div class="view view-first">
		              <img src="images/a4.jpg" class="img-responsive" alt=""/>
		            </div>
					</a>
					<h3><a href="#">sit amet</a></h3>
					<p class="service_desc">Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna.</p>
		            <a href="#" class="btn_4">more</a>						
			    </div>
			    
			    --%>
			    </c:forEach>
			    </c:if>
			    <c:if test="${empty postings}">
				    <div class="col-md-4 col-md-offset-4">포스트가 없습니다.</div>
			    </c:if>
	   	   		<div class="clearfix"> </div>
	   	   		</div>
	   	   </div>
</div>
	<footer id="footer" class="footer midnight-blue">
        <jsp:include page="/include/footer.jsp"></jsp:include>
        <%--
        <c:import url="/include/footer.jsp" />
        --%>
    </footer>	
</body>
</html>