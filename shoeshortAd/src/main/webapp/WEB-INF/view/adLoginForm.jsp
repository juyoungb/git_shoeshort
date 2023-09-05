<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<style>
*{
    padding: 0;
    margin: 0;
    border: none;
}
body{
    font-size: 14px;
    font-family: 'Roboto', sans-serif;
}
.login-wrapper{
    width: 600px;
    height: 350px;
    padding: 40px;
    box-sizing: border-box;
    display: inline-block; position: absolute; left: 600px; top: 150px;
}

.login-wrapper > h2{
    font-size: 40px;
    color: #8296b0;
    margin-bottom: 20px;
    text-align: center;
}
#login-form > input{
    width: 100%;
    height: 48px;
    padding: 0 10px;
    box-sizing: border-box;
    margin-bottom: 16px;
    border-radius: 6px;
    background-color: #F8F8F8;
}
#login-form > input::placeholder{
    color: #D2D2D2;
}
#login-form > input[type="submit"]{
    color: #fff;
    font-size: 16px;
    background-color: #8296b0;
    margin-top: 20px;
    position: absolute; left:200px;
    width:200px;
}
#login-form > label{
    color: #999999;
}
</style>
<body>
<div class="login-wrapper" >
<h2>ShoeShort</h2>
<form name="frmLogin" id="login-form" method="post">
아이디   <input type="text" name="uid" value="admin" /><br />
비밀번호   <input type="password" name="pwd" value="1111" /><br />
<input type="submit" value="로그인" />
</form>
</div>
</body>
</html>