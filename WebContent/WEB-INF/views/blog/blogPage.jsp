<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
   <head>
      <title>Blog | Kitsch</title>
      <link href="css/style2.css" rel='stylesheet' type='text/css' />
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <link rel="shortcut icon" type="image/x-icon" href="images/fav-icon.png" />
      <script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
      <!----webfonts---->
      <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700,800' rel='stylesheet' type='text/css'>
      <!----//webfonts---->
      <!-- Global CSS for the page and tiles -->
        <link rel="stylesheet" href="css/main2.css">
        <!-- //Global CSS for the page and tiles -->
      <!-- Custom CSS for kitsch Web App -->
      <!-- //Custom CSS for kitsch Web App -->
      <!-- Custom CSS for kitsch Web App -->
        <link rel="stylesheet" href="css/bootstrap.min.css">
      <!-- //Custom CSS for kitsch Web App -->
      
      <link href="css/font-awesome.min.css" rel="stylesheet">
            
       <link href="css/animate.min.css" rel="stylesheet">
       
       <link href="css/prettyPhoto.css" rel="stylesheet">
       
       <link href="css/main.css" rel="stylesheet">
       
       <link href="css/responsive.css" rel="stylesheet">
       
        <link rel="stylesheet" href="css/kitsch.css">
      
      
      <!---start-click-drop-down-menu----->
      <script src="js/jquery.min.js"></script>
      <!----start-dropdown--->
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
      <script src="js/jquery.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/jquery.prettyPhoto.js"></script>
	<script src="js/jquery.isotope.min.js"></script>
	<script src="js/main.js"></script>
	<script src="js/wow.min.js"></script>
      <!----//End-dropdown--->
   </head>
   
   <body class="homepage">
   <!-- blog header -->
      <div class="container-fluid blog_header">
         <div class="img_center" style="background-image: url('${requestScope.blog.headerImage}')">
            <h3 class="text-right owner">${blogOwner.name}의 블로그 입니다.</h3>
            <p class="text-center">
               <label class="h1 text-center">${requestScope.blog.blogName}</label>
               <c:if test="${sessionScope.logonMember.email ne blogOwner.email}">
               <span class="text-right"><a href="/blog?action=following&blogName=${requestScope.blog.blogName}">팔로우</a></span>
               </c:if>
               <c:if test="${sessionScope.logonMember.email eq blogOwner.email}">
	               <c:forEach var="blog" items="${sessionScope.memberBlogs}" varStatus="status">
	               	<c:if test="${status.count > 1}">
               			<a href="/kitsch/blog?action=remove&blogName=${requestScope.blog.blogName}">블로그 삭제</a>
	               	</c:if>
	               </c:forEach>
               </c:if>
            </p>
            <span class="img_center">
               <img src="${blogOwner.profileImage}"  class="img-circle2">
            </span>
         </div>
      </div>
   <!-- blog header -->
      <!---start-content---->
      <div class="container">
         <div class="row">
         <div class="single-page">
            <c:if test="${not empty requestScope.postings}">
               <c:forEach var="post" items="${requestScope.postings}">
                  <div class="col-sm-6 col-sm-offset-3 post-basic-info myblog_post" >
                     <div class="single-page-artical blog_contents radius_post">
                        <div class="artical-content">
                        <p class="h3 post_writer">작성자: <strong> ${post.writer} </strong></p>
                        <hr>
                        <%-- Post Media Contents --%>
                           <%-- Image post --%>
                           <c:if test="${post.contentType eq 120 || post.contentType eq 121 || post.contentType eq 220 || post.contentType eq 221}">
                              <c:if test="${not empty post.contents.filePaths}">
                                 <c:forEach var="image" items="${post.contents.filePaths}">
                                    <div><img src="${image}" title="banner1"></div>
                                 </c:forEach>
                              </c:if>
                           </c:if>
                           <%-- //Image post --%>
                           
                           <%-- Audio post --%>
                           <c:if test="${post.contentType eq 130 || post.contentType eq 131 || post.contentType eq 230 || post.contentType eq 231}">
                              <c:if test="${not empty post.contents.filePaths}">
                                 <c:forEach var="audio" items="${post.contents.filePaths}">
                                 <div>
                                      <audio class="col-sm-6 col-sm-offset-3" controls>
                                       <source src="${audio}" type="audio/ogg">
                                       <source src="${audio}" type="audio/mpeg">
                                       <source src="${audio}" type="audio/mp3">
                                       <source src="${audio}" type="audio/mp4">
                                    </audio>
                                 </div>
                                   </c:forEach>
                              </c:if>
                           </c:if>
                           <%-- //Audio post --%>
                           
                           <%-- Video post --%>
                           <c:if test="${post.contentType eq 140 || post.contentType eq 141 || post.contentType eq 240 || post.contentType eq 241}">
                              <c:if test="${not empty post.contents.filePaths}">
                                 <c:forEach var="video" items="${post.contents.filePaths}">
                                      <div>
                                         <video class="col-sm-6 col-sm-offset-3" controls>
                                          <source src="${video}" type="video/mp4">
                                          <source src="${video}" type="video/ogg">
                                       </video>
                                      </div>
                                   </c:forEach>
                              </c:if>
                           </c:if>
                           <%-- //Video post --%>
                           
                           <div class="col-xs-12">
                           <%-- Post title --%>
                           <c:if test="${not empty post.title}">
                              <h3>${post.title}</h3>
                           </c:if>
                           <%-- //Post title --%>
                           
                           <%-- Post Text Content --%>
                           <c:if test="${not empty post.contents.textContent}">
                              <p class="text_content">${post.contents.textContent}</p> 
                           </c:if>
                           <%-- //Post Text Content --%>
                           </div>
                           
                            </div>
                            <div class="artical-links">                     
                              <%--
                              <ul>
                                 <li><a href="#"><img src="images/blog-icon2.png" title="Admin"><span>admin</span></a></li>
                                 <li><a href="#"><img src="images/blog-icon3.png" title="Comments"><span>No comments</span></a></li>
                                 <li><a href="#"><img src="images/blog-icon4.png" title="Lables"><span>View posts</span></a></li>
                              </ul>
                              --%>
                           </div>
                           <!-- 좋아요 foot -->
                           <div class="share-artical">
                           좋아요${post.likes}개
                           <%--
                              <ul>
                                 <li><a href="#"><img src="images/facebooks.png" title="facebook">Facebook</a></li>
                                 <li><a href="#"><img src="images/twiter.png" title="Twitter">Twiiter</a></li>
                                 <li><a href="#"><img src="images/google+.png" title="google+">Google+</a></li>
                                 <li><a href="#"><img src="images/rss.png" title="rss">Rss</a></li>
                              </ul>
                           --%>
                           </div>
                           <div class="clear"> </div>
                     </div>
                     </div>
               </c:forEach>
            </c:if>
            </div>
          </div>
      </div>
      
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
      
      <header id="header">
        <jsp:include page="/include/top.jsp"></jsp:include>
    </header><!--/header-->
    
      <div class="footer footer_style">
         <p id="footer_font">&copy;copyright all reserved</p>
      </div>
      
      <!----//End-footer--->
   </body>
</html>