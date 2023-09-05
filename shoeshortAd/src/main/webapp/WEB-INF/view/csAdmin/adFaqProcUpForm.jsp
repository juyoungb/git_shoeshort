<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head_ad.jsp" %>
<%
request.setCharacterEncoding("utf-8");
FaqList faqInfo = (FaqList)request.getAttribute("faqInfo");
int flidx = Integer.parseInt(request.getParameter("flidx"));

%>
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
<h2>faq 글 수정 폼</h2>
<form name="frm" action="adFaqProcUp" method="post">
<input type="hidden" name="flidx" value="<%=flidx %>" />
<table width="600" cellpadding="5">
<tr>
<th width="15%">관리자 번호 : </th>
<td width="35%"><%=faqInfo.getAi_idx() %></td>
<th width="15%">작성일</th>
<td width="35%"><%=faqInfo.getFl_date() %></td>
</tr>
<tr>
<th width="15%">질문 제목</th>
<td colspan="3"><input type="text" name="title" size="60" value="<%=faqInfo.getFl_title() %>"></td>
</tr>
<tr>
<th>질문 내용</th>
<td colspan="3"><textarea name="content" rows="10" cols="65"><%=faqInfo.getFl_content() %></textarea></td>
</tr>
<tr>
<th>질문 답변</th>
<td colspan="3"><textarea name="answer" rows="10" cols="65"><%=faqInfo.getFl_answer() %></textarea></td>
</tr>
<tr><td colspan="4" align="center">
	<input type="button" value="글 수정" onclick="chkForm();">
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="reset" value="다시 입력">
</td></tr>
</table>
</form>
</body>
</html>