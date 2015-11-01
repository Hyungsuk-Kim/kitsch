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
<nav class="navbar navbar-inverse navbar-fixed-top" role="banner">
            <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
    	<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">Brand</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      
      <form class="navbar-form navbar-left" role="search">
        <div class="form-group">
          <input type="text" class="form-control" placeholder="Search">
        </div>
        <button type="submit" class="btn btn-default">Submit</button>
      </form>
      <div class="menu">
      <div class="btn-group">
	<a href="#" class="nav_icons"><i class="glyphicon glyphicon-home h2 m_padding"></i></a>
	</div>
	<div class="btn-group">
	<a href="#" class="nav_icons"><i class="glyphicon glyphicon-dashboard h2 m_padding"></i></a>
	</div>
	
	<div class="btn-group">
      <a class="nav_icons" aria-expanded="false" href="#" data-toggle="dropdown">
        <i class="glyphicon glyphicon-user h2 m_padding"></i>
      </a>
      <ul class="dropdown-menu">
        <li><a href="#">Dropdown link</a></li>
        <li><a href="#">Dropdown link</a></li>
        <li><a href="#">Dropdown link</a></li>
       </ul>
    </div>

	<div class="btn-group">	
	<a href="#" class="nav_icons" data-toggle="modal" data-target="#myModal">
	<i class="glyphicon glyphicon-pencil h2 m_padding">
	</i>
	</a>
	</div>

   <ul class="nav navbar-nav navbar-right">
    <li><button type="button" class="btn btn-primary btn-lg top_font" data-toggle="modal" data-target="#myModal">
    로그인
</button></li>
   <li><button type="button" class="btn btn-primary btn-lg top_font">
    회원가입
</button></li>
   </ul>
      </div>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
            
        </nav><!--/nav-->
<c:import url="/login_modal.jsp" />
</body>
</html>