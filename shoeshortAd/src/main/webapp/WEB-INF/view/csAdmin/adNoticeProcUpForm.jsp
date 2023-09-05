<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head_ad.jsp" %>
<%
request.setCharacterEncoding("utf-8");
String args = (String)request.getAttribute("args");
NoticeList noticeInfo = (NoticeList)request.getAttribute("noticeInfo");
PageInfo pi = (PageInfo)request.getAttribute("pi");
int nlidx = Integer.parseInt(request.getParameter("nlidx"));
String ctgr = pi.getCtgr();
%>
<h2>공지사항 글 수정 폼</h2>
<form name="frm" action="adNoticeProcUp" method="post">
<input type="hidden" name="args" value="<%=args %>" />
<input type="hidden" name="nlidx" value="<%=nlidx %>" />
<fieldset>
		<select name="ctgr">
			<option value="">분류조건</option>
			<option value="a" <% if(ctgr.equals("a")){ %>selected="selected"<% } %>>공지사항</option>
			<option value="b" <% if(ctgr.equals("b")){ %>selected="selected"<% } %>>이벤트안내</option>
			<option value="c" <% if(ctgr.equals("c")){ %>selected="selected"<% } %>>이벤트발표</option>
		</select>
	</fieldset>
<table width="600" cellpadding="5">
<tr>
<th width="15%">관리자 번호 : </th>
<td width="35%"><%=noticeInfo.getAi_idx() %></td>
<th width="15%">작성일</th>
<td width="35%"><%=noticeInfo.getNl_date() %></td>
</tr>
<tr>
<th width="15%">글제목</th>
<td colspan="3"><input type="text" name="title" size="60" value="<%=noticeInfo.getNl_title() %>"></td>
</tr>
<tr>
<th>글내용</th>
<td colspan="3"><textarea name="content" rows="10" cols="65"><%=noticeInfo.getNl_content() %></textarea></td>
</tr>
<tr><td colspan="4" align="center">
	<input type="submit" value="글 수정">
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="reset" value="다시 입력">
</td></tr>
</table>
</form>
</body>
</html>