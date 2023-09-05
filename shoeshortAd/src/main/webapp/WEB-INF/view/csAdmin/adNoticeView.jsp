<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head_ad.jsp" %>

<%
request.setCharacterEncoding("utf-8");
NoticeList noticeInfo = (NoticeList)request.getAttribute("noticeInfo");
int nlidx = Integer.parseInt(request.getParameter("nlidx"));
String ctgr = "";
if (noticeInfo.getNl_ctgr().equals("a")) ctgr = "공지사항";
if (noticeInfo.getNl_ctgr().equals("b")) ctgr = "이벤트 안내";
if (noticeInfo.getNl_ctgr().equals("c")) ctgr = "이벤트 발표";
%>
<html>
<script>
function link(idx){
	location.href="adNoticeList?cpage=1";
}
function procLink(val){
	if (val == "up"){
		location.href = "adNoticeProcUpForm?nlidx=<%=nlidx%>&ctgr=<%=noticeInfo.getNl_ctgr() %>";
	}else{
		alert("정말 삭제하시겠습니까?");
		location.href = "adNoticeProcDel?nlidx=<%=nlidx%>";
	}
}
</script>
<body>
<style>
tr, th {border:1px solid black; border-left:0; border-right:0; border-color: gray;}
th { background-color: #E2DECE;  opacity: 0.8; font-weight: bold; }
</style>
<body>
<br />
<div style="width:70%; height:600px; margin-left:8%" align="center" >
<h2 style="margin-right: 60%;">공지사항 보기화면</h2>
<br />
<table width="80%" height="90%" cellpadding="5" align="right">
<tr style="vertical-align : top;" height="5%">
<th width="7%">조회수</th><td width="70%"><%=noticeInfo.getNl_read() %></td></tr>
<tr style="vertical-align : top;" height="5%">
<th>작성일</th><td><%=noticeInfo.getNl_date() %></td></tr>
<tr style="vertical-align : top;" height="5%">
<th>제목</th><td><%=noticeInfo.getNl_title() %><td>
</tr>
<tr>
<tr style="vertical-align : top; "><td colspan="5" ><%=noticeInfo.getNl_content().replace("/r/n", "<br />") %></td></tr>
</tr>
</table>
</div>
<div style="width: 20%; margin-left:40%; margin-top:1%;" align="center">
<p>
<input type="button" class="btn btn-outline-secondary" value="수정" onclick="procLink('up');"/>&nbsp;&nbsp;&nbsp;
<input type="button" class="btn btn-outline-secondary" value="목록" onclick="link();"/>&nbsp;&nbsp;&nbsp;
<input type="button" class="btn btn-outline-danger" value="삭제" onclick="procLink('del');"/>
</p>
</div>
<%@ include file="../_inc/inc_foot_ad.jsp" %>




