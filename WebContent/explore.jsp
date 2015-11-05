<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="microblog.kitsch.test.DomainObjectsForTest" %>

<!DOCTYPE HTML>
<html>
	<head>
		<title>Explore | Kitsch</title>
		<link href="css/style2.css" rel='stylesheet' type='text/css' />
		<meta name="viewport" content="width=device-width, initial-scale=1">
    
		<link rel="shortcut icon" type="image/x-icon" href="images/fav-icon.png" />
		
		<script type="application/x-javascript">
		addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } 
		</script>
		
		<!----webfonts---->
		<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700,800' rel='stylesheet' type='text/css'>
		<!----//webfonts---->
		<!-- Global CSS for the page and tiles -->
  		<link rel="stylesheet" href="css/main2.css">

  		<link rel="stylesheet" href="css/kitsch.css">

  		<link rel="stylesheet" href="css/bootstrap.min.css">
  		
  		<link href="css/font-awesome.min.css" rel="stylesheet">
  		    
    	<link href="css/animate.min.css" rel="stylesheet">
    	
    	<link href="css/prettyPhoto.css" rel="stylesheet">
    	
    	<link href="css/main.css" rel="stylesheet">
    	
    	<link href="css/responsive.css" rel="stylesheet">
    	
		<link href="css/kitsch.css" rel="stylesheet">

		<script src="js/jquery.min.js"></script>
        
         <script type="text/javascript">
			var $ = jQuery.noConflict();
				$(function() {
					$('#activator').click(function(){
						$('#box').animate({'top':'0px'},500);
					});
					$('#boxclose').click(function(){
					$('#box').animate({'top':'-700px'},500);
					});
				});
				$(document).ready(function(){
				//Hide (Collapse) the toggle containers on load
				$(".toggle_container").hide(); 
				//Switch the "Open" and "Close" state per click then slide up/down (depending on open/close state)
				$(".trigger").click(function(){
					$(this).toggleClass("active").next().slideToggle("slow");
						return false; //Prevent the browser jump to the link anchor
				});
									
			});
		</script>
		
	</head>
	<%
		session.setAttribute("logonMember", DomainObjectsForTest.logonMember);
		session.setAttribute("memberBlogs", DomainObjectsForTest.memberBlogs);
		session.setAttribute("defaultBlog", DomainObjectsForTest.defaultBlog);
		request.setAttribute("postings", DomainObjectsForTest.postings);
		request.setAttribute("blogs", DomainObjectsForTest.blogs);
	%>
	<body>
		<!---start-wrap---->
		<%--
			<!---start-header---->
			<div class="header">
				<div class="wrap">
				<div class="logo">
					<a href="index.html"><img src="images/logo.png" title="pinbal" /></a>
				</div>
				<div class="nav-icon">
					 <a href="#" class="right_bt" id="activator"><span> </span> </a>
				</div>
				 <div class="box" id="box">
					 <div class="box_content">        					                         
						<div class="box_content_center">
						 	<div class="form_content">
								<div class="menu_box_list">
									<ul>
										<li><a href="#"><span>home</span></a></li>
										<li><a href="#"><span>About</span></a></li>
										<li><a href="#"><span>Works</span></a></li>
										<li><a href="#"><span>Clients</span></a></li>
										<li><a href="#"><span>Blog</span></a></li>
										<li><a href="contact.html"><span>Contact</span></a></li>
										<div class="clear"> </div>
									</ul>
								</div>
								<a class="boxclose" id="boxclose"> <span> </span></a>
							</div>                                  
						</div> 	
					</div> 
				</div>       	  
				<div class="top-searchbar">
					<form>
						<input type="text" /><input type="submit" value="" />
					</form>
				</div>
				<div class="userinfo">
					<div class="user">
						<ul>
							<li><a href="#"><img src="images/user-pic.png" title="user-name" /><span>Ipsum</span></a></li>
						</ul>
					</div>
				</div>
				<div class="clear"> </div>
			</div>
		</div>
		<!---//End-header---->
		--%>
		<!---start-content---->
		
		 
		
		
	<div class="container">
		<div class="row">
			<div class="content">
				<div class="wrap">
					 <div id="main" role="main">
					      <ul id="tiles">
					        <!-- These are our grid blocks -->
					        
					        <c:forEach var="post" items="${requestScope.postings}">
					        
					        	<!-- Text Post -->
						        <c:if test="${post.contentType eq 310}">
							        <li>
							        	<div class="post-info">
							        		<div class="post-basic-info">
							        		<p class="h5">작성자: <a href="#">${post.writer}</a>
							        		<i class="glyphicon glyphicon-retweet follow_style">팔로우</i>
							        		</p>
							        		</div>
							        		<div class="post-basic-info">
							        			<c:if test="${not empty post.title}">
								        			<h3><a href="#">${post.title}</a></h3>
							        			</c:if>
								        		<p class="text_content">${post.contents.textContent}</p>
							        		</div>
							        		<div class="post-info-rate-share">
							        		<!-- 좋아요 아이콘 -->
							        			<div class="rateit">
							        			<a href="#" class="like_btn">
							        				<i class="glyphicon glyphicon-heart-empty" ></i>
							        			</a>
							        			
							        			<c:if test="">
							        			<a href="#" class="like_btn">
							        				<i class="glyphicon glyphicon-heart" ></i>
							        			</a>
							        			</c:if>
							        			
							        			
							        			
							        				
							        			</div>
							        			<div class="post-share">							  
							        					좋아요${post.likes}개							        				
							        			</div>
							        			<div class="clear"> </div>
							        		</div>
							        	</div>
							        </li>
						        </c:if>
						        
						        <!-- Single Image Post -->
						        <c:if test="${post.contentType eq 120 || post.contentType eq 121}">
							        <li>
							        	<div class="post-info">
							        		<div class="post-basic-info">
							        		<p class="h5">작성자: <a href="#">${post.writer}</a></p>
							        		</div>
							        		<c:forEach var="image" items="${post.contents.filePaths}">
							        			<img src="${image}" width="282">
							        		</c:forEach>
							        		<div class="post-basic-info">
							        			<c:if test="${not empty post.title}">
								        			<h3><a href="#">${post.title}</a></h3>
							        			</c:if>
							        		</div>
							        		<div class="post-info-rate-share">
							        			<div class="rateit">
							        				<span> </span>
							        			</div>
							        			<div class="post-share">
							        				<span> </span>
							        			</div>
							        			<div class="clear"> </div>
							        		</div>
							        	</div>
							        </li>
						        </c:if>
						        
						        <!-- Mixed Image Post -->
						        <c:if test="${post.contentType eq 220 || post.contentType eq 221}">
							        <li>
							        	<div class="post-info">
							        		<div class="post-basic-info">
							        		<p class="h5">작성자: <a href="#">${post.writer}</a></p>
							        		</div>
							        		<c:forEach var="image" items="${post.contents.filePaths}">
							        			<img src="${image}" width="282">
							        		</c:forEach>
							        		<div class="post-basic-info">
							        			<c:if test="${not empty post.title}">
								        			<h3><a href="#">${post.title}</a></h3>
							        			</c:if>
								        		<p class="text_content">${post.contents.textContent}</p>
							        		</div>
							        		<div class="post-info-rate-share">
							        			<div class="rateit">
							        				<span> </span>
							        			</div>
							        			<div class="post-share">
							        				<span> </span>
							        			</div>
							        			<div class="clear"> </div>
							        		</div>
							        	</div>
							        </li>
						        </c:if>
						        
						        <!-- Single Audio Post -->
						        <c:if test="${post.contentType eq 130 || post.contentType eq 131}">
							        <li>
							        	<div class="post-info">
							        		<div class="post-basic-info">
							        		<p class="h5">작성자: <a href="#">${post.writer}</a></p>
							        		</div>
							        		<c:forEach var="audio" items="${post.contents.filePaths}">
							        			<audio class="col-xs-10 col-xs-offset-1" controls>
													<source src="${audio}" type="audio/ogg">
													<source src="${audio}" type="audio/mpeg">
													<source src="${audio}" type="audio/mp3">
													<source src="${audio}" type="audio/mp4">
												</audio>
							        		</c:forEach>
							        		<div class="post-basic-info">
							        			<c:if test="${not empty post.title}">
								        			<h3><a href="#">${post.title}</a></h3>
							        			</c:if>
							        		</div>
							        		<div class="post-info-rate-share">
							        			<div class="rateit">
							        				<span> </span>
							        			</div>
							        			<div class="post-share">
							        				<span> </span>
							        			</div>
							        			<div class="clear"> </div>
							        		</div>
							        	</div>
							        </li>
						        </c:if>
						        
						        <!-- Mixed Audio Post -->
						        <c:if test="${post.contentType eq 230 || post.contentType eq 231}">
							        <li>
							        	<div class="post-info">
							        		<div class="post-basic-info">
							        		<p class="h5">작성자: <a href="#">${post.writer}</a></p>
							        		</div>
							        		<c:forEach var="audio" items="${post.contents.filePaths}">
							        			<audio class="col-xs-10 col-xs-offset-1" controls>
													<source src="${audio}" type="audio/ogg">
													<source src="${audio}" type="audio/mpeg">
													<source src="${audio}" type="audio/mp3">
													<source src="${audio}" type="audio/mp4">
												</audio>
							        		</c:forEach>
							        		<div class="post-basic-info">
							        			<c:if test="${not empty post.title}">
								        			<h3><a href="#">${post.title}</a></h3>
							        			</c:if>
								        		<p class="text_content">${post.contents.textContent}</p>
							        		</div>
							        		<div class="post-info-rate-share">
							        			<div class="rateit">
							        				<span> </span>
							        			</div>
							        			<div class="post-share">
							        				<span> </span>
							        			</div>
							        			<div class="clear"> </div>
							        		</div>
							        	</div>
							        </li>
						        </c:if>
						        
						        <!-- Single Video Post -->
						        <c:if test="${post.contentType eq 140 || post.contentType eq 141}">
							        <li>
							        	<div class="post-info">
							        		<div class="post-basic-info">
							        		<p class="h5">작성자: <a href="#">${post.writer}</a></p>
							        		</div>
							        		<c:forEach var="video" items="${post.contents.filePaths}">
							        			<video width="281" controls>
													<source src="${video}" type="video/mp4">
													<source src="${video}" type="video/ogg">
												</video>
							        		</c:forEach>
							        		<div class="post-basic-info">
							        			<c:if test="${not empty post.title}">
								        			<h3><a href="#">${post.title}</a></h3>
							        			</c:if>
							        		</div>
							        		<div class="post-info-rate-share">
							        			<div class="rateit">
							        				<span> </span>
							        			</div>
							        			<div class="post-share">
							        				<span> </span>
							        			</div>
							        			<div class="clear"> </div>
							        		</div>
							        	</div>
							        </li>
						        </c:if>
						        
						        <!-- Mixed Video Post -->
						        <c:if test="${post.contentType eq 240 || post.contentType eq 241}">
							        <li>
							        	<div class="post-info">
							        		<div class="post-basic-info">
							        		<p class="h5">작성자: <a href="#">${post.writer}</a></p>
							        		</div>
							        		<c:forEach var="video" items="${post.contents.filePaths}">
							        			<video width="281" controls>
													<source src="${video}" type="video/mp4">
													<source src="${video}" type="video/ogg">
												</video>
							        		</c:forEach>
							        		<div class="post-basic-info">
							        			<c:if test="${not empty post.title}">
								        			<h3><a href="#">${post.title}</a></h3>
							        			</c:if>
								        		<p class="text_content">${post.contents.textContent}</p>
							        		</div>
							        		<div class="post-info-rate-share">
							        			<div class="rateit">
							        				<span> </span>
							        			</div>
							        			<div class="post-share">
							        				<span> </span>
							        			</div>
							        			<div class="clear"> </div>
							        		</div>
							        	</div>
							        </li>
						        </c:if>
						        
					        </c:forEach>
					        
					        <%--
					        <li onclick="location.href='single-page.html';">
					        	<img src="images/img1.jpg" width="282" height="118">
					        	<div class="post-info">
					        		<div class="post-basic-info">
						        		<h3><a href="#">Animation films</a></h3>
						        		<span><a href="#"><label> </label>Movies</a></span>
						        		<p>Lorem Ipsum is simply dummy text of the printing & typesetting industry.</p>
					        		</div>
					        		<div class="post-info-rate-share">
					        			<div class="rateit">
					        				<span> </span>
					        			</div>
					        			<div class="post-share">
					        				<span> </span>
					        			</div>
					        			<div class="clear"> </div>
					        		</div>
					        	</div>
					        </li>
					        <li onclick="location.href='single-page.html';">
					        	<img src="images/img2.jpg" width="282" height="344">
								<div class="post-info">
					        		<div class="post-basic-info">
						        		<h3><a href="#">Animation films</a></h3>
						        		<span><a href="#"><label> </label>Movies</a></span>
						        		<p>Lorem Ipsum is simply dummy text of the printing & typesetting industry.</p>
					        		</div>
					        		<div class="post-info-rate-share">
					        			<div class="rateit">
					        				<span> </span>
					        			</div>
					        			<div class="post-share">
					        				<span> </span>
					        			</div>
					        			<div class="clear"> </div>
					        		</div>
					        	</div>
							</li>
					        <li onclick="location.href='single-page.html';">
					        	<img src="images/img3.jpg" width="282" height="210">
					        	<div class="post-info">
					        		<div class="post-basic-info">
						        		<h3><a href="#">Animation films</a></h3>
						        		<span><a href="#"><label> </label>Movies</a></span>
						        		<p>Lorem Ipsum is simply dummy text of the printing & typesetting industry.</p>
					        		</div>
					        		<div class="post-info-rate-share">
					        			<div class="rateit">
					        				<span> </span>
					        			</div>
					        			<div class="post-share">
					        				<span> </span>
					        			</div>
					        			<div class="clear"> </div>
					        		</div>
					        	</div>
					        </li>
					        <li onclick="location.href='single-page.html';">
					        	<img src="images/img4.jpg" width="282" height="385">
					        	<div class="post-info">
					        		<div class="post-basic-info">
						        		<h3><a href="#">Animation films</a></h3>
						        		<span><a href="#"><label> </label>Movies</a></span>
						        		<p>Lorem Ipsum is simply dummy text of the printing & typesetting industry.</p>
					        		</div>
					        		<div class="post-info-rate-share">
					        			<div class="rateit">
					        				<span> </span>
					        			</div>
					        			<div class="post-share">
					        				<span> </span>
					        			</div>
					        			<div class="clear"> </div>
					        		</div>
					        	</div>
					        </li>
					        <!----//--->
					        <li onclick="location.href='single-page.html';">
					        	<img src="images/img4.jpg" width="282" height="385">
					        	<div class="post-info">
					        		<div class="post-basic-info">
						        		<h3><a href="#">Animation films</a></h3>
						        		<span><a href="#"><label> </label>Movies</a></span>
						        		<p>Lorem Ipsum is simply dummy text of the printing & typesetting industry.</p>
					        		</div>
					        		<div class="post-info-rate-share">
					        			<div class="rateit">
					        				<span> </span>
					        			</div>
					        			<div class="post-share">
					        				<span> </span>
					        			</div>
					        			<div class="clear"> </div>
					        		</div>
					        	</div>
					        </li>
					        <li onclick="location.href='single-page.html';">
					        	<img src="images/img3.jpg" width="282" height="210">
					        	<div class="post-info">
					        		<div class="post-basic-info">
						        		<h3><a href="#">Animation films</a></h3>
						        		<span><a href="#"><label> </label>Movies</a></span>
						        		<p>Lorem Ipsum is simply dummy text of the printing & typesetting industry.</p>
					        		</div>
					        		<div class="post-info-rate-share">
					        			<div class="rateit">
					        				<span> </span>
					        			</div>
					        			<div class="post-share">
					        				<span> </span>
					        			</div>
					        			<div class="clear"> </div>
					        		</div>
					        	</div>
					        </li>
					        <li onclick="location.href='single-page.html';">
					        	<img src="images/img2.jpg" width="282" height="344">
								<div class="post-info">
					        		<div class="post-basic-info">
						        		<h3><a href="#">Animation films</a></h3>
						        		<span><a href="#"><label> </label>Movies</a></span>
						        		<p>Lorem Ipsum is simply dummy text of the printing & typesetting industry.</p>
					        		</div>
					        		<div class="post-info-rate-share">
					        			<div class="rateit">
					        				<span> </span>
					        			</div>
					        			<div class="post-share">
					        				<span> </span>
					        			</div>
					        			<div class="clear"> </div>
					        		</div>
					        	</div>
							</li>
							  <li onclick="location.href='single-page.html';">
					        	<img src="images/img1.jpg" width="282" height="118">
					        	<div class="post-info">
					        		<div class="post-basic-info">
						        		<h3><a href="#">Animation films</a></h3>
						        		<span><a href="#"><label> </label>Movies</a></span>
						        		<p>Lorem Ipsum is simply dummy text of the printing & typesetting industry.</p>
					        		</div>
					        		<div class="post-info-rate-share">
					        			<div class="rateit">
					        				<span> </span>
					        			</div>
					        			<div class="post-share">
					        				<span> </span>
					        			</div>
					        			<div class="clear"> </div>
					        		</div>
					        	</div>
					        </li>
					        <!----//--->
					         <li onclick="location.href='single-page.html';">
					        	<img src="images/img1.jpg" width="282" height="118">
					        	<div class="post-info">
					        		<div class="post-basic-info">
						        		<h3><a href="#">Animation films</a></h3>
						        		<span><a href="#"><label> </label>Movies</a></span>
						        		<p>Lorem Ipsum is simply dummy text of the printing & typesetting industry.</p>
					        		</div>
					        		<div class="post-info-rate-share">
					        			<div class="rateit">
					        				<span> </span>
					        			</div>
					        			<div class="post-share">
					        				<span> </span>
					        			</div>
					        			<div class="clear"> </div>
					        		</div>
					        	</div>
					        </li>
					        <li onclick="location.href='single-page.html';">
					        	<img src="images/img2.jpg" width="282" height="344">
								<div class="post-info">
					        		<div class="post-basic-info">
						        		<h3><a href="#">Animation films</a></h3>
						        		<span><a href="#"><label> </label>Movies</a></span>
						        		<p>Lorem Ipsum is simply dummy text of the printing & typesetting industry.</p>
					        		</div>
					        		<div class="post-info-rate-share">
					        			<div class="rateit">
					        				<span> </span>
					        			</div>
					        			<div class="post-share">
					        				<span> </span>
					        			</div>
					        			<div class="clear"> </div>
					        		</div>
					        	</div>
							</li>
					        <li onclick="location.href='single-page.html';">
					        	<img src="images/img3.jpg" width="282" height="210">
					        	<div class="post-info">
					        		<div class="post-basic-info">
						        		<h3><a href="#">Animation films</a></h3>
						        		<span><a href="#"><label> </label>Movies</a></span>
						        		<p>Lorem Ipsum is simply dummy text of the printing & typesetting industry.</p>
					        		</div>
					        		<div class="post-info-rate-share">
					        			<div class="rateit">
					        				<span> </span>
					        			</div>
					        			<div class="post-share">
					        				<span> </span>
					        			</div>
					        			<div class="clear"> </div>
					        		</div>
					        	</div>
					        </li>
					        <li onclick="location.href='single-page.html';">
					        	<img src="images/img4.jpg" width="282" height="385">
					        	<div class="post-info">
					        		<div class="post-basic-info">
						        		<h3><a href="#">Animation films</a></h3>
						        		<span><a href="#"><label> </label>Movies</a></span>
						        		<p>Lorem Ipsum is simply dummy text of the printing & typesetting industry.</p>
					        		</div>
					        		<div class="post-info-rate-share">
					        			<div class="rateit">
					        				<span> </span>
					        			</div>
					        			<div class="post-share">
					        				<span> </span>
					        			</div>
					        			<div class="clear"> </div>
					        		</div>
					        	</div>
					        </li>
					        --%>
					        <!-- End of grid blocks -->
					      </ul>
					    </div>
					</div>
				</div>
			</div>
		</div>
		<!---//End-content---->
		<!----wookmark-scripts---->
		  <script src="js/jquery.imagesloaded.js"></script>
		  <script src="js/jquery.wookmark.js"></script>
		  <script type="text/javascript">
		    (function ($){
		      var $tiles = $('#tiles'),
		          $handler = $('li', $tiles),
		          $main = $('#main'),
		          $window = $(window),
		          $document = $(document),
		          options = {
		            autoResize: true, // This will auto-update the layout when the browser window is resized.
		            container: $main, // Optional, used for some extra CSS styling
		            offset: 20, // Optional, the distance between grid items
		            itemWidth:280 // Optional, the width of a grid item
		          };
		      /**
		       * Reinitializes the wookmark handler after all images have loaded
		       */
		      function applyLayout() {
		        $tiles.imagesLoaded(function() {
		          // Destroy the old handler
		          if ($handler.wookmarkInstance) {
		            $handler.wookmarkInstance.clear();
		          }
		
		          // Create a new layout handler.
		          $handler = $('li', $tiles);
		          $handler.wookmark(options);
		        });
		      }
		      /**
		       * When scrolled all the way to the bottom, add more tiles
		       */
		      function onScroll() {
		        // Check if we're within 100 pixels of the bottom edge of the broser window.
		        var winHeight = window.innerHeight ? window.innerHeight : $window.height(), // iphone fix
		            closeToBottom = ($window.scrollTop() + winHeight > $document.height() - 100);
		
		        if (closeToBottom) {
		          // Get the first then items from the grid, clone them, and add them to the bottom of the grid
		          var $items = $('li', $tiles),
		              $firstTen = $items.slice(0, 10);
		          $tiles.append($firstTen.clone());
		
		          applyLayout();
		        }
		      };
		
		      // Call the layout function for the first time
		      applyLayout();
		
		      // Capture scroll event.
		      $window.bind('scroll.wookmark', onScroll);
		    })(jQuery);
		  </script>
		<!----//wookmark-scripts---->
		<!-- Go to top button -->
		<a href="#" class="btn_go_to_top"><img src="images/top-btn.png"></a>
		<%--
		<button type="button" class="btn btn-default btn_go_to_top" aria-label="Left Align" onclick="#container"><span class="glyphicon glyphicon-triangle-top" aria-hidden="true"> </span></button>
		--%>
		<!-- //Go to top button -->
		<!----start-footer--->
		<header id="header">
        <jsp:include page="/include/explore_top.jsp"></jsp:include>
    </header><!--/header-->
    
		<div class="footer footer_style">
			<p id="footer_font">&copy;copyright all reserved</p>
		</div>
		
		<!----//End-footer--->
		<!---//End-wrap---->
	</body>
</html>