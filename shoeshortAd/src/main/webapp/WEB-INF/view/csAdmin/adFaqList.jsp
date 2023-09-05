<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head_ad.jsp" %>
<%@ page import="java.util.*" %>
<% 
request.setCharacterEncoding("utf-8");
List<FaqList> faqList  = (List<FaqList>)request.getAttribute("faqList");
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
a { text-decoration: none; color:black;}
.faqList {position: absolute; left:7%; top: 100px; width: 90%; height:60%;}
</style>
<link rel="stylesheet" href="resources/css/style.css" />
<script>
function inBtn(){
	location.href="adFaqForm";
}
</script>
<div class="faqList">
<h2>FaQ 목록</h2>
<br />
<table width="100%" cellpadding="5">
<tr><td align="right">
	<form name="frmSch" method="get">
	<fieldset>
		<select name="schtype">
			<option value="">검색조건</option>
			<option value="title" <% if(schtype.equals("title")){ %>selected="selected"<% } %>>제목</option>
			<option value="content" <% if(schtype.equals("content")){ %>selected="selected"<% } %>>내용</option>
		</select>
		<input type="text" name="keyword" value="<%=keyword %>">
		<input type="submit" class="btn btn-outline-secondary" value="검색">
		<input type="button" class="btn btn-outline-secondary" value="전체 글" onclick="location.href='adFaqList'">
	</fieldset>
	</form>
	</td></tr>
</table>
<br />
<table width="100%"  cellpadding="0" cellspacing="0" id="list">
<tr>
<th width="10%" style="text-align:center;">번호</th><th width="30%">제목</th><th width="30%">내용</th>
<th width="15%" style="text-align:center;">작성일</th><th width="10%" style="text-align:center;">조회수</th>
</tr>
<%
if (faqList.size() > 0) {
	int num = rcnt - (psize * (cpage - 1));
	for (FaqList fl : faqList) { 
	String title = fl.getFl_title();
		if (title.length() > 30)	title = title.substring(0, 27) +"...";
	title ="<a href='adFaqView?flidx="+ fl.getFl_idx() + args + "'>" + title + "</a>";
		
	String content = fl.getFl_content();
		if (content.length() > 30)	content = content.substring(0, 27) +"...";
		
%>		
<tr align="center" onmouseover="this.bgColor ='#efefef'" onmouseout="this.bgColor =''">
<td align="center"><%=num %></td><td align="left"><%=title %></td><td align="left"><%=fl.getFl_content() %></td>
<td><%=fl.getFl_date() %></td><td><%=fl.getFl_read() %></td>
<%
		num--;
	} 

} else { //게시글 목록이 없으면
	out.println("<tr><td colspan='5' align='center'>");
	out.println("등록된 공지사항이 없습니다.</td></tr>");
}
%>
</table>
<br>
<table width="100%" cellpadding="5">
<tr>
<td width="600" align="center">
<%
if (rcnt > 0) { //게시글이 있으면 - 페이징 영역을 보여줌
	String link = "adFaqList?1=1&cpage=";
	
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
<td width="5%" align="rignt">
	<input type="button" class="btn btn-outline-secondary" value="글 등록" onclick="location.href='adFaqForm'">
</td>
</tr>
</table>
</div>
<%@ include file="../_inc/inc_foot_ad.jsp" %>