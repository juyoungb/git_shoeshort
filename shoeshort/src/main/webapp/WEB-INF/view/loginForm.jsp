<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
String url= request.getParameter("url");
System.out.println("url"+url);
if(url ==null)	url = "/shoeshort/";
else			url = url.replace('~', '&');
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
<script type="text/javascript" src="https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.0.js" charset="utf-8"></script>
<title>SSlogin</title>
<style>
  body {
    font-family: Arial, sans-serif;
    background-color: #f1f1f1;
    margin: 0;
    padding: 0;
  }
#loginbody{
	  margin: auto;
    width: 400px;
    background-color: 	white;
    text-align: center;
     border-radius: 20px;
    padding: 20px;
}

#cenlo{
	text-align:center;
}
a {
  text-decoration: none;
  color: #333;
}
#apiBtn{
	margin:10px; 
	width:95%;
	
	cursor:pointer;
}
</style>
</head>
<body>
<div  id="loginbody">
	<div class="container" align="center">
	 <div align="center"><img alt="banner" id="cenlo" style="width:80%;cursor:pointer;" src="/shoeshort/resources/img/logo.png" class="logo" onclick="location.href='/shoeshort/';"></div>
	 <br /><br />
	<div align="left">
	<form name="frmLogin" action="login" method="post">
	<input type="hidden" name="url" value="<%=url%>" />
		<div class="mb-3">
			<label class="form-label" for="uid">아이디</label> 
			<input class="form-control" type="text" name="uid" id="uid" value="test6" placeholder="아이디 입력" />
		</div>
		<div class="mb-3">
			<label class="form-label" for="pwd">비밀번호</label> 
			<input class="form-control" type="password" name="pwd" id="pwd" value="1234"  placeholder="비밀번호 입력" />
		</div>
		<button class="btn btn-secondary" type="submit" style="width:100%; height:45px; font-size:22px;">로그인</button>
	</form>
    <hr>
    <div id="cenlo">
    <a href="memberJoin">회원가입</a>  |  
    <a href="memberSch">아이디/비밀번호 찾기</a>  
    </div>
	<a href="https://kauth.kakao.com/oauth/authorize?client_id=456f5080f677824581cb16867e1a0280&redirect_uri=http://localhost:8087/shoeshort/kakaoLoginProc
&response_type=code"><img src="/shoeshort/resources/img/btn/kakaoBtn.png" id="apiBtn" onclick="" /></a>
<a href="https://kauth.kakao.com/oauth/logout?client_id=456f5080f677824581cb16867e1a0280&logout_redirect_uri=http://localhost:8087/shoeshort/kakaoLogoutProc">카카오 로그아웃</a>
    <br>
</div>
</body>
</html>
