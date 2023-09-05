<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head_ad.jsp" %>
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
</script>
<h2>자유게시판 글 등록 폼</h2>
<form name="frm" action="adNoticeProcIn" method="post">
<table width="600" cellpadding="5">
<input type="hidden" name="idx" value="<%=loginInfo.getAi_idx() %>" />
<tr><td>
	<select name="ctgr">
		<option value="">분류조건</option>
		<option value="a" >공지사항</option>
		<option value="b" >이벤트안내</option>
		<option value="c" >이벤트발표</option>
	</select>
</td></tr>
<tr>
<th width="15%">작성자</th>
<td width="35%"><%=aiid %></td>
</tr>
<tr>
<th width="15%">글제목</th>
<td colspan="3"><input type="text" name="title" size="60"></td>
</tr>
<tr>
<th>글내용</th>
<td colspan="3"><textarea name="content" rows="10" cols="65"></textarea></td>
</tr>
<tr><td colspan="4" align="center">
	<input type="button" value="글 등록" onclick="chkForm();">
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="reset" value="다시 입력">
</td></tr>
</table>
</form>
</body>
</html>