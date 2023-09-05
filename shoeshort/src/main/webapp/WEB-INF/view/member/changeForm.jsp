<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");
String kind=request.getParameter("kind");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="resources/js/jquery-3.6.4.js"></script>
<script>
$(document).ready(function(){
	$("#e2").change(function(){
		if($(this).val() == ""){
			$("#e3").val("");
		} else if($(this).val() == "direct"){
			$("#e3").val("");	$("#e3").focus();
		} else{
			$("#e3").val($(this).val());
		}
	});
});
</script>
</head>
<body>
<form name="frm" action="changeInfo" method="post">
<input type="hidden" name="uid" value="<%=loginInfo.getMi_id() %>" />
<input type="hidden" name="kind" value="<%=kind %>" />
<%if(kind.equals("pw")){%>
<h2>비밀번호 변경</h2>
현재 비밀번호 : <input type="password" name="pwd" value="1234"><br>
새 비밀번호 : <input type="password" name="npwd" value="1234"><br>
새 비밀번호 확인 : <input type="password" name="npwd2" value="1234"><br>
<input type="submit" value="변경">
<%} else if(kind.equals("email")) {
	String[] arrEmail = loginInfo.getMi_email().split("@");
	String e1 = arrEmail[0], e3 = arrEmail[1];
%>
<h2>이메일 변경</h2>
<p class="pLine">이메일 : 
   <input type="text" name="e1" id="e1" size="10" value="<%=e1 %>"/> @ 
   <select name="data" id="e2">
      <option value="">도메인 선택</option>
      <option value="naver.com"<% if(e3.equals("naver.com")) {%>selected="selected"<%} %>>네이버</option>
      <option value="nate.com"<% if(e3.equals("nate.com")) {%>selected="selected"<%} %>>네이트</option>
      <option value="gmail.com"<% if(e3.equals("gmain.com")) {%>selected="selected"<%} %>>지메일</option>
      <option value="direct">직접입력</option>
   </select>
   <input type="text" name="e3" id="e3" size="10" value="<%=e3 %>" />
</p>
<input type="submit" value="변경">
<%} else if(kind.equals("phone")) {
	String[] arrPhone = loginInfo.getMi_phone().split("-");
	String p2 = arrPhone[1], p3 = arrPhone[2];
%>
<h2>휴대폰 번호 변경</h2>
	  <p class="pLine">휴대폰 : 010 -
	   <input type="text" name="p2" size="4" maxlength="4" value="<%=p2%>"/> - 
	   <input type="text" name="p3" size="4" maxlength="4" value="<%=p3%>"/>
		</p>
<input type="submit" value="변경">
<%} %>
</form>
</html>