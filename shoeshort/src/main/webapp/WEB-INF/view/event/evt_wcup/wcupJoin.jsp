<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
div {
    display: inline-block;
    width: 100%;
    height: 400px;
    line-height: 100px;
    text-align: left;
    font-size: 20px;
}
.img{
	width:200px;
	heignt:400px;
}
</style>
<script>
function checkOnlyOne(element) {
		  const checkboxes = document.getElementsByName("chk");
	  checkboxes.forEach((cb) => {
		    cb.checked = false;
	  });  
	  element.checked = true;
	}

</script>
<h2>지원하기</h2>
<form name="frm" action="wcupVol" method="POST" >
<input type="hidden" name="ewidx" value="${ewidx}" />
<hr>
<table width="100%">
<c:forEach items="${styleList }" var="si" varStatus="i">
<tr><td style="width:400px;">
<div style="vertical-align:top; height:20px;">
<input type="checkbox" id="pic${i.count}"  name="chk" value="${si.getSi_idx() }" onclick='checkOnlyOne(this)' />
</div>
<label for="pic${i.count}" onclick="if(navigator.appVersion.indexOf('MSIE') != -1){pic${i.count}".click()}">
<img src="resources/img/style_img/${si.getSi_img()}"  style="width:300px; height:400px;"/>
</label> 
</td></tr>
</c:forEach>
</table>

<div align="center">
<input type="submit" value="지원하기" />
<input type="button" value="취소하기" onclick="window.close();" />
</div>
</form>
<hr>