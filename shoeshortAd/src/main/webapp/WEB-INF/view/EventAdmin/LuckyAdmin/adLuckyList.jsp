<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../../_inc/inc_head_ad.jsp" %>
<%@ page import="java.util.*" %>
<style>
table{
	border: 1px solid #121212;
	width:80%;
}
table th,td{
border: 1px solid #121212;
text-align: center;
}
#wrap{
	border-radius: 100px;
	border:none;
	background: white;
	margin: 20px;
  	padding: 10px;
  	width:90%;
  	heith:90%;
  	text-align:center;
}
</style>
<script src="resources/js/popUpJs.js"></script>
<script src="resources/js/jquery-3.6.4.js"></script>
<script>
function adluckyDel(elidx){	
		if(confirm("정말 삭제하시겠습니까? \r\n미게시로 변경하시려면 수정버튼을 클릭해 주세요.")){
			$.ajax({
				type: "POST", url: "adLuckyDel",
				data:{"elidx": elidx},
				success : function(chkRs){
					if(chkRs == 0){					
						alert("삭제에 실패했습니다.\n다시 시도하세요.");
					}
					else{
						alert("목록이 삭제되었습니다.");
					}
					location.reload(); 
				}		
			});
		}	
	}
</script>
<div id="wrap">
<div align="center">
<h2>행운의 숫자 찾기</h2>
<div>
<table  cellpadding="5" align="cener">
<tr>
<th width="10%">번호</th>
<th width="*">제목</th>
<th width="*">상품 이미지</th>
<th width="15%">응모기간</th>
<th width="10%">당첨자</th>
<th width="10%">당첨금액</th>
<th width="10%">상태</th>
<th width="10%">설정</th>
</tr>	
 
<c:forEach items="${luckyList}" var="il" varStatus="i">
<tr>
<td>${i.count}</td>
<td>${il.getEl_title()}</td>
<td><img width="200px" height="100px" style="cursor: pointer" src="resources/img/product/${il.getPi_img1()}"/></td>
<td>  <br> (${il.getEl_sdate()} ~ ${il.getEl_edate()})</td>
<td>${il.getMi_id() == '' ? '': il.getMi_id()}</td>
<td>${il.getEl_final_price() }</td>
<td>${il.getEl_isview() == 'y'? '진행중': (il.getEl_isview() == 'n' ? '미게시' : '종료')}</td>
<td><input type="button" class="btn btn-secondary" onclick="popUp('adLuckyIn?kind=up&elidx=${il.getEl_idx()}','','700','440');"  value="수정"/><br>
<input type="button" class="btn btn-outline-secondary" onclick="adluckyDel('${il.getEl_idx()}')" value="삭제"/></td> 
</tr>
</c:forEach>

</table>
</div>
<table style="border:none;" cellpadding="10">
<tr>
<td style="text-align:right; border:none;">
&nbsp;&nbsp;<input style="width:8%" class="btn btn-dark" type="button" onclick="popUp('adLuckyIn?kind=in','','700','440');" value="등록"/></td>
</tr>
</table>
<nav aria-label="Page navigation example">
  <ul class="pagination justify-content-center">
    <li class="page-item"><a class="page-link" href="#">이전</a></li>
    <li class="page-item"><a class="page-link" href="#">1</a></li>
    <li class="page-item"><a class="page-link" href="#">2</a></li>
    <li class="page-item"><a class="page-link" href="#">3</a></li>
    <li class="page-item"><a class="page-link" href="#">다음</a></li>
  </ul>
</nav>
</div> 
<%@ include file="../../_inc/inc_foot_ad.jsp" %>