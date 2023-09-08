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
function link(){
	location.href="adFaqList";
}
</script>
<style>
.form-group {width:50%; height:80%; align:center; margin-left:25%; margin-top:40px;}
.btn {margin-left:100px;}
</style>
<link rel="stylesheet" href="resources/css/style.css" />
<form name="frm" action="adFaqProcIn" method="post">
<input type="hidden" name="idx" value="<%=loginInfo.getAi_idx() %>" />
<div class="form-group">
<h2>faq 수정 폼</h2>
<br/>
		<label for="exampleFormControlInput1">작성자</label>
		<p><%=aiid %></p>
	      <label for="exampleFormControlInput1">제목</label>
	    <input type="text" class="form-control" id="exampleFormControlInput1" name="title" value="<%=faqInfo.getFl_title() %>">
	    <label for="exampleFormControlInput1">질문</label>
	    <textarea class="form-control" id="exampleFormControlTextarea1" name="content" rows="9"><%=faqInfo.getFl_content() %></textarea>
	    <label for="exampleFormControlTextarea1">답변</label>
	    <textarea class="form-control" id="exampleFormControlTextarea1" name="answer" rows="9"><%=faqInfo.getFl_answer() %></textarea>	  
	<div class="btn">
	<button type="submit" class="btn btn-dark">수정하기</button>
	<button type="button" class="btn btn-dark" onclick="link();">목록으로</button>
	<button type="reset" class="btn btn-dark">다시입력</button>
	</div>
</div>
</form>
<%@ include file="../_inc/inc_foot_ad.jsp" %>











