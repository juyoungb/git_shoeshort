<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head_ad.jsp" %>
<%@ page import="java.util.*" %>
<% 
request.setCharacterEncoding("utf-8");
List<StyleInfo> styleList  = (List<StyleInfo>)request.getAttribute("styleList");
PageInfo pageInfo = (PageInfo)request.getAttribute("pageInfo");
int bsize = pageInfo.getBsize();		int cpage = pageInfo.getCpage();
int psize = pageInfo.getPsize();		int pcnt = pageInfo.getPcnt();
int rcnt = pageInfo.getRcnt();
String schtype = pageInfo.getSchtype() , keyword = pageInfo.getKeyword(), args = pageInfo.getArgs()  ,schargs = pageInfo.getSchargs();
%>
<style>
#list tr { height:25px;}
#list tr, #list td { padding:8px 3px;}
#list th { border-bottom:double black 3px;}
#list td { border-bottom:dotted black 1px;}
#styleList { position: absolute; left: 200px; top: 100px; width: 80%; height:60%; }
</style>
<body>
<div id="styleList">
<h2>스타일 목록</h2>
<table width="100%" cellpadding="2" height="20%">
<tr><td align="right">
	<form name="frmSch" method="get">
	<fieldset>
		<select name="schtype">
			<option value="">검색조건</option>
			<option value="id" <% if(schtype.equals("id")){ %>selected="selected"<% } %>>아이디</option>
			<option value="content" <% if(schtype.equals("content")){ %>selected="selected"<% } %>>내용</option>
		</select>
		<input type="text" name="keyword" value="<%=keyword %>">
		<input type="submit" class="btn btn-outline-dark" value="검색">
		<input type="button" class="btn btn-outline-dark" value="전체 글" onclick="location.href='adStyle?cpage=1'">
	</fieldset>
	</form>
	</td></tr>
</table>
<table width="100%"  cellpadding="0" cellspacing="0" id="list">
<tr>
<th width="5%">번호</th><th width="10%">회원아이디</th><th width="15%">내용</th><th width="10%">작성일</th><th width="5%">조회</th><th width="5%">좋아요</th>
</tr>
<%
if (styleList.size() > 0) {
	int num = rcnt - (psize * (cpage - 1));
	for (StyleInfo si : styleList) {
	String mi_id = si.getMi_id(), si_content = si.getSi_content(), piid = si.getPi_id();
	int siidx = si.getSi_idx();
	if (si_content.length() > 18)	si_content = si_content.substring(0, 15) +"...";
		mi_id ="<a href='adStyleView?siidx=" + siidx +"&piid="+ piid + "' style='text-decoration:none; color:black;'>" + mi_id + "</a>";
%>		
<tr align="center" onmouseover="this.bgColor ='#efefef'" onmouseout="this.bgColor =''">
<td align="left"><%=num %></td><td align="left"><%=mi_id %></td>
<td align="left"><%=si_content %></td><td align="left"><%=si.getSi_date() %></td>
<td align="left"><%=si.getSi_read() %></td><td align="left"><%=si.getSi_good() %></td>
<%
		num--;
	} 

} else { //게시글 목록이 없으면
	out.println("<tr><td colspan='5' align='center'>");
	out.println("검색 결과가 없습니다.</td></tr>");
}
%>
</table>
<br>
<table width="100%" cellpadding="5">
<tr>
<td width="600" align="center">
<%
if (rcnt > 0) { //게시글이 있으면 - 페이징 영역을 보여줌
	String link = "adStyle?1=1" + schargs + "&cpage=";
	
	if (cpage == 1) {
		out.println("[<<]&nbsp;&nbsp;&nbsp;[<]&nbsp;&nbsp;");
	}else{
		out.println("<a href='" + link + "1'>[<<]</a>&nbsp;&nbsp;&nbsp;");
		out.println("<a href='" + link + (cpage - 1) + "'>[<]</a>&nbsp;&nbsp;&nbsp;");		
	}
	int spage = (cpage -1) / bsize * bsize + 1; //현재 블록에서의 시작 페이지 번호
	for (int i = 1, j = spage ; i<= bsize && j <= pcnt ; i++, j++){
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
</tr>
</table>
</div>
<%@ include file="../_inc/inc_foot_ad.jsp" %>