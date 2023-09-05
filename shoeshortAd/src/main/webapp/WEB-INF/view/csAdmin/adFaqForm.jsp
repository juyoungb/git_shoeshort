<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head_ad.jsp" %>
<script>
function chkForm(){
	var frm = document.frm;
	var title = frm.title.value;
	var content = frm.content.value;
	var answer = frm.answer.value;
	
	if (title == "" || content == "" || answer == ""){
		alert("입력칸을 확인해주세요.");
		return false;
	}
	if (title != "" && content != "" || answer == ""){ 
		document.frm.submit();
	}
}
</script>
<link rel="stylesheet" href="resources/css/style.css" />
<h2>faq 글 등록 폼</h2>
<form name="frm" action="adFaqProcIn" method="post">
<table width="600" cellpadding="5">
<input type="hidden" name="idx" value="<%=loginInfo.getAi_idx() %>" />
<tr>
<th width="15%">작성자</th>
<td width="35%"><%=aiid %></td>
</tr>
<tr>
<th width="15%">질문제목</th>
<td colspan="3"><input type="text" name="title" size="60"></td>
</tr>
<tr>
<th>질문내용</th>
<td colspan="3"><textarea name="content" rows="10" cols="65"></textarea></td>
</tr>
<tr>
<th>질문답변</th>
<td colspan="3"><textarea name="answer" rows="10" cols="65"></textarea></td>
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