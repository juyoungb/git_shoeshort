<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head_ad.jsp" %>
<%
request.setCharacterEncoding("utf-8");
FaqList faqInfo = (FaqList)request.getAttribute("faqInfo");
int flidx = faqInfo.getFl_idx();
%>
<html>
<script>
function link(idx){
	location.href="adFaqList?cpage=1";
}
function procLink(val){
	if (val == "up"){
		location.href = "adFaqProcUpForm?flidx=<%=flidx%>";
	}else{
		alert("정말 삭제하시겠습니까?");
		location.href = "adFaqProcDel?flidx=<%=flidx%>";
	}
}
</script>
<style>
tr, th {border:1px solid black; border-left:0; border-right:0; border-color: gray;}
th { background-color: #E2DECE;  opacity: 0.8; font-weight: bold; }
</style>
<body>
<br />
<div style="width:70%; height:600px; margin-left:8%" align="center" >
<h2 style="margin-right: 60%;">FaQ 보기화면</h2>
<br />
<table width="80%" height="90%" cellpadding="5" align="right">
<tr style="vertical-align : top;" height="5%">
<th width="7%">조회수</th><td width="70%"><%=faqInfo.getFl_read() %></td></tr>
<tr style="vertical-align : top;" height="5%">
<th>작성일</th><td><%=faqInfo.getFl_date() %></td></tr>
<tr style="vertical-align : top;" height="5%">
<th>제목</th><td><%=faqInfo.getFl_title() %><td>
</tr>
<tr>
<tr style="vertical-align : top; "><td colspan="5" ><%=faqInfo.getFl_content().replace("/r/n", "<br />") %></td></tr>
<tr style="vertical-align : top;"><td colspan="5" ><%=faqInfo.getFl_answer().replace("/r/n", "<br />") %></td></tr>
</tr>
</table>
</div>
<div style="width: 20%; margin-left:40%; margin-top:1%;" align="center">
<p>
<input type="button" class="btn btn-outline-dark" value="수정" onclick="procLink('up');"/>&nbsp;&nbsp;&nbsp;
<input type="button" class="btn btn-dark" value="목록" onclick="link();"/>&nbsp;&nbsp;&nbsp;
<input type="button" class="btn btn-outline-danger" value="삭제" onclick="procLink('del');"/>
</p>
</div>
<%@ include file="../_inc/inc_foot_ad.jsp" %>


