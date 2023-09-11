<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head_fr.jsp" %>
<%@ include file="../_inc/inc_mypage.jsp" %>

<style>
.orderbtn1{
 background-color: #333; 
 color: white;
 border: none; 
 border-radius: 5px; 
 padding: 8px 20px; 
 cursor: pointer; 
 margin-left: 10px;
}
</style>
<script src="resources/js/jquery-3.6.4.js"></script>
<script>
function cart(index) {	
	var frm = document.getElementById("frm" + index);

	var piid = frm.piid.value;
	var size = frm.size.value;
	var cnt = frm.cnt.value;
	
	console.log("piid: " + piid);
	console.log("size: " + size);
	console.log("cnt: " + cnt);
	$.ajax({
		type :"POST" , url:"cartProcIn",
		data:{"piid" : piid, "psidx" : size, "cnt": cnt},
		success :function(chkRs){//장바구니 담기에 실패했을 경우
			if(chkRs == 0){
				alert("장바구니 담기에 실패하였습니다.\n다시 시도해 주세요.");
				return;
		} else { //장바구니 담기에 성공했을 경우
			if(confirm("장바구니에 담았습니다.\n장바구니로 이동하시겠습니까?")){
				location.href="orderCart";
			}
		}
	}
  }); 

}	
</script>
<h2 style="text-align: center; margin-top: 30px;">주문내역</h2>
<div style="margin: 20px auto; width: 1000px;">
  <table style="width: 100%; border-collapse: collapse;">
<c:if test="${not empty orderList}">
    <c:forEach items="${orderList}" var="ol" varStatus="i">
    <c:if test="${i.count == 0}"><c:set var="pdate" value="${fn:substring(ol.getOi_date(),0,10)}"/></c:if>    
    <c:set var="date" value="${fn:substring(ol.getOi_date(),0,10)}"/>
     <c:if test="${date != pdate }">
      <tr bgcolor="#f4f4f4">
        <c:set var="pdate" value="${fn:substring(ol.getOi_date(),0,10)}"/>
        <td colspan="4" style="padding: 10px; border: 1px solid #ccc; text-align: left; font-weight:bold;">${pdate}</td>
      </tr>
     </c:if> 
    <tr>
   <th colspan="3" style="padding: 10px; border: 1px solid #ccc; text-align: left;  border-right: none;">
      ${ol.getOi_status() == "d" ? "배송완료": ol.getOi_status() == "c" ? "배송중": "배송대기"}
   </th>
   <td style="text-align: right; border: 1px solid #ccc;  border-left: none;">
<%--    <fmt:parseNumber value="${ol.getDetailList().get(i.index).getOd_cnt()}" var="cnt"/> --%>
       <span style="cursor: pointer" onclick="popUp('orderDetail?ol=${ol.getOi_id()}','','1000','800');" style="margin-right: 10px;">주문상세</span>
       &nbsp;&nbsp;
   </td>
</tr>
      <c:forEach items="${ol.getDetailList()}" var="dl" varStatus="j">
      <form id="frm${i.count}" method="post">
      <input type="hidden" name="piid" value="${dl.getPi_id()}" />
      <input type="hidden" name="size" value="${dl.getOd_size()}" />
      <input type="hidden" name="cnt" value="${dl.getOd_cnt()}" />
      </form>
        <tr style="border: 1px solid #ccc;">
			<td width="15%" align="left"  >
    			<img src="resources/img/product/${dl.getOd_img()}" style="display: block; width: 80%; margin: 2px auto; cursor: pointer; " 
    			onclick="location.href='productView?piid=${dl.getPi_id()}';"/>
			</td>
			<td align="left">   ${dl.getOd_name()} - 사이즈 ${dl.getOd_size()}<br>
			<fmt:formatNumber value="${dl.getOd_price()*dl.getOd_cnt()}" pattern="#,###원"/>
			 - ${dl.getOd_cnt()}개&nbsp;&nbsp;
			<input type="button" class="btn btn-secondary" onclick="cart('${j.count}');" value="장바구니 담기"  />	
			</td>
			<c:if test="${ol.getOi_status() =='d'}">
			<td colspan="2" align="right">
			 <input type="button" onclick="location.href='styleForm?piid=${dl.getPi_id()}';" value="+ 스타일" class="orderbtn1">
			 &nbsp;&nbsp;&nbsp;
			</td>
			</c:if>
        </tr>
		</c:forEach>
	</c:forEach>
  </c:if>
  <c:if test="${empty orderList}">
  <tr><td>주문 내역이 없습니다.</td></tr>
  </c:if>
  </table>
</div>

<%@ include file="../_inc/inc_foot_fr.jsp" %>
</body>
</html>

