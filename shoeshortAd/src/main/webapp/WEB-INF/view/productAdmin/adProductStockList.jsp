<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>

function makeSch() {
	var frm = document.frm2;
	var sch = "";
		
	 	
	 var sarr = frm.size; // size라는 이름의 컨트롤들을 배열로 받아옴
	 var isFrist = true;   //size 체크박스들 중 첫번째로 선택한 체크박스인지 여부를 저장
	 for(var i = 0 ; i < sarr.length ; i++){
		if (sarr[i].value) {    
		//alert("sarr[i].value : " + sarr[i].value);
	    	if (isFrist){ 
	        	if (sch != "") sch += ",";   
	         	isFrist = false;         
	         	sch += "s" + sarr[i].id + " " + sarr[i].value;
	      	} else {
	        	sch += ":" + sarr[i].id + " " + sarr[i].value;
	      	}
	    }
	 }   
	 

	 document.frm2.sch.value = sch;   
	 document.frm2.submit();
}

</script>
<style>
#List th,tr,td{border:1px solid black; align:"center"}
span {color:blue}
</style>
</head>
<body>

<form name="frm2" action="stockUpdate" method="post" enctype="multipart/form-data">
<input type="hidden" name="sch" value=""> 
<input type="hidden" name="piid" value="${piid}"> 
<h2 align ="center">상품명 : <span>${piid}</span></h2>
<table width="450px" class="list" align="center" cellpadding="0" cellspacing="0">
<tr align="center">
<th>사이즈</th>
<th>재고량</th>
</tr>

<c:forEach var="sl" items="${stockList}">
<tr align="center">
	<td width="50%">${sl.getPs_size()}</td>
	<td width="50%"><input type="number" name="size" value="${sl.getPs_stock()}" width="100%"></td>
</tr>
</c:forEach>
</table>
<table width="450px" cellpadding="0" cellspacing="0" align="center" style="border:none">
<tr align="center">
	<td><input type="button" class="btn btn-dark" value="수정" onclick="makeSch()"><input type="button" value="닫기"  onClick='window.close()'></td>
</tr>
</table>
</form>
</body>
</html>