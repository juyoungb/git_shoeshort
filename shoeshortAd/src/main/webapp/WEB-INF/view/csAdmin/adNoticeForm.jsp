<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head_ad.jsp" %>
<script>
function chkForm(){
	var frm = document.frm;
	if (frm.ctgr.value == "" || frm.title.value == "" || frm.content.value == ""){
		alert("입력칸을 확인해주세요.");
		return;
	} else {
		alert("등록 되었습니다.");
		return frm.submit();
	}
}
function link(){
	location.href="adNoticeList";
}
</script>
<style>
.form-group {width:50%; height:80%; align:center; margin-left:25%; margin-top:40px;}
.btn {margin-left:100px; border:none;}
</style>
<link rel="stylesheet" href="resources/css/style.css" />
<form name="frm" action="adNoticeProcIn" method="post">
<input type="hidden" name="idx" value="<%=loginInfo.getAi_idx() %>" />
<div class="form-group">
<h2>공지사항 등록 폼</h2>
<br/>
<p>
	<select name="ctgr">
		<option value="">분류조건</option>
		<option value="a" >공지사항</option>
		<option value="b" >이벤트안내</option>
		<option value="c" >이벤트발표</option>
	</select>
</p>
		<label for="exampleFormControlInput1">작성자</label>
		<p><%=aiid %></p>
	      <label for="exampleFormControlInput1">제목</label>
	    <input type="text" class="form-control" id="exampleFormControlInput1" name="title" placeholder="제목을 작성해주세요.">
	  
	    <label for="exampleFormControlInput1">내용</label>
	    <textarea class="form-control" id="exampleFormControlTextarea1" name="content" rows="9"></textarea>  
	<div class="btn">
	<input type="button" onclick="chkForm();"  class="btn btn-dark" value="등록하기" />
	<button type="button" class="btn btn-dark" onclick="link();">목록으로</button>
	<button type="reset" class="btn btn-dark">다시입력</button>
	</div>
</div>
</form>
<%@ include file="../_inc/inc_foot_ad.jsp" %>















