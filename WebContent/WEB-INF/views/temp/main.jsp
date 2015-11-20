<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
	<title>Kitsch | dashboard</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/font-awesome.min.css" rel="stylesheet">
    <link href="css/prettyPhoto.css" rel="stylesheet">
    <link href="css/animate.min.css" rel="stylesheet">
    <link href="css/main.css" rel="stylesheet">
    <link href="css/responsive.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="js/html5shiv.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->       
    <link rel="shortcut icon" href="images/ico/favicon.ico">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="images/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="images/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="images/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="images/ico/apple-touch-icon-57-precomposed.png">
</head>
<body>
	<div class="container">
		<div class="row">
			<c:forEach begin="0" end="7" >
				<div class="col-md-4 panel panel-default">
					<div class="panel-heading">
			          <img src="https://lh3.googleusercontent.com/uFp_tsTJboUY7kue5XAsGA=s28" width="28px" height="28px">
			          <strong>작성자 : ${posting.writer}</strong>
					</div>
					<div class="panel-body">
						<p class="title h3">
						제목 : ${posting.title}
						</p>
						<p>
							<div>
								<img src="/kitsch/images/a93015bear5790.jpg" width="110" height="117">
								<%--
								<img src="${posting.contents.filePaths}" width="110" height="117">
								--%>
							</div>
							<div>
								내용 출력  ${posting.contents.textContent}
							</div>
						</p>
						<hr>
						<div class="comment">
			
						
						<a href="reply.jsp">
						댓글 ${posting.replyCount}개
						</a>
						
					
							<a href="#" class="good">
							<span class="glyphicon glyphicon-heart"></span>
							</a>
							
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</body>
</html>