<%@ page contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
$('#myModal').on('shown.bs.modal', function () {
	  $('#myInput').focus()
	})
$('#update').on('shown.bs.modal', function () {
	  $('#update').focus()
	})		
</script>
<meta charset=UTF-8>
<title>Insert title here</title>
</head>
<body>
<nav class="navbar navbar-inverse" role="banner">
            <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
    	<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <c:if test="${not empty sessionScope.member}">
      	<a class="navbar-brand" href="explore?action=trend">Kitsch</a>
      </c:if>
      <c:if test="${empty sessionScope.member}">
      	<a class="navbar-brand" href="index.jsp">Kitsch</a>
      </c:if>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      
      <form class="navbar-form navbar-left" role="search">
        <div class="form-group">
          <input type="text" class="form-control" placeholder="Search">
        </div>
        <button type="submit" class="btn btn-default">Search</button>
      </form>
      
		<div class="menu">
      	<c:if test="${not empty sessionScope.member}">
						<div class="btn-group">
							<a href="blog.jsp" class="nav_icons"><i
								class="glyphicon glyphicon-home h2 m_padding"></i></a>
						</div>
						<div class="btn-group">
							<a href="explore.jsp" class="nav_icons"><i
								class="glyphicon glyphicon-dashboard h2 m_padding"></i></a>
						</div>

						<div class="btn-group">
							<a class="nav_icons" aria-expanded="false" href="#"
								data-toggle="dropdown"> <i
								class="glyphicon glyphicon-user h2 m_padding"></i>
							</a>
							<ul class="dropdown-menu">
								<li class="top_dropdown">계정</li>
								<li class="divider"></li>
								<li><a href="like.jsp"> <span
										class="glyphicon glyphicon-thumbs-up drop_sub top_dropdown">
											좋아요</span>
								</a></li>
								<li><a href="followingList.jsp"> <span
										class="glyphicon glyphicon-th-list top_dropdown"> 팔로잉</span>
								</a></li>
								<li><a href="user_update.jsp"> <span
										class="glyphicon glyphicon-cog top_dropdown"> 설정</span>
								</a></li>
								<li><a href="member?action=signOut" class="logout"> <span
										class="glyphicon glyphicon-remove-sign top_dropdown">로그아웃</span>
								</a></li>
								<li class="divider"></li>
								<li class="top_dropdown">블로그</li>
								<li class="divider"></li>
								<li><a href="createblog.jsp"> <span
										class="glyphicon glyphicon-plus top_dropdown">블로그 추가</span></a></li>
								<li><a href="#"><div class="top_dropdown">포스트</div></a></li>
								<li><a href="#"><div class="top_dropdown">팔로워</div></a></li>
								<li><a href="#"><span
										class="glyphicon glyphicon-wrench">블로그 수정</span> </a></li>
							</ul>
						</div>

						<div class="btn-group">
							<a href="postingForm.jsp" class="nav_icons"> <i
								class="glyphicon glyphicon-pencil h2 m_padding"> </i>
							</a>
						</div>
				</c:if>
				<c:if test="${empty sessionScope.member}">
						<ul class="nav navbar-nav navbar-right">
							<li><button type="button"
									class="btn btn-primary btn-lg top_font" data-toggle="modal"
									data-target="#myModal">로그인</button></li>
							<li>
								<button type="button" class="btn btn-primary btn-lg top_font"
									onclick="goUrl('index.jsp')">회원가입</button>
							</li>
						</ul>
				</c:if>
					</div>
				
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
            
        </nav><!--/nav-->
<c:import url="/login_modal.jsp" />
</body>
</html>