<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Posting | Kitsch</title>
	
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
	<script src="<c:url value='/ckeditor/ckeditor.js' />"></script>
	<script src="<c:url value='/js/board.js' />"></script>
</head><!--/head-->
<body>
	<div class="container">
        <div class="row">
			<form name="postingForm" action="/kitsch/posting?action=reply" method="POST" role="form" enctype="multipart/form-data">
				<div class="form-group"><input type="hidden" value="${param.blogName}" name="blogName"></div>
				<div class="form-group">
					<label for="title h2">작성자 ${sessionScope.member.name}</label>
					<input class="form-control h2" type="hidden" name="writer" maxlength="60" value="${sessionScope.member.name}">
				</div>
				<div class="form-group">
					<textarea class="" name="contents"></textarea>
				</div>
				<div class="form-group">
					<div><label for="title h3 col-xs-4">공개여부</label></div>
					<div class="radio">
						<label class="radio-inline">
						<input type="radio" name="exposure" value="0" checked>
						공개
						</label>
						<label class="radio-inline">
						<input type="radio" name="exposure" value="4">
						비공개
						</label>
					</div>
				</div>
				<div class="form-group">
					<input type="hidden" name="postingType" value="1">
				</div>
				<div class="clearfix"></div>
				<div class="form-group">
					<input type="button" class="btn btn-default" value="댓글달기" onclick="boardWriteCheck(this.form);">
					<input type="button" class="btn btn-default" value="취소" onclick="goUrl('list?pageNumber=${param.pageNumber}');">
				</div>
			</form>
		</div>
	</div>
</body>
</html>
