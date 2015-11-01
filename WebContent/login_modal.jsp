<%@ page contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset=UTF-8>
<title>Insert title here</title>
</head>
<body>
<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Login</h4>
      </div>
      <form action="member?action=signIn" method="POST">
      <div class="modal-body">
	      <table class="table_label">
		      <tr>
			      <td class="table_label"><label>E-mail : </label></td>
			      <td><input type="text" name="email"></td>
		      </tr>
		      <tr>
			      <td><label>비밀번호 : </label></td>
			      <td><input type="password" name="password"></td>
		      </tr>
	      </table>
      </div>
      <div class="modal-footer">
        <button type="submit" class="btn btn-default">로그인</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
      </div>
      </form>
    </div>
  </div>
</div>
</body>
</html>