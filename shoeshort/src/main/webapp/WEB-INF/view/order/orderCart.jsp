<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head_fr.jsp" %>
<%@ include file="../_inc/inc_mypage.jsp" %>
<style>
#list td{ border-bottom:1px solid black; }
</style>
<!-- ajax를 이용할려면 jquery를 넣어야 한다. -->
<script src="resources/js/jquery-3.6.4.js"></script>
<script>
function chkAll(all){
	//전체 선택 체크박스 클릭시 모든 체크박스에 대한 체크 여부를 처리하는 함수
		var frmCart = document.frmCart.value;
		var arr = document.frmCart.chk;	
		for(var i =1; i<arr.length; i++)	arr[i].checked = all.checked;

	}
	function chkOne(one){
	// 특정 체크박스 클릭시 체크 여부에 따른 '전체 선택' 체크박스의 체크 여부를 처리하는 함수
		var frm = document.frmCart;
		var all =frm.all;
		if(one.checked){		//특정 체크박스를 체크했을 경우
			var arr = frm.chk;
			var isChk = true;
			for(var i =1; i<arr.length; i++){
				if(arr[i].checked == false){
					isChk = false;		break;
				}
			}  
			all.checked =isChk;
		} else {				//특정 체크박스를 해제했을 경우
			all.checked =false;
		}
	}
	function cartUp(ocidx, opt, cnt){
	// 장바구니내 특정 상품의 옵션 및 수량을 변경하는 함수
	// cnt가 0이면 옵션 변경이고, opt가 0이면 수량 변경을 의미
		$.ajax({
			type: "POST", url: "cartUpCnt",
			data: {"ocidx":ocidx, "opt":opt, "cnt":cnt},
			success: function(chkRs){
				if(chkRs ==0){
					alert("상품 변경에 실패했습니다.\n다시 시도하세요.");
				}
				location.reload();
			}
		});		
	}

	function cartDel(ocidx){	
	//장바구니내 특정 상품을 삭제하는 함수
		if(confirm("정말 삭제하시겠습니까?")){
			$.ajax({
				type: "POST", url: "cartDel",
				data:{"ocidx": ocidx},
				success : function(chkRs){
					alert(chkRs);
					if(chkRs == 0){					
						alert("상품 삭제에 실패했습니다.\n다시 시도하세요.");
					}
					else{
						alert("상품이 삭제되었습니다.");
					}
					location.reload(); 
				}		
			});
		}	
	}
	function getSelectedChk(){
	// 체크박스들 중 선택된 체크박스들의 값(value)들을 쉼표로 구분하여 문자열로 리턴하는 함수
		var chk =document.frmCart.chk;
		var idxs ="";	// chk컨트롤 배열에서 선택된 체크박스의 값들을 누적 저장할 변수
		for(var i= 1; i<chk.length ; i++){
			if(chk[i].checked)	idxs +=","+chk[i].value;
		}
		return idxs.substring(1);
	}
	function chkDel(){
	//사용자가 선택한 상품(들)을 삭제하는 함수
		var ocidx =getSelectedChk();
		//선택한 체크박스의  oc_idx 값들이 쉼표를 기준으로 '1,2,3,4' 형태의 문자열로 저장됨
		if(ocidx == "")	alert("삭제할 상품을 선택하세요.");
		else			cartDel(ocidx);
	}
	function chkBuy(oc){
		var ocidx = oc;
		if(oc == ""){
			ocidx = getSelectedChk();
			if(ocidx == "")	alert("구매할 상품을 선택하세요.");
			else			document.frmCart.submit();
		}else{
			document.frmCart.oc.value =oc;	
			document.frmCart.submit();
		}
		
		
	}
	function allBuy(){
	// 전체 상품 구매를 처리하는 함수
		var arr = document.frmCart.chk;	
		for(var i =1; i<arr.length; i++)	arr[i].checked = true;
		document.frmCart.submit();
	}
</script><br>&nbsp;
<div align="center">
<div style="width:100%">
<h2>장바구니</h2>
<form name="frmCart" action="orderForm" method="POST">
<c:if test="${not empty cartList}">
<input type="hidden" name="chk"/>
<input type="hidden" name="kind" value="c" />
<input type="hidden" name="oc" value="" />
<div align="left" style="padding-left:10px;"><input type="checkbox" name="all" id="all" checked="checked" onclick="chkAll(this)" />
<label for="all">전체 선택</label></div>
<hr>
<table width='100%' cellpadding='5' cellspacing='0' id='list'>
	<c:forEach items="${cartList}" var="oc">
<tr align="center" onmouseover="this.bgColor='#efefef';" onmouseout="this.bgColor='';">
<td width="5%">
	<input type="checkbox" name="chk" value="${oc.getOc_idx() }"checked="checked" onclick="chkOne(this)"/>
</td>
<td width="15%">
	<a href="productView?piid=${oc.getPi_id() }"><img src="resources/img/product/${oc.getPi_img1() }" width="100" height="90" /></a>
</td>
<td width="20%">
	<a href="productView?piid=${oc.getPi_id() }">${oc.getPi_name() }</a>
</td>
<td width="20%">
	<select name="size" onchange="cartUp('${oc.getOc_idx() }', this.value, 0);">
	<c:forEach items="${oc.getStockList() }" var="ps">
	<c:if test="${ps.getPs_stock() > 10}"><c:set var="max" value="10" /></c:if>
	<c:if test="${ps.getPs_stock() <= 10}"><c:set var="max" value="${ps.getPs_stock() }" /></c:if>
	<option value="${ps.getPs_idx() }" 
		<c:if test="${ps.getPs_idx() == oc.getPs_idx() }">selected='selected'</c:if>>${ps.getPs_size()}mm (재고 : ${max}개)
	</option>
	</c:forEach>
	</select>
</td>
<td width="15%">
	<select name="cnt" onchange="cartUp(${oc.getOc_idx() }, 0, this.value);">
	<c:forEach var="i" begin="1" end="${max }">
		<option value="${i}"<c:if test="${oc.getOc_cnt() == i }">selected='selected'</c:if>>${i}</option>
	</c:forEach>
	</select>
</td>
<td width="15%">
<fmt:formatNumber value="${oc.getPi_price()*oc.getOc_cnt()}" pattern="#,###원"/>
</td>
<td width="*">
	<input type="button" class="btn btn-outline-secondary" value="바로구매" onclick="chkBuy(${oc.getOc_idx()})" />
	<p style="margin-top:10px;">
	<input type="button" class="btn btn-outline-secondary" value="삭제" onclick="cartDel('${oc.getOc_idx()}')" />	
</td>
</tr>
<c:set var="total" value="${total+oc.getPi_price()*oc.getOc_cnt()}" />
</c:forEach>
</table>
<p align="right" style="width:100%; font-size:1.8em;">총 결제 금액: <strong><fmt:formatNumber value="${total}" pattern="#,###"/></strong>원</p>
<p align="center" style="width:100%; font-size:1.3em;">
	<input type="button" class="btn btn-secondary" value="선택 삭제" onclick="chkDel()" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="button" class="btn btn-secondary" value="계속 쇼핑하기" onclick="location.href='/shoeshort/'" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="button" class="btn btn-secondary" value="구매하기" onclick="chkBuy('')" />
</p>
</c:if>
<c:if test="${empty cartList}">
	<p align="right" style="width:80%; text-align:center;">장바구니에 담긴 상품이 없습니다.</p>
</c:if>
<hr width="100%">

</form>
</div>
</div>
<%@ include file="../_inc/inc_foot_fr.jsp" %>