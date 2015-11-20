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
	<link href="css/kitsch_temp.css" rel="stylesheet">
         
    <link rel="shortcut icon" href="images/ico/favicon.ico">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="images/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="images/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="images/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="images/ico/apple-touch-icon-57-precomposed.png">
	
	<script src="<c:url value='/js/kitsch.js' />"></script>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
</head><!--/head-->
<body>
	<div class="container">
        <div class="row">
			<form name="postingForm" action="/kitsch/posting?action=write" method="POST" role="form" enctype="multipart/form-data">
				<%--
				<div class="form-group"><input type="hidden" value="${param.blogName}" name="blogName"></div>
				--%>
				<div class="form-group">
				<select name="blogName">
					<c:forEach var="blog" items="${requestScope.memberBlogs}">
						<option value="${blog.blogName}">${blog.blogName}</option>
					</c:forEach>
				</select>
				</div>
				<div class="form-group">
					<label for="title h2 col-xs-4">제목</label>
					<input class="form-control h2 col-xs-8" type="text" name="title" maxlength="100" placeholder="제목">
				</div>
				<div class="form-group">
					<label for="title h2">작성자 ${sessionScope.member.name}</label>
					<input class="form-control h2" type="hidden" name="writer" maxlength="60" value="${sessionScope.member.name}">
				</div>
				<div class="form-group">
					<textarea name="contents"></textarea>
				</div>
				<div id="uploadPreview">
				</div>
				<div class="form-group">
					<label for="title h3 col-xs-4">태그</label>
					<input class="form-control h2 col-xs-8" type="text" name="tags" maxlength="100" placeholder="#태그">
				</div>
				<div class="form-group">
					<div><label for="title h3 col-xs-4">공개여부</label></div>
					<div class="radio">
						<label>
						<input type="radio" name="exposure" value="0" checked>
						공개, 댓글과 리블로그 모두 허용
						</label>
					</div>
					<div class="radio">
						<label>
						<input type="radio" name="exposure" value="2">
						공개, 댓글만 허용
						</label>
					</div>
					<div class="radio">
						<label>
						<input type="radio" name="exposure" value="1">
						공개, 리블로그만 허용
						</label>
					</div>
					<div class="radio">
						<label>
						<input type="radio" name="exposure" value="3">
						공개, 댓글과 리블로그 모두 허용하지 않음
						</label>
					</div>
					<div class="radio">
						<label>
						<input type="radio" name="exposure" value="4">
						비공개 포스팅
						</label>
					</div>
				</div>
				<div class="form-group">
					<label for="title h3 col-xs-4">미디어 파일</label>
					<input type="file" id="choose" name="file" multiple>
				</div>
				<div class="form-group">
					<input type="hidden" name="postingType" value="0">
				</div>
				<div class="clearfix"></div>
				<div class="form-group">
					<%--
					<input type="button" class="btn btn-default" value="포스팅" onclick="postingCheck(this.form);">
					--%>
					<input type="submit" class="btn btn-default" value="포스팅">
					<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
				</div>
			</form>
		</div>
	</div>
	<script type="text/javascript">
	// var url = window.URL || window.webkitURL; // alternate use

	function readFile(file) {
	  
	    var reader = new FileReader();
	    var file  = new File();
	  
	    reader.readAsDataURL(file);  
	    reader.onload = function(_file) {
	        file.src    = _file.target.result;              // url.createObjectURL(file);
	        file.onload = function() {
	            var w = this.width,
	                h = this.height,
	                t = file.type,                           // ext only: // file.type.split('/')[1],
	                n = file.name,
	                s = ~~(file.size/1024) +'KB';
	            if (t.indexOf("image/") != -1) {
	            	$('#uploadPreview').append('<img src="'+ this.src +'"> '+w+'x'+h+' '+s+' '+t+' '+n+'<br>');
	            } else if (t.indexOf("video/") != -1) {
	            	$('#uploadPreview').append('<video controls>'
	    					+'<source src="'+ this.src +'" type="video/ogg">'
	    					+'<source src="'+ this.src +'" type="video/mp4">'
	    					+'</video>'+w+'x'+h+' '+s+' '+t+' '+n+'<br>');
	            } else if (t.indexOf("audio/") != -1) {
	            	$('#uploadPreview').append('<audio controls>'
	    					+'<source src="'+ this.src +'" type="audio/ogg">'
	    					+'<source src="'+ this.src +'" type="audio/mpeg">'
	    					+'<source src="'+ this.src +'" type="audio/mp3">'
	    					+'<source src="'+ this.src +'" type="audio/mp4">'
	    					+'</audio>' +w+'x'+h+' '+s+' '+t+' '+n+'<br>');
	            }
	        };
	        image.onerror= function() {
	            alert('Invalid file type: '+ file.type);
	        };      
	    };
	    
	}
	$("#choose").change(function (e) {
	    if(this.disabled) return alert('File upload not supported!');
	    var F = this.files;
	    if(F && F[0]) for(var i=0; i<F.length; i++) readImage( F[i] );
	});
	</script>
</body>
</html>
