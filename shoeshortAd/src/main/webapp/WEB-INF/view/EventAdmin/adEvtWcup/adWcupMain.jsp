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

<div id="wrap">
<div align="center">
<h2>스타일 월드컵</h2>
<div>
<table cellpadding="0" cellspacing="0" id="list">
<tr>
<th width="10%">번호</th><th width="*">제목</th><th width="*">모집기간/투표기간</th><th width="15%">상태</th><th width="10%">
설정</th>
</tr>	
<!-- 번호, 제목, 모집기간/투표기간, 상태, 설정 -->
<c:forEach items="${wcupList}" var="wc" varStatus="i">
<c:set var="cdate" value="${fn:substring(wc.getEw_csdate(),0,10)} ~ ${fn:substring(wc.getEw_cedate(),0,10)}"/>
<c:set var="edate" value="${fn:substring(wc.getEw_vsdate(),0,10)} ~ ${fn:substring(wc.getEw_vedate(),0,10)}"/>
<tr>
<!-- ew_csdate, ew_cedate,  ew_vsdate, ew_vedate, ew_status -->
<td>${i.count }</td><td>${wc.getEw_title() }</td>
<td>${wc.getEw_status() =='a' ? cdate: edate}</td>
<td>${wc.getEw_status() == 'a'? '모집중': (wc.getEw_status() == 'b' ? '투표중' : '마감')}</td>
<td style="padding: 15px; margin: 10px;"><input type="button" class="btn btn-outline-dark btn-sm" style="width:90%" onclick="popUp('adWcupIn?kind=up&ewidx=${wc.getEw_idx()}','','700','440');"  value="수정"/><br>
<input style="width:90%" type="button" class="btn btn-outline-danger btn-sm" onclick="wcupDel('${wc.getEw_idx()}')" value="삭제"/></td>
</tr>
</c:forEach>
</table>
<span style="line-height:50%"><br></span><!-- <br> 절반 -->
<table style="border:none;">
<tr><td style="text-align:right; border:none;">
&nbsp;&nbsp;<input style="width:8%" type="button" class="btn btn-dark"onclick="popUp('adWcupIn?kind=in','','700','440');" value="등록"/></td>
</td>
</tr>
</table>
</div>

<br>
<div align="center"><h3>스타일 월드컵 RANKING</h3></div>
<table width="1000"  cellpadding="0" cellspacing="0" id="list">
<tr>
<th width="15%">순위</th><th width="30%">이미지</th><th width="30%">아이디</th><th width="15%">우승비율<br>(우승 횟수/전체 게임 수)
</th>
</tr>
<c:forEach items="${wcupList.get(0).getWcupDetail() }" var="wd" varStatus="i">
<c:set var="late" value="${wd.getEwl_win()/wcupList.get(0).getEw_people() }" />
<tr>
<td>${i.count}</td>
<td><img width="100px;" height="130px;" style="cursor: pointer" onclick="location.href='adStyleView?siidx=${wd.getSi_idx() }&piid=${wd.getPi_id()}'" src="resources/img/style_img/${wd.getSi_img()}"/></td>
<td>${wd.getMi_id() }</td>
<td><c:if test="${late>0}"><fmt:formatNumber value="${late*100}" pattern=".0"/></c:if> 
<c:if test="${late<=0}">0.0</c:if>%
<br>(${wd.getEwl_win()}/${wcupList.get(0).getEw_people() })</td>
</tr>


</c:forEach>
</table>
</div>
</div>
<script src="resources/js/jquery-3.6.4.js"></script>
<script>
function wcupDel(ewidx){	
		if(confirm("정말 삭제하시겠습니까?")){
			$.ajax({
				type: "POST", url: "adWcupDel",
				data:{"ewidx": ewidx},
				success : function(chkRs){
					alert(chkRs);
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
<%@ include file="../../_inc/inc_foot_ad.jsp" %>