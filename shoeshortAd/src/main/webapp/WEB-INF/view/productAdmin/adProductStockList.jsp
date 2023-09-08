<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
#List th,tr,td{border:1px solid black; align:"center"}
span {color:blue}
</style>
</head>
<body>
<h2 align ="center">상품명 : <span>${piid}</span></h2>
<table width="450" class="list" align="center" cellpadding="0" cellspacing="0">
<tr>
<th>사이즈</th>
<th>재고량</th>
</tr>

<c:forEach var="sl" items="${stockList}">
<tr align="center">
	<td>${sl.getPs_size()}</td>
	<td>${sl.getPs_stock()}</td>
</tr>
</c:forEach>

</table>
<<<<<<< HEAD
=======
<table width="450px" cellpadding="0" cellspacing="0" align="center" style="border:none">
<tr align="center">
	<td><input type="button" class="btn btn-dark" value="수정" onclick="makeSch()"><input type="button" value="닫기"  onClick='window.close()'></td>
</tr>
</table>
</form>
>>>>>>> origin/main
</body>
</html>