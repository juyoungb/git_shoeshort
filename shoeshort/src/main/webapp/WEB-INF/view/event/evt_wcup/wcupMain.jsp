<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../_inc/inc_head_fr.jsp" %>
<c:if test="${not empty detailList}">
<style>
.styled-table {
	width: 1300px;
	border-collapse: collapse;
	border: 1px solid #ddd;
	margin: 20px 0;
}

.styled-table th, .styled-table td {
	border: 1px solid #ddd;
	padding: 8px;
	text-align: center;
}
.styled-link {
	display: inline-block;
	margin: 10px;
	text-decoration: none;
	color: #333;
	text-align: center;
}

.styled-link img {
	display: block;
	margin: 0 auto;
}

.styled-button {
	 background-color: #333; 
 	color: white;
	border: none;
	padding: 10px 20px;
	cursor: pointer;
	opacity: 1;
	transition: opacity 0.3s ease-in-out;
}

.styled-button:hover {
	opacity: 0.8;
}
</style>

<br>
<div align="center">
<h2>${el.getEw_title()}</h2>
<c:forEach var="dl" items="${detailList }" begin="0" end="2" varStatus="status" >
<c:set var="late" value="${dl.getEwl_win()/el.getEw_people() }" />
<a href="styleView?siidx=${dl.getSi_idx()}&piid=${dl.getPi_id()}" class="styled-link">
<img width="350px" height=400px; src="resources/img/style_img/${dl.getSi_img() }" /> ${status.count }위 ${dl.getMi_id() }(
<c:if test="${late>0}"><fmt:formatNumber value="${late*100}" pattern=".0"/></c:if> 
<c:if test="${late<=0}">0.0</c:if>%)
</a>
</c:forEach>

<br>
<input type="button" class="styled-button" style="width:500px;" onclick="popUp('wcupGame?gi=${el.getEw_idx()}&rule=${el.getEw_rule()}','','1400','850');" value="코디왕을 뽑으러 가기"/>
<br>
이벤트 기간: ${fn:substring(el.getEw_vsdate(),0,10) }~${fn:substring(el.getEw_vedate(),0,10) }
</div>
<br>

<div align="center">
<h3>${rl.getEw_title()} 지원하기</h3>
<table class="styled-table">
	<thead>
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>기간</th>
			<th>지원현황</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>1</td>
			<td>${rl.getEw_title()}</td>
			<td>${fn:substring(rl.getEw_csdate(),0,10) }~${fn:substring(rl.getEw_cedate(),0,10) }</td>
			<td>
				<c:if test="${rl.getEw_people() != rl.getEw_rule() }">
				<input type="button"onclick="popUp('wcupJoin?n=${rl.getEw_idx()}','','1300','850');" value="지원하기 (${rl.getEw_people()} / ${rl.getEw_rule()})" class="styled-button" />
				</c:if>
				<c:if test="${rl.getEw_people() == rl.getEw_rule() }">모집 종료</c:if>
			</td>
		</tr>
		<!-- 다른 행들도 추가 가능 -->
	</tbody>
</table>
</div>
</c:if>
<c:if test="${empty detailList}">
<script>
alert('현재 진행중인 이벤트가 없습니다.');
history.back();
</script>
</c:if>
<%@ include file="../../_inc/inc_foot_fr.jsp" %>