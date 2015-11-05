<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!--A Design by W3layouts
Author: W3layout
Author URL: http://w3layouts.com
License: Creative Commons Attribution 3.0 Unported
License URL: http://creativecommons.org/licenses/by/3.0/
-->
<!DOCTYPE HTML>
<html>
<head>
<title>Tourplan an Travel Category Flat Bootstarp responsive Website Template| Portfolio :: w3layouts</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="Tourplan Responsive web template, Bootstrap Web Templates, Flat Web Templates, Andriod Compatible web template, 
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyErricsson, Motorola web design" />
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<link href="<c:url value='/css/bootstrap.css' />" rel='stylesheet' type='text/css' />
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
<body>
<div class="about">
	<div class="container">
		<div class="logo">
			<h1><a href="index.html">Tourplan</a></h1>
		</div>
		<nav class="navbar navbar-default menu" role="navigation"><h3 class="nav_right"><a href="index.html">Tourplan</a></h3>
		  <div class="container-fluid">
		    <!-- Brand and toggle get grouped for better mobile display -->
		    <div class="navbar-header">
		      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
		        <span class="sr-only">Toggle navigation</span>
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>
		      </button>
		    </div>
			<!-- Collect the nav links, forms, and other content for toggling -->
		    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
		      <ul class="nav navbar-nav menu1">
		      	<li><a href="index.html">Home</a></li>
		        <li><a href="about.html">About</a></li>
		        <li><a href="destinations.html">Destinations</a></li>
		        <li><a href="portfolio.html">Portfolio <i class="menu-border"></i></a></li>
		        <li><a href="typography.html">Blog</a></li>
		        <li><a href="contact.html">Contact</a></li>
		       </ul>
		       <form class="navbar-form navbar-left search1" role="search">
		        <div class="search2">
				  <form>
					 <input type="text" value="">
					 <input type="submit" value="">
				  </form>
				</div>
		      </form>
		    </div><!-- /.navbar-collapse -->
		  </div><!-- /.container-fluid -->
		</nav>
	    <div class="clearfix"></div>
	 </div>
</div>
<div class="postlist">
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
<div class="footer">
	<div class="container">
       <div class="col-md-3 footer-grid">
          <h3>Extras</h3>
		  <p>Ut rutrum neque a mollis laoreet diam enim feuiat dui nec ulacoper quam felis id diam. Nunc ut tortor ligula eu petiu risus. Pelleesque conquat dignissim lacus quis altrcies.</p>
	   </div>	
	   <div class="col-md-3 footer-grid">
          <h3>Recent Posts</h3>
		  <ul class="list_1">
			<li><a href="#">Vestibulum felis</a></li>
			<li><a href="#">Mauris at tellus</a></li>
			<li><a href="#">Donec ut lectus</a></li>
			<li><a href="#">vitae interdum</a></li>
		  </ul>
	   </div>	
	   <div class="col-md-3 footer-grid">
          <h3>Useful Info</h3>
		  <ul class="list_1">
			<li><a href="#">Hendrerit quam</a></li>
			<li><a href="#">Amet consectetur </a></li>
			<li><a href="#">Iquam hendrerit</a></li>
			<li><a href="#">Donec ut lectus </a></li>
		  </ul>
	   </div>	
	   <div class="col-md-3 footer-grid">
          <h3>Contact Us</h3>
		  <div class="footer-grid-address">
			<p>Tel.800-255-9999</p>
			<p>Fax: 1234 568</p>
			<p>Email:<a href="malito:mail@demolink.org">mail(at)tourplan.com</a></p>
		  </div>
	   </div>
	   <div class="clearfix"> </div>				
	</div>
	<div class="copy">
	   <p>Copyright © 2015 Tourplan . All Rights Reserved . Design by <a href="http://w3layouts.com/" target="_blank">W3layouts</a> </p>
    </div>
</div>		
</body>
</html>