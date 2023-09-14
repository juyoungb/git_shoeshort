<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%
MemberInfo memberPoint = (MemberInfo)request.getAttribute("memberPoint");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
<script>
function chkPoint(){
	var frm = document.frm;
	if (frm.mp_detail.value == "" || frm.point.value <= 0){
		alert("입력된 값을 확인해주세요.");
		return false;
	} else {
		frm.submit();
	}
}
</script>
</head>
<body>
<h2>포인트 적립</h2>
<br />
<div align="center">
<form name="frm" action="givePoint">
<input type="hidden" name="miid" value="<%=memberPoint.getMi_id() %>" />
<!-- 관리자 직권 빼고는 자동으로 포인트가 적립되지만 다른 곳에서 포인트 적립할 때 
 오류가 발생해서 적립이 안될 시 관리자가 직접 선택해서 포인트 적립을 할 수 있게 하기위한 기능 추가 -->
<div align="left">
<select name="mp_desc">
	<option value="a" selected="selected" >가입축하금</option>
	<option value="b" >상품 구매</option>
	<option value="c" >게시글 작성</option>
	<option value="d" >관리자 직권</option>
</select>
</div>
<table style="width: 90%;">
<tr><td>포인트 적립 내용 : </td>
<td align="left"><input type="text" id="mp_detail" name="mp_detail" value="" class="form-control" /></td></tr>
<tr><td>현재 포인트 : </td>
<td align="left"><input type="text" id="oldPoint" name="oldPoint" value="<%=memberPoint.getMi_point() %>" class="form-control" /></td><td style="font-size:20px;">P</td></tr>
<tr><td>적립 포인트 : </td>
<td align="left"><input type="text" id="point" name="point" value="0" class="form-control" /></td><td style="font-size:20px;">P</td></tr>
<tr><td align="right" colspan="3"><input type="button" onclick="chkPoint();" value="적립" class="btn btn-outline-dark d-inline-flex align-items-center" /></td></tr>
</table>
</form>
</div>
</body>
</html>