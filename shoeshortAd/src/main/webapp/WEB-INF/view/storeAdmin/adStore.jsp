<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head_ad.jsp" %>
<%@ page import="java.util.*" %>
<% 
request.setCharacterEncoding("utf-8");
List<StoreInfo> storeList  = (List<StoreInfo>)request.getAttribute("storeList");
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
#storeList { position: absolute; width:80%; left:10%; top: 150px;}
.page-link {color:black;}
</style>
<script src="resources/js/popUpJs.js"></script>
<body>
<div id="storeList">
<h2>매장 관리</h2>
<br />
<table width="100%" cellpadding="5">
<tr><td align="right">
	<form name="frmSch" method="get">
	<fieldset>
		<select name="schtype">
			<option value="">검색조건</option>
			<option value="brand" <% if(schtype.equals("brand")){ %>selected="selected"<% } %>>브랜드</option>
			<option value="name" <% if(schtype.equals("name")){ %>selected="selected"<% } %>>매장명</option>
		</select>
		<input type="text" name="keyword" value="<%=keyword %>">
		<input type="submit" class="btn btn-dark" value="검색">
		<input type="button" class="btn btn-dark" value="전체 글" onclick="location.href='adStore?cpage=1'">
	</fieldset>
	</form>
	</td></tr>
</table>
<br />
<table width="100%"  cellpadding="0" cellspacing="0" id="list">
<tr>
<th width="5%" style="text-align:center;">번호</th><th width="10%" style="text-align:center;">브랜드</th>
<th width="20%">매장명</th><th width="30%">주소</th><th width="20%">등록/수정일</th><th width="10%" style="text-align:center;">수정</th>
</tr>
<%
if (storeList.size() > 0) {
	int num = rcnt - (psize * (cpage - 1));
	for (StoreInfo si : storeList) {
	String brand = si.getBsi_brand();
	String bsiname = si.getBsi_name(), bsiaddr = si.getBsi_addr();
	int bsiidx = si.getBsi_idx();
%>		
<tr align="center">
<td align="center"><%=num %></td><td align="center"><%=brand %></td>
<td align="left"><%=bsiname %></td>
<td align="left"><%=bsiaddr %></td>
<td align="left"><%=si.getBsi_date() %></td>
<td><input type="button" onclick="popUp('storeFormUp?bsiidx=<%=si.getBsi_idx() %>','','700','500');" class="btn btn-outline-dark" value="수정"></td>
<%
		num--;
	} 

} else { //게시글 목록이 없으면
	out.println("<tr><td colspan='6' align='center'>");
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
	<input type="button" class="btn btn-outline-dark" value="글 등록" onclick="popUp('storeFormIn','','700','500');">
</td>
</tr>
</table>
</div>
</div>
<%@ include file="../_inc/inc_foot_ad.jsp" %>