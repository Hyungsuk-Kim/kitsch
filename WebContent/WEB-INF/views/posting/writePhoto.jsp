<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>

<meta charset=UTF-8>
<title>micro Blogging Service</title>
<!-- Jquery 다운 받아 사용하는게 귀찮으면 아래 처럼 ㅎㅎ -->

<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>

<script type="text/javascript">

function readUploadImage( inputObject ) {

	if ( window.File && window.FileReader ) {
		/*

		입력된 파일이 1개 이상 있는지 확인~

		*/
		if ( inputObject.files && inputObject.files[0]) {

			/* 이미지 파일인지도 체크해 주면 좋지~ */
			if ( !(/image/i).test(inputObject.files[0].type ) ){

				alert("이미지 파일을 선택해 주세요!");

				return false;
			}
			/* FileReader 를 준비 한다. */
			var reader = new FileReader();
			reader.onload = function (e) {
				/* reader가 다 읽으면 imagePreview에 뿌려 주면 끝~  */
				$('#imagePreview').attr('src', e.target.result);
			}
			reader.readAsDataURL(inputObject.files[0]);
		}
	} else {
		alert( "미리보기 안되요.~ 브라우저를 업그레이드하세요~");
	}
}

/*

input 태그에 보통

<element onchange="SomeJavaScriptCode">

해 주던지 아님

jquery를 이용해 change 이벤트를 달아 줘도 된다.

*/

$("#uploadImage").change(function(){

    readUploadImage(this);

});
</script>

</head>
<body>
	<div>
		
		<!-- 블로그 이름은 현재 자신의 블로그 이름이 출력 되야 한다. -->

			<ul class="nav navbar-nav navbar-right">
                      <li class="dropdown">
                      선택된 블로그 이름
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <i class="glyphicon glyphicon-user"></i>
                        </a>
                        <ul class="dropdown-menu">
                          <li><a href="">자신의 블로그</a></li>
                          <li><a href="">자신의 블로그</a></li>
                        </ul>
                      </li>
                    </ul>
       <input type="text" placeholder="제목"><br>
			<textarea rows="1" cols="50" placeholder="내용을 입력해 주세요"></textarea><br>
	             
                    
      <!-- 이미지 업로드 부분 -->
	<form id="form1">
    <input type='file' id="uploadImage" />

    <div id="preview">
	<img id="imagePreview" src="#" width="200" height="200"/>
    </div>
</form>	
		<div>	
			<input type="button" value="포스팅 쓰기" onclick="#">		
			<input type="button" value="닫기" onclick="close">
		</div>		
	</div>
</body>
</html>