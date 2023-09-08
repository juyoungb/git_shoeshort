<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head_ad.jsp" %>
<%
request.setCharacterEncoding("utf-8");
NoticeList noticeInfo = (NoticeList)request.getAttribute("noticeInfo");
PageInfo pi = (PageInfo)request.getAttribute("pi");
int nlidx = Integer.parseInt(request.getParameter("nlidx"));
String ctgr = pi.getCtgr();
System.out.println(ctgr);
%>
<script>
function chkForm(){
	var frm = document.frm;
	var ctgr = frm.ctgr.value;
	var title = frm.title.value;
	var content = frm.content.value;
	
	if (ctgr == "" || title == "" || content == ""){
		alert("입력칸을 확인해주세요.");
		return false;
	}
	if (ctgr != "" && title != "" && content != ""){ 
		document.frm.submit();
	}
}
function link(){
	location.href="adNoticeList";
}
</script>
<style>
.form-group {width:50%; height:80%; align:center; margin-left:25%; margin-top:40px;}
.btn {margin-left:100px;}
</style>
<link rel="stylesheet" href="resources/css/style.css" />
<form name="frm" action="adNoticeProcIn" method="post">
<input type="hidden" name="nlidx" value="<%=nlidx %>" />
<div class="form-group">
<h2>공지사항 수정 폼</h2>
<br/>
	<fieldset>
		<select name="ctgr">
			<option value="">분류조건</option>
			<option value="a" <% if(ctgr.equals("a")){ %>selected="selected"<% } %>>공지사항</option>
			<option value="b" <% if(ctgr.equals("b")){ %>selected="selected"<% } %>>이벤트안내</option>
			<option value="c" <% if(ctgr.equals("c")){ %>selected="selected"<% } %>>이벤트발표</option>
		</select>
	</fieldset>
		<label for="exampleFormControlInput1">작성자</label>
		<p><%=aiid %></p>
	      <label for="exampleFormControlInput1">제목</label>
	    <input type="text" class="form-control" id="exampleFormControlInput1" name="title" value="<%=noticeInfo.getNl_title() %>">
	  
	    <label for="exampleFormControlInput1">내용</label>
	    <textarea class="form-control" id="exampleFormControlTextarea1" name="content" rows="9"><%=noticeInfo.getNl_content() %></textarea>  
	<div class="btn">
	<button type="submit" class="btn btn-dark">수정하기</button>
	<button type="button" class="btn btn-dark" onclick="link();">목록으로</button>
	<button type="reset" class="btn btn-dark">다시입력</button>
	</div>
</div>
</form>
<%@ include file="../_inc/inc_foot_ad.jsp" %>





















