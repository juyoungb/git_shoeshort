<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%
request.setCharacterEncoding("utf-8");
String kind = request.getParameter("kind");
String uid = request.getParameter("u");
String maidx = request.getParameter("n");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="resources/css/addrStyle.css">
<script src="resources/js/memInfoJs.js"></script>
</head>
<body>
<form id="frm" action="addrSet" method="POST">
<input type="hidden" name="kind" value="<%=kind %>"/>
<input type="hidden" name="u" value="<%=uid%>"/>
<input type="hidden" name="n" value="<%=maidx%>"/>
<h3 align="center">배송지 <%=kind.equals("in") ? "추가":"수정" %></h3>
<div align="center">
    <table>
      <tr>
        <th>이름</th>
        <td><input type="text" name="name" style="width: 100%;" value="${ma.getMa_name() }"></td>
      </tr>
      <tr>
        <th>수취인</th>
        <td><input type="text" name="rname" style="width: 100%;" value="${ma.getMa_rname() }"></td>
      </tr>
      <tr>
        <th>전화번호</th>
        <td>
          <input type="text" name="p1" size="3" maxlength="3" value="${ma.getMa_phone().split("-")[0]}"  > - 
          <input type="text" name="p2" size="4" maxlength="4" value="${ma.getMa_phone().split("-")[1]}"> - 
          <input type="text" name="p3" size="4" maxlength="4" value="${ma.getMa_phone().split("-")[2]}">
        </td>
      </tr>
      <tr>
        <th>우편번호</th>
        <td class="address-container">
          <input type="text" id="zipcode" name="zip" value="${ma.getMa_zip() }" readonly>&nbsp;
          <input type="button" id="select-address" name="address" value="우편 검색">
        </td>
      </tr>
      <tr>
        <th>주소</th>
        <td>
          <input type="text" id="address" name="addr1" value="${ma.getMa_addr1() }" readonly>
        </td>
      </tr>
      <tr>
        <th>상세 주소</th>
        <td><input type="text" name="addr2" value="${ma.getMa_addr2() }"></td>
      </tr>
      <tr>
        <td colspan="2"><input type="checkbox" id="chkVal" name="chkVal" <c:if test="${ma.getMa_basic() == 'y'}">checked="checked"</c:if>> <span>기본 주소 설정</span></td>
      </tr>
      <tr>
        <td colspan="2" align="center" style="border: none">
          <input type="button" value="취소" onclick="window.close();">
          <input type="submit" value="<%=kind.equals("in") ? "저장":"수정" %>하기">
    
        </td>
      </tr>
    </table>
  </div>
</form>
</body>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
window.onload = function(){
    document.getElementById("select-address").addEventListener("click", function(){ //주소입력칸을 클릭하면
        //카카오 지도 발생
        new daum.Postcode({
            oncomplete: function(data) { //선택시 입력값 세팅
            	document.getElementById("zipcode").value = data.zonecode;
                document.getElementById("address").value = data.address; // 주소 넣기
                document.querySelector("input[name=addr2]").focus(); //상세입력 포커싱
            }
        }).open();
    });
}
</script>	
</body>
</html>
