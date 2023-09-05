<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head_fr.jsp" %>
<%
request.setCharacterEncoding("utf-8");
StyleInfo styleInfo = new StyleInfo();
String piid = styleInfo.getPi_id();
%>
<style>
.filebox .upload-name {
    display: inline-block;
    height: 40px;
    padding: 0 10px;
    vertical-align: middle;
    border: 1px solid #dddddd;
    width: 78%;
    color: #999999;
}
.filebox label {
    display: inline-block;
    padding: 10px 20px;
    color: #fff;
    vertical-align: middle;
    background-color: #999999;
    cursor: pointer;
    height: 40px;
    margin-left: 10px;
}
.filebox input[type="file"] {
    position: absolute;
    width: 0;
    height: 0;
    padding: 0;
    overflow: hidden;
    border: 0;
}
</style>
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.4.js"></script>
<script>
$(document).ready(function() {
	$("#file").on('change',function(){
		  var fileName = $("#file").val();
		  $(".upload-name").val(fileName);
		});
});
</script>
<form action="StyleFormProc" method="post" enctype="multipart/form-data">
<input type="hidden" name="piid" value="<%=piid %>" />
	<div>
	<table width="50%" height="550px" cellpadding="20" align="center">
	<tr>
	<th width="20%">스타일 등록 폼</th>
	</tr>	
	<tr>
	<th width="15%">이미지</th>
	<td width="*">
	<div class="filebox">
	<input class="upload-name" value="첨부파일" placeholder="첨부파일" />
	<label for="file">이미지 선택</label>
	<input type="file" id="file" name="uploadFile" multiple="multiple" />
	</div>
	</td>
	</tr>
	<tr>
		<th>글 내용</th>
		<td><textarea name="si_content" rows="10" cols="65" class="form-control"></textarea></td>
	</tr>
	</table>
	<table width="400" align="center">
	<tr>
		<td align="center">
		<input type="submit" class="btn btn-outline-dark" value="등록" />&nbsp;&nbsp;&nbsp;
		<input type="reset"  class="btn btn-outline-dark" value="다시 입력" />&nbsp;&nbsp;&nbsp;
		<input type="button" class="btn btn-outline-dark" value="취소" onclick="location.href='';" />
		</td>
	</tr>
	</table>
	</div>
</form>