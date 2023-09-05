<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../_inc/inc_head_fr.jsp" %>
<%
request.setCharacterEncoding("utf-8");
OrderCart oc = (OrderCart)request.getAttribute("detailList");
%>
<script src="resources/js/jquery-3.6.4.js"></script>
<script>
function chAddr(val){
	var frm = document.frmOrder;
	var arr = val.split("|");
	var phone = arr[2].split("-");
	frm.ma_name.value =arr[0];	frm.oi_name.value =arr[1];
	frm.p2.value =phone[1];	frm.p3.value =phone[2];
	frm.oi_zip.value =arr[3];	frm.oi_addr1.value =arr[4];
	frm.oi_addr2.value = arr[5];
	
}
</script>
<h2>주문 폼</h2>
<h3>구매할 상품 목록</h3>
<%-- <c:set var="ocidxs" value="${pdtList.get(0).getOc_idx()}" />
<input type="hidden" value="${pdtList.get(0).getPi_price()}" /> --%>
<table width="800" cellpadding="5" >
<c:forEach items="${pdtList}" var="oc" varStatus="i">
<c:set var="ocidxs" value="${ocidxs},${oc.getOc_idx()}" />

<c:set var="total" value="${total+oc.getPi_price()*oc.getOc_cnt()}" />
<tr align="center">
<td width="15%">
	<a href="productView?piid=${oc.getPi_id() }">
	<img src="resources/img/product/${oc.getPi_img1() }" width="100" height="90" />
	</a>
</td>
<td width="25%" align="left">&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="productView?piid=${oc.getPi_id() }">${oc.getPi_name() }</a>
</td>
<td width="20%">${oc.getPs_size() }mm</td>
<td width="15%">${oc.getOc_cnt() }개</td>
<td width="*">${oc.getPi_price()*oc.getOc_cnt() }원</td>
</tr>
</c:forEach>
<tr><td colspan="5" align="right">총 결제 금액: ${total }원</td></tr>
</table>
<hr />
<form name="frmOrder" action="orderIn" method="post">
<!-- od.getPi_id(), od.getPs_idx(), od.getOd_cnt(), 
od.getOd_price(),od.getOd_name(), od.getOd_img(),od.getOd_size()
 -->

<h3>배송지 정보</h3>
<table width="800" cellpadding="5">
<tr>
<th>배송지 정보</th>
<td colspan="3">
	<select style="width:500px;height:25px;" onchange="chAddr(this.value);">
<c:forEach items="${addrList}" var="ma">
<c:if test="${ma.getMa_basic() =='y' }" >
<c:set var="maname" value="${ma.getMa_name()}" /><c:set var="marname" value="${ma.getMa_rname()}" />
<c:set var="maphone" value="${ma.getMa_phone()}" /><c:set var="mazip" value="${ma.getMa_zip()}" />
<c:set var="maaddr1" value="${ma.getMa_addr1()}" /><c:set var="maaddr2" value="${ma.getMa_addr2()}" />

</c:if>
<c:set var="val" value="${ma.getMa_name()}|${ma.getMa_rname()}|${ma.getMa_phone()}|${ma.getMa_zip()}|${ma.getMa_addr1()}|${ma.getMa_addr2()}" />
<c:set var="txt" value="[${ma.getMa_zip()}] ${ma.getMa_addr1()} ${ma.getMa_addr2()}" />
<option value="${val}">${txt}</option>
</c:forEach>
	</select>
</td>
</tr>
<tr>
<th width="15%">주소명</th>
<td width="35%">
	<input type="text" name="ma_name" value="${maname}" readonly="readonly" onfocus="this.blur();" style="border:none;"/>
</td>
<th width="15%">수취인명</th>
<td width="35%">
	<input type="text" name="oi_name" value="${marname}" />
</td>
</tr>
<tr>
<th width="15%">전화번호</th>
<td>
<c:set var="tel" value="${fn:split(maphone,'-')}" />
	010- <input type="text" name="p2" value="${tel[1]}" size="4" maxlength="4" />
	- <input type="text" name="p3" value="${tel[2]}" size="4" maxlength="4" />
</td>
<th>우편번호</th>
<td>
	<input type="text" name="oi_zip" value="${mazip}" size="5" maxlength="5" />
</td>
</tr>
<tr>
<th>주소</th>
<td colspan="3">
	<input type="text" name="oi_addr1" value="${maaddr1 }" size="40"/>
	<input type="text" name="oi_addr2" value="${maaddr2 }" size="40"/>
</td>
</tr>
</table>
<hr>
<h3>결제 정보</h3>
<p style="width:800px;">
	<input type="radio" name="oi_payment" value="a" id="payA" checked=u"checked"/>
	<label for="payA">카드 결제</label>&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="radio" name="oi_payment" value="b" id="payB" />
	<label for="payB">휴대폰 결제</label>&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="radio" name="oi_payment" value="b" id="payB" />
	<label for="payB">계좌 이체</label>&nbsp;&nbsp;&nbsp;&nbsp;
</p>
<hr>
<p style="width:800px; text-align:center">
	<input type="submit" value="결제 하기" />
</p>
<input type="hidden" name="piid" value="<%=oc.getPi_id()%>"/>
<input type="hidden" name="cnt" value="<%=oc.getOc_cnt()%>"/>
<input type="hidden" name="pssize" value="<%=oc.getPs_size()%>"/>

<input type="hidden" name="kind" value="${kind }"/>
<input type="hidden" name="ocidxs" value="${ocidxs }"/>
<input type="hidden" name="total" value="${total }"/>
</form>
<br><br>
<%@ include file="../_inc/inc_foot_fr.jsp" %>