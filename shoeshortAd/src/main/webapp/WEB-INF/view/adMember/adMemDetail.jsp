<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%
MemberDetails memberDetails = (MemberDetails)request.getAttribute("memberDetails");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
<style>
td {border:1px solid black;}
</style>
</head>
<body>
<div>
<table width="600" height="200" class="table table-striped table-sm">
<h2>회원 상세 정보</h2>
<br />
<%
String mi_id = "", mi_name = "", mi_birth = "", mi_email = "", mi_status = "", ma_addr1 ="", ma_addr2 = "", mi_date = "";
int mi_point = 0;

if (memberDetails == null){
    mi_id = "회원정보 없음";  mi_name = "회원정보 없음"; mi_birth = "회원정보 없음";
    mi_email = "회원정보 없음"; mi_status = "회원정보 없음";
    ma_addr1 ="회원정보 없음"; ma_addr2 = "회원정보 없음"; mi_date = "회원정보 없음";
    mi_point = 0;
} else {
    mi_id = memberDetails.getMi_id();
    mi_name = memberDetails.getMi_name();
    mi_birth = memberDetails.getMi_birth();
    mi_email = memberDetails.getMi_email();
    
    mi_status = memberDetails.getMi_status();
    if (mi_status.equals("a")) mi_status = "정상 회원";
    if (mi_status.equals("b")) mi_status = "휴면 회원";
    if (mi_status.equals("c")) mi_status = "탈퇴 회원";
    
    ma_addr1 = memberDetails.getMa_addr1();
    ma_addr2 = memberDetails.getMa_addr2();
    mi_date = memberDetails.getMi_date();
    mi_point = memberDetails.getMi_point();
}
	String[] arr = mi_birth.split("-");
	String bt1 = "", bt2 = "", bt3 = "";
	for (int i = 0; i < arr.length; i++){
		bt1 = arr[0];
		bt2 = arr[1];
		bt3 = arr[2];
	}
%>
<tr>
    <td>아이디 : <%=mi_id %></td><td>이름 : <%=mi_name %></td>
    <td>생년월일 : <%=bt1 %>년<%=bt2 %>월<%=bt3 %>일</td>
</tr>
<tr>
    <td>상태 : <%=mi_status %></td><td>보유 포인트 : <%=mi_point %>P</td>
	<td>주소 1 : <%=ma_addr1 %></td>
</tr>
<tr>
	<td>이메일 : <%=mi_email %></td>
    <td>주소 2 : <%=ma_addr2 %></td>
    <td>가입일 : <%=mi_date %></td>
</tr>
</table>
</div>
</body>
</html>