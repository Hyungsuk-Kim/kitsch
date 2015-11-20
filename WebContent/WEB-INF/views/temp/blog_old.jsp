<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="microblog.kitsch.test.DomainObjectsForTest" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Blog | Kitsch</title>
	
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
    <script src="<c:url value='/js/imagesloaded.pkgd.min.js' />"></script>
</head><!--/head-->
	<%
		request.setAttribute("postings", DomainObjectsForTest.postings);
		request.setAttribute("defaultBlog", DomainObjectsForTest.defaultBlog);
		request.setAttribute("logonMember", DomainObjectsForTest.logonMember);
	%>
<body class="homepage">
	<header id="header">
		<c:import url="/include/top.jsp" />
	</header>
	<section class="table_label  blog_posting">
	<div class="container-fluid">
	<img src="${requestScope.defaultBlog.headerImage}">
	<h1><label>${requestScope.defaultBlog.blogName}</label></h1>
	 <a href="/blog?action=following&blogName=${requestScope.defaultBlog.blogName}">팔로우</a><hr>
		<img src="${logonMember.profileImage}"  class="img-circle2">
		<h3 class="writer_color">${logonMember.name}</h3>
	</div>
	
	<!-- posting -->
	<c:if test="${empty requestScope.postings}">
		<div class="col-md-4 col-md-offset-4">포스트가 없습니다.</div>
	</c:if>
	<c:if test="${not empty requestScope.postings}">
		<c:forEach var="posting" items="${requestScope.postings}">
		   <div class="my_posting">
		   <div class="postingNum hidden"><label>${posting.num}</label></div>
		   <div class="postingTitle"><label class="h2">${posting.title}</label></div>
		   <div class="postingWriter"><label class="h3">${posting.writer}</label></div>
		   		<div align="center">
			   		<object class="embed-responsive-item">
				   		<c:if test="${posting.contentType eq 120 || posting.contentType eq 121 || posting.contentType eq 220 || posting.contentType eq 221}">
				      		<img width="50" height="50" src="${posting.contents.filePaths}">
				   		</c:if>
				   		<c:if test="${posting.contentType eq 140 || posting.contentType eq 141 || posting.contentType eq 240 || posting.contentType eq 241}">
					      	<video class="player playlist">
					      		<source src="${posting.contents.filePaths}">
					      	</video>
				   		</c:if>
				   		<c:if test="${posting.contentType eq 130 || posting.contentType eq 131 || posting.contentType eq 230 || posting.contentType eq 231}">
				      		<audio class="player playlist">
					      		<source src="${posting.contents.filePaths}">
					      	</audio>
				   		</c:if>
			   		</object>
		      	</div>
			 <div>
			 	${posting.contents.textContent}
			 </div>
				<div class="postingTags"><label>태그 : <span>${posting.tags}</span></label></div>
				<div class="postingRegDate"><label>작성날짜 : <span>${posting.regDate}</span></label></div>
			 <hr>
			 <div class="postingLikes">
			 <label class="text-left">좋아요 : <span>${posting.likes}</span>개</label>
			 <a href="/posting?action=addLike&blogName=${requestScope.blog.blogName}&postingNum=${posting.num}" class="text-right"><span class="glyphicon glyphicon-thumbs-up">좋아요</span></a>
			 </div>
			 <div class="postingReblog">
			 <label class="text-left">리블로그 : <span>${posting.reblogCount}</span>개</label>
			 <a href="/posting?action=reblogRequest&blogName=${requestScope.blog.blogName}&postingNum=${posting.num}" class="text-right"><span class="glyphicon glyphicon-thumbs-up">리블로그</span></a>
			 </div>
			 <div class="replies">
			 	<a href="/posting?action=replies&blogName=${requestScope.blog.blogName}&postingNum=${posting.num}">댓글보기</a>
			 </div>
			 <c:if test="${sessionScope.member.email eq requestScope.blog.email}">
				 <div>
				 	<button value="수정" onclick="goUrl('/posting?action=updateForm&blogName=${requestScope.blog.blogName}&postingNum=${posting.num}');"></button>
				 	<button value="삭제" onclick="goUrl('/posting?action=remove&blogName=${requestScope.blog.blogName}&postingNum=${posting.num}');"></button>
				 </div>
			 </c:if>
			</div>
		</c:forEach>
	</c:if>
	</section>
	<footer id="footer" class="midnight-blue">
        <c:import url="/include/footer.jsp" />
    </footer>
    
    <script src="<c:url value='js/jquery.js' />"></script>
    <script src="<c:url value='js/bootstrap.min.js' />"></script>
    <script src="<c:url value='js/jquery.prettyPhoto.js' />"></script>
    <script src="<c:url value='js/jquery.isotope.min.js' />"></script>
    <script src="<c:url value='js/main.js' />"></script>
    <script src="<c:url value='js/wow.min.js' />"></script>
</body>
</html>