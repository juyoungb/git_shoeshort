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
<h2>faq 등록 폼</h2>
<br/>
		<label for="exampleFormControlInput1">작성자</label>
		<p><%=aiid %></p>
	      <label for="exampleFormControlInput1">제목</label>
	    <input type="text" class="form-control" id="exampleFormControlInput1" name="title" placeholder="제목을 작성해주세요.">
	  
	    <label for="exampleFormControlInput1">질문</label>
	    <textarea class="form-control" id="exampleFormControlTextarea1" name="content" rows="9"></textarea>
	    <label for="exampleFormControlTextarea1">답변</label>
	    <textarea class="form-control" id="exampleFormControlTextarea1" name="answer" rows="9"></textarea>	  
	<div class="btn">
	<button type="submit" class="btn btn-dark">등록하기</button>
	<button type="button" class="btn btn-dark" onclick="link();">목록으로</button>
	<button type="reset" class="btn btn-dark">다시입력</button>
	</div>
</div>
</form>
<%@ include file="../_inc/inc_foot_ad.jsp" %>





