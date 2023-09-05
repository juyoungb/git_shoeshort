<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="vo.*"%>
<%@ include file="../_inc/inc_head_fr.jsp" %>
<%
MemberInfo mi = (MemberInfo)session.getAttribute("loginInfo");
String[] arrBirth = mi.getMi_birth().split("-");
String birth = arrBirth[0] + "년 "+arrBirth[1]+ "월 "+ arrBirth[2] + "일 ";
String[] arrPhone = mi.getMi_phone().split("-");
String p2 = arrPhone[1], p3 = arrPhone[2];
String[] arrEmail = mi.getMi_email().split("@");
String e1 = arrEmail[0], e3 = arrEmail[1];
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
#agreement { width:500px; height:100px; overflow:auto;}
.pLine {width:500px; }
</style>
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.4.js"></script>
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

<h2>회원 정보 수정 폼</h2>
<form name="frmJoin" action="memberUp" method="post">
<p class="pLine">아이디 :<%=mi.getMi_id() %> </p>
<p class="pLine">이름 :<%=mi.getMi_name() %> </p>
<p class="pLine">성별 :<%=mi.getMi_gender() %> 자 </p>
<p class="pLine">생년월일 :<%=birth %> </p>
<p class="pLine">휴대폰 : 010 - 
   <input type="text" name="p2" value="<%=p2 %>" size="4" maxlength="4" /> - 
   <input type="text" name="p3" value="<%=p3 %>" size="4" maxlength="4" />
</p>
<p class="pLine">이메일 : 
   <input type="text" name="e1" value="<%=e1 %>" id="e1" size="10" /> @ 
   <select name="e2" id="e2">
      <option value="">도메인 선택</option>
      <option value="naver.com">네이버</option>
      <option value="nate.com">네이트</option>
      <option value="gmail.com">지메일</option>
      <option value="direct">직접입력</option>
   </select>
   <input type="text" name="e3" value="<%=e3 %>" id="e3" size="10" />
</p>
<p class="pLine">광고 수신 : 
   <input type="radio" name="mi_isad" value="y" id="adY" checked="checked"/>
   <label for="adY">받음</label>&nbsp;&nbsp;&nbsp;&nbsp;
   <input type="radio" name="mi_isad" value="n" id="adN" />
   <label for="adN">안받음</label>
</p>
<p class="pLine" align="center">
   <input type="submit" value="정보 수정" />
</p>
</form>
<%@ include file="../_inc/inc_foot_fr.jsp" %>