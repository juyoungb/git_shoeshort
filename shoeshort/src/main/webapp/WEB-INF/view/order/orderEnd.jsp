<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../_inc/inc_head_fr.jsp" %>
<%@ include file="../_inc/inc_mypage.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
table{
	width:100%;
	border: 1px solid #444444;
}
table th,td{
	border: 1px solid #444444;
	text-align:left;

</style>
</head>
<body>
<div style="width:1000px;" align="center">
<h2>주문 확인</h2>
<table class="table">
<tr>
<th>주문자 성함</th>
<td>${mi.getMi_name()}</td>
<th>연락처</th>
<td>${mi.getMi_email()}</td>
</tr>
<tr>
<th>주문일자</th>
<td>${oi.getOi_date()}</td>
<th>결제방법</th>
<!-- a: 카드결제 b: 휴대폰결제 c: 무통장입금 -->
<td>${oi.getOi_payment() == "a" ? "카드결제" : (oi.getOi_payment() == "b" ? "휴대폰결제" : "계좌이체")}</td>
</table><br>
<table class="table">
<tr><th width="15%">받으시는분</th><td>${oi.getOi_name()}</td></tr>
<tr><th>연락처</th><td>${oi.getOi_phone()}</td></tr>
<tr><th>주소</th><td>[${oi.getOi_zip()}]${oi.getOi_addr1()}(${oi.getOi_addr2()})</td></tr>
<tr><th>배송 유의사항</th><td>${oi.getOi_memo() == "" ? "" : oi.getOi_memo() }</td></tr>
</table><br>
<table class="table">
<tr><th>상품코드</th><th>상품이름</th><th>수량</th><th>판매가</th><th>적립금</th></tr>
<c:forEach items="${oi.getDetailList()}" var="dl">
<tr><td>${dl.getPi_id() }</td>
<td>${dl.getOd_name() } (사이즈:${dl.getOd_size() })</td>
<td>${dl.getOd_cnt() }</td>
<td>${dl.getOd_price()*dl.getOd_cnt() }</td>
<td style="vertical-align: middle;"><fmt:formatNumber value="${dl.getOd_price() * dl.getOd_cnt() * 0.002}" pattern="#,###P"/>
</tr>
</c:forEach>
</table>
</div>
</body>
</html>