<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head_ad.jsp" %>
<%@ page import="java.util.*" %>
<% 
request.setCharacterEncoding("utf-8");
List<NoticeList> noticeList  = (List<NoticeList>)request.getAttribute("noticeList");
PageInfo pageInfo = (PageInfo)request.getAttribute("pageInfo");
int bsize = pageInfo.getBsize();		int cpage = pageInfo.getCpage();
int psize = pageInfo.getPsize();		int pcnt = pageInfo.getPcnt();
int rcnt = pageInfo.getRcnt();
String schtype = pageInfo.getSchtype() , keyword = pageInfo.getKeyword(), args = pageInfo.getArgs()  ,schargs = pageInfo.getSchargs(),
ctgr = pageInfo.getCtgr();
%>
<style>
#list tr { height:25px;}
#list tr, #list td { padding:8px 3px;}
#list th { border-bottom:double black 3px;}
#list td { border-bottom:dotted black 1px;}
a { text-decoration: none; color:black;}
.noticeList {position: absolute; left: 200px; top: 100px; width: 80%; height:60%;}
</style>
<div class="noticeList">
<h2>공지사항 목록</h2>
<table width="100%" cellpadding="5">
<tr><td align="right">
	<form name="frmSch" method="get">
	<fieldset>
		<select name="ctgr">
			<option value="">분류조건</option>
			<option value="a" <% if(ctgr.equals("a")){ %>selected="selected"<% } %>>공지사항</option>
			<option value="b" <% if(ctgr.equals("b")){ %>selected="selected"<% } %>>이벤트안내</option>
			<option value="c" <% if(ctgr.equals("c")){ %>selected="selected"<% } %>>이벤트발표</option>
		</select>
		<select name="schtype">
			<option value="">검색조건</option>
			<option value="title" <% if(schtype.equals("title")){ %>selected="selected"<% } %>>제목</option>
			<option value="content" <% if(schtype.equals("content")){ %>selected="selected"<% } %>>내용</option>
		</select>
		<input type="text" name="keyword" value="<%=keyword %>">
		<input type="submit" class="btn btn-outline-dark" value="검색">
		<input type="button" class="btn btn-outline-dark" value="전체 글" onclick="location.href='adNoticeList'">
	</fieldset>
	</form>
	</td></tr>
</table>
<br />
<table width="100%"  cellpadding="0" cellspacing="0" id="list">
<tr>
<th width="5%" style="text-align:center;">번호</th><th width="15%">제목</th><th width="30%">내용</th>
<th width="10%" style="text-align:center;">작성일</th><th width="5%" style="text-align:center;">조회수</th>
</tr>
<%
if (noticeList.size() > 0) {
	int num = rcnt - (psize * (cpage - 1));
	for (NoticeList nl : noticeList) { 
	String title = nl.getNl_title();
	String content = nl.getNl_content();
		if (title.length() > 30)	title = title.substring(0, 27) +"...";
		if (content.length() > 30)	content = content.substring(0, 37) +"...";
		
		title ="<a href='adNoticeView?nlidx=" + nl.getNl_idx() + args + "'>" + title + "</a>";
%>		
<tr align="center" onmouseover="this.bgColor ='#efefef'" onmouseout="this.bgColor =''">
<td><%=num %></td><td align="left"><%=title %></td>
<td align="left"><%=content %></td><td><%=nl.getNl_date() %></td>
<td><%=nl.getNl_read() %></td>
<%
		num--;
	} 

} else { //게시글 목록이 없으면
	out.println("<tr><td colspan='5' align='center'>");
	out.println("검색 결과가 없습니다.</td></tr>");
}
%>
</table>
<br />
<table width="100%" cellpadding="5">
<tr>
<td width="*" align="center">
<%
if (rcnt > 0) { //게시글이 있으면 - ㅍㅔ이징 영역을 보여줌
	String link = "adNoticeList?1=1" + schargs + "&cpage=";
	
	if (cpage == 1) {
		out.println("[<<]&nbsp;&nbsp;&nbsp;[<]&nbsp;&nbsp;");
	}else{
		out.println("<a href='" + link + "1'>[<<]</a>&nbsp;&nbsp;&nbsp;");
		out.println("<a href='" + link + (cpage - 1) + "'>[<]</a>&nbsp;&nbsp;&nbsp;");		
	}
	int spage = (cpage -1) / bsize * bsize + 1; //현재 블록에서의 시작 페이지 번호
	for (int i = 1, j = spage ; i<= bsize && j <=pcnt ; i++, j++){
		// i : 블록에서 보여줄 페이지의 개수만큼 루프를 돌릴 조건으로 사용되는 변수
		// j :실제 출력할 페이지 번호로 전체 페이지 개수(마지막 페이지 번호)를 넘지 않게 사용해야 함
		if (cpage == j){
			out.println("&nbsp;<strong>" + j + "</strong>&nbsp;");
		} else {
			out.println("&nbsp;<a href='" + link + j + "'>" + j + "</a>&nbsp;");
		}
	}
	
	if (cpage == pcnt) {
		out.println("&nbsp;&nbsp;[>]&nbsp;&nbsp;&nbsp;[>>]");
	}else{
		out.println("&nbsp;&nbsp;<a href='" + link + (cpage + 1) +"'>[>]</a>");
		out.println("&nbsp;&nbsp;&nbsp;<a href='" + link + pcnt + "'>[>>]</a>");
	}
}
%>
</td>
<td width="3%" align="right">
	<input type="button" class="btn btn-dark" value="글 등록" onclick="location.href='adNoticeForm'">
</td>
</tr>
</table>
</div>
<%@ include file="../_inc/inc_foot_ad.jsp" %>