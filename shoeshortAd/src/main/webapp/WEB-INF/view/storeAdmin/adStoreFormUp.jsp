<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%
StoreInfo storeInfo = (StoreInfo)request.getAttribute("storeInfo");
int bsiidx = Integer.parseInt(request.getParameter("bsiidx"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
<script>
function chkTxt(){
var frm = document.frm;
	if (frm.brand.value == "" || frm.name.value == "" || frm.addr.value == ""){
		alert("입력칸을 확인해주세요.");
		return false;
	} else  {
		return frm.submit();
	}
}
</script>
<style>
td {padding-bottom: 10px;}
</style>
</head>
<body>
<h2>매장 정보 수정</h2>
<br />
<div align="center">
<form name="frm" action="adStoreUp" >
<input type="hidden" name="bsiidx" value="<%=bsiidx %>" />
<!-- 관리자 직권 빼고는 자동으로 포인트가 적립되지만 다른 곳에서 포인트 적립할 때 
 오류가 발생해서 적립이 안될 시 관리자가 직접 선택해서 포인트 적립을 할 수 있게 하기위한 기능 추가 -->
<div align="left"  style="margin-left:40px;">
<select name="isview">
	<option value="y" selected="selected" >노출</option>
	<option value="n" >미 노출</option>
</select>
</div>
<table style="width: 90%;">
<tr><td style="font-size:20px;">브랜드 : </td>
<td align="left"><input type="text" name="brand" value="<%=storeInfo.getBsi_brand() %>" class="form-control" /></td>
<tr><td style="font-size:20px;">매장명 : </td>
<td align="left"><input type="text" name="name" value="<%=storeInfo.getBsi_name() %>" class="form-control" /></td>
<tr><td style="font-size:20px;">주소 : </td>
<td align="left"><input type="text" name="addr" value="<%=storeInfo.getBsi_addr() %>" class="form-control" /></td>
<tr><td align="right" colspan="3"><input type="button" onclick="chkTxt();" value="수정" class="btn btn-dark d-inline-flex align-items-center" /></td></tr>
</table>
</form>
</div>
</body>
</html>



