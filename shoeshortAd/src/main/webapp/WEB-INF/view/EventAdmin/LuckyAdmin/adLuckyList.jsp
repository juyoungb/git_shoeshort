<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../../_inc/inc_head_ad.jsp" %>
<%@ page import="java.util.*" %>
<%
PageInfo pageInfo = (PageInfo)request.getAttribute("pageInfo");
int rcnt = pageInfo.getRcnt(), cpage = pageInfo.getCpage(), bsize = pageInfo.getBsize(), pcnt = pageInfo.getPcnt(), count = pageInfo.getCount();

%>
<style>
#list th, #list td { padding:8px 3px; }
#list th { border:solid  black 1px; }
#list td { border:solid black 1px; }
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
thead {
  background: #dcdcdc;
  text-align: center;
}
.page-link {
  color: #000; 
  background-color: #fff;
  border: 1px solid #ccc; 
}

.page-item.active .page-link {
 z-index: 1;
 color: #555;
 font-weight:bold;
 background-color: #f1f1f1;
 border-color: #ccc;
 
}

.page-link:focus, .page-link:hover {
  color: #000;
  background-color: #fafafa; 
  border-color: #ccc;
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

<table cellpadding="5" width="80%" align="cener" id="list">
 <thead>
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
 <thead>
 
  <tbody>
<c:forEach items="${luckyList}" var="il" varStatus="i">
<tr>
<td align="center">${i.count}</td>
<td align="left">${il.getEl_title()}</td>
<td align="center"><img width="200px" height="100px" style="cursor: pointer" src="resources/img/product/${il.getPi_img1()}"/></td>
<td><br> (${il.getEl_sdate()} ~ ${il.getEl_edate()})</td>
<td align="center">${il.getMi_id() == '' ? '': il.getMi_id()}</td>
<td align="center">${il.getEl_final_price() }</td>
<td align="center">${il.getEl_isview() == 'y'? '진행중': (il.getEl_isview() == 'n' ? '미게시' : '종료')}</td>
<td align="center"><input type="button" class="btn btn-outline-dark" onclick="popUp('adLuckyIn?kind=up&elidx=${il.getEl_idx()}','','700','440');"  value="수정"/><br>
<input type="button" class="btn btn-outline-danger" onclick="adluckyDel('${il.getEl_idx()}')" value="삭제"/></td> 
</tr>
</c:forEach>
<tr>
<td  colspan="8" style="text-align:right; border:none;">
<input class="btn btn-dark" type="button" onclick="popUp('adLuckyIn?kind=in','','700','440');" value="등록"/></td>
</tr>
 <tbody>
</table>




<table align="center" width="80%" cellpadding="5" >
<tr>
<td align="center">
<%
if(rcnt > 0 ) {
	String lnk = "adLuckyList?1=1&sch="+pageInfo.getSchargs()+"cpage=";

%>

	<nav aria-label="Page navigation example">
  	<ul class="pagination justify-content-center">
<%

	if(cpage == 1) {
%>  
		<li class="page-item"><a class="page-link" href="#">이전</a></li>
<%
	} else {
%>		
		<li class="page-item"><a class="page-link" href="<%=lnk + (cpage - 1)%>">이전</a></li>
<%
	}
	int spage = (cpage - 1) / bsize * bsize + 1; // 시작할 페이지
	int j = 0;

	for (count = 1, j = spage ; count<= bsize && j <= pcnt ; count++, j++){
	// i : 블록에서 보여줄 페이지의 개수만큼 루프를 돌릴 조건으로 사용될 변수
	//j: 실제 출력할 페이지 번호로 전체 페이지 개수 (마지막 페이지 번호)를 넘지 않게 사용해야 함
		if(cpage == j ) { 
%>		
			<li class="page-item"><a class="page-link" href="#"><%=j %></a></li>
<%
		} else {
%>			<li class="page-item"><a class="page-link" href="<%=lnk + j%>"><%=j %></a></li> <%
	
		}
	}
	if (cpage == pcnt){
%>
			<li class="page-item"><a class="page-link" href="#">다음</a></li>
<%
	} else{ 
%>
			<li class="page-item"><a class="page-link" href="<%=lnk +(cpage + 1) %>">다음</a></li>
<%
	}
%>
		</ul>
	</nav>
<%
}
%>  
</td>
</tr> 
</table>
</div>
</div>
<%@ include file="../../_inc/inc_foot_ad.jsp" %>