<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>

<style>

table{
	width:90%;
	border: 1px solid #444444;
}
table th,td{
	border: 1px solid #444444;
	text-align:left;
}
</style>
</head>
<body>

<div align="center">
<br>
<h2>주문 상세 (주문번호:<strong>${oi.getOi_id() }</strong>)</h2>
<table class="table table-striped table-hover" style="width:90%">
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

</table><br><br>
<table class="table table-striped table-hover" style="width:90%">
<tr><th>수령자</th><td>${oi.getOi_name()}</td></tr>
<tr><th>연락처</th><td>${oi.getOi_phone()}</td></tr>
<tr><th>주소</th><td>[${oi.getOi_zip()}]${oi.getOi_addr1()}(${oi.getOi_addr2()})</td></tr>
<tr><th>배송 유의사항</th><td>${oi.getOi_memo() == "" ? "" : oi.getOi_memo() }</td></tr>
</table><br><br>
<table class="table table-striped table-hover" style="width:90%">
<tr><th>상품 이미지</th><th>상품이름</th><th>수량</th><th>판매가</th><th>적립금</th></tr>
<c:forEach items="${oi.getDetailList()}" var="dl">
    <c:set var="tpoint" value="${tpoint + dl.getOd_price() * dl.getOd_cnt() * 0.002}"/>
    <c:set var="tprice" value="${tprice + dl.getOd_price() * dl.getOd_cnt()}"/>
    <tr>
        <td width="150px;"><img src="resources/img/product/${dl.getOd_img()}" style="display: block; width: 100%; " 
    			/></td>
        <td style="vertical-align: middle;">${dl.getOd_name()} (사이즈:${dl.getOd_size()})</td>
        <td style="vertical-align: middle;">${dl.getOd_cnt()}</td>
        <td style="vertical-align: middle;"><fmt:formatNumber value="${dl.getOd_price() * dl.getOd_cnt()}" pattern="#,###원"/></td>
        <td style="vertical-align: middle;"><fmt:formatNumber value="${dl.getOd_price() * dl.getOd_cnt() * 0.002}" pattern="#,###P"/></td>
    </tr>
</c:forEach>
</table>
<table style=" border-collapse:collapse; border:none;font-size:24px;" ><tr>
<th width="85%" style="text-align:right; border:none;">총 적립 포인트</th><td style="text-align:right;border:none;"><fmt:formatNumber value="${tpoint}" pattern="#,###P"/></td>
</tr><tr>
<th width="85%" style="text-align:right; border:none;">총 결제 금액</th><td style="text-align:right;border:none;"><fmt:formatNumber value="${tprice}" pattern="#,###원"/></td>
</tr>
</table>
<table style="border: 0;">
<br>
<tr style="border: 0;">
<td style="border: 0; text-align:right" width="85%">
<input type="button" class="btn btn-outline-dark" onclick="window.close();" value="나가기" />
</td>
</tr>
</table>
</div>
</body>
</html>