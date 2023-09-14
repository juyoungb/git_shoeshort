<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head_ad.jsp" %>
<%
//vo패키지에서 키워드 가져올려고함
RcmdSearch rcchk = new RcmdSearch();
String gender = rcchk.getGender(), season = rcchk.getRc_season(), age = rcchk.getRc_age(), rc_keyword = rcchk.getRc_keyword();

String [] arrGender = gender.split(",");
String [] arrSeason = season.split(",");
String [] arrAge= age.split(",");
String [] arrKeyword = rc_keyword.split(",");


%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<style>
#list, th, td {border:1px solid black;}
#fontBlue {color:blue;}
#fontRed {color:red;}
.input {width:100%;}
</style>

<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.4.js"></script>
<script>

function makeSch() {
	var frm = document.frm2;
	var sch = "";
		
	 	
	 var sarr = frm.size; // size라는 이름의 컨트롤들을 배열로 받아옴
	 var isFrist = true;   //size 체크박스들 중 첫번째로 선택한 체크박스인지 여부를 저장
	 for(var i = 0 ; i < sarr.length ; i++){
		if (sarr[i].value) {    
	    	if (isFrist){
	        	if (sch != "") sch += ",";   // 기존에 검색어가 있을경우 쉼표로 구분         
	         	isFrist = false;         
	         	sch += "s" + sarr[i].id + " " + sarr[i].value;
	      	} else {
	        	sch += ":" + sarr[i].id + " " + sarr[i].value;
	      	}
	    }
	 }   
	 
	 alert("sch :" +sch);
	 document.frm2.sch.value = sch;   
	 document.frm2.submit();
	 alert("등록이 되었습니다.")
}

function chkBox(name, checked) {
     var chks = document.getElementsByName(name);
    
     for (var i = 0; i < chks.length; i++) { 	
     	if (chks[0] == checked) {
        	chks[i].checked = false;
        }
     }
}

function chkDupId(piid) { //상품아이디가 이미 있는지 확인
	 if (piid.length == 7) {
	 	$.ajax({
			type : "POST", url : "./dupId", data : {"piid" : piid}, 
			success : function(chkRs) {
				var msg = "";
				if (chkRs == 0) {
					msg = "<span id='fontBlue'>사용할 수 있는 상품아이디 입니다.</span>";
					$("#piidChk").val("y");
				} else {
					msg = "<span id='fontRed'>사용중인 상품아이디 입니다.</span>";
					$("#piidChk").val("n");
				}
				$("#isMessage").html(msg);
			}
		});
	} else {
		$("#isMessage").text("상품아이디는 7자입니다. 예)CC10001	NN:나이키, CC: 크록스, DD:닥터마틴");
		$("#piidChk").val("n");
	}
}
</script>
</head>
<body>
<div style="margin:20px;" width="80%" align="center">
<h2 align="left" style="margin-left:10%">상품 등록</h2>
<form name="frm2" action="adProductFile" method="post" enctype="multipart/form-data">
<input type="hidden" name="sch" value=""> 
<table width="80%" cellpadding="10" cellspacing="0" id="list" class="mx-auto">
<tr>
<th>상품명아이디</th>
<td>
	<input type="text" name="piid" onkeyup="chkDupId(this.value);">
	<span id="isMessage"style="font-size:0.8em;"> 예)CC10001 &nbsp;&nbsp;&nbsp; NN:나이키, CC: 크록스, DD:닥터마틴</span>
</td>
</tr>
<tr>
<th>상품명</th>
<td>
	<input type="text" name="piname" placeholder="상품명 입력">
</td>
</tr>
<tr>
<th>성별</th>
<td>
<%
for(String st : arrGender){
%>	
	<input type="radio" name="gender" id="<%=st %>"  value="<%=st.charAt(0) %>" <%if(st.equals("men")) {%> checked="checked"<%} %>onclick="chkBox('gender', this)" class="bnt">
	<label for="<%=st %>"><%=st %></label>
<%} %>	
 </td>	
</tr>
<tr>
<th>카테고리</th>
<td>
	<label><input type="radio" name="ctgr" checked="checked" value="a">스니커즈</label>&nbsp;&nbsp;&nbsp;&nbsp;
	<label><input type="radio" name="ctgr" value="b">구두</label>&nbsp;&nbsp;&nbsp;&nbsp;
	<label><input type="radio" name="ctgr" value="c">샌들</label>
</td>
</tr>
<tr>
<th>이미지</th>
<td>
상품이미지1 : <input type="file" name="uploadFile1" multiple="multiple"><br>
상품이미지2 : <input type="file" name="uploadFile2" multiple="multiple"><br>
상품이미지3 : <input type="file" name="uploadFile3" multiple="multiple"><br>
상품 설명 이미지 : <input type="file" name="uploadFile4" multiple="multiple"><br>
</tr>
<tr>
<th>가격</th>
<td><input type="number" value="0" name="price">원</td>
</tr>
<tr>
<th>원가</th>
<td><input type="number" value="0" name="cost">원</td>
</tr>
<tr>
<th>원산지</th>
<td><input type="text"  name="com"></td>
</tr>
<tr>
<th>게시 여부</th>
<td>
	<label><input type="radio" name="isview" value="y" checked="checked">게시</label>&nbsp;&nbsp;&nbsp;&nbsp;
	<label><input type="radio" name="isview" value="n" >미게시</label>
</td>
</tr>
</table>
<div style="margin:20px;" id="app">
<table width="80%"  align="center">
<tr>
<th width="25%">사이즈</th>
<th width="25%">재고량</th>
<th width="25%">사이즈</th>
<th width="25%">재고량</th>
</tr>
<tr>
<td>225</td>
<td><input type="number" id="225" v-bind:value="defalut" name="size" class="input"></td>
<td>255</td>
<td><input type="number" id="255" v-bind:value="defalut"  name="size" class="input"></td>
</tr>

<tr>
<td>230</td>
<td><input type="number" id="230" v-bind:value="defalut" name="size" class="input"></td>
<td>260</td>
<td><input type="number" id="260" v-bind:value="defalut"  name="size" class="input"></td>
</tr>

<tr>
<td>235</td>
<td><input type="number" id="235" v-bind:value="defalut" name="size" class="input"></td>
<td>265</td>
<td><input type="number" id="265" v-bind:value="defalut" name="size" class="input"></td>
</tr>

<tr>
<td>240</td>
<td><input type="number" id="240" v-bind:value="defalut" name="size" class="input"></td>
<td>270</td>
<td><input type="number" id="270" v-bind:value="defalut" name="size" class="input"></td>
</tr>

<tr>
<td>245</td>
<td><input type="number" id="245" v-bind:value="defalut" name="size" class="input"></td>
<td>275</td>
<td><input type="number" id="275" v-bind:value="defalut" name="size" class="input"></td>
</tr>

<tr>
<td>250</td>
<td><input type="number" id="250" v-bind:value="defalut" name="size" class="input"></td>
<td>280</td>
<td><input type="number" id="280" v-bind:value="defalut" name="size" class="input"></td>
</tr>

<tr style="border: 0;">
<td colspan="4" align="center"   style="border: 0;">
	<input type="button" class="btn btn-outline-dark" value="등록" onclick="makeSch();">
	<input type="button" class="btn btn-secondary"  value="취소"  onclick="location.href='adProductProc';">
</td>
</tr>

 </table>
</div>
</div>
</form>

</div>
<script>
new Vue({
	el : "#app",
	data : {
		defalut:0
	}
});
</script>
</body>
</html>