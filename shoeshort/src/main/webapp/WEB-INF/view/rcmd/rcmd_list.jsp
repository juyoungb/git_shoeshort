<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head_fr.jsp" %>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
request.setCharacterEncoding("utf-8");
List<ProductInfo> rcmdList = (List<ProductInfo>)request.getAttribute("rcmdList");
PageInfo pageInfo = (PageInfo)request.getAttribute("pageInfo");
int rcnt = pageInfo.getRcnt(), cpage = pageInfo.getCpage(), bsize = pageInfo.getBsize(), pcnt = pageInfo.getPcnt();
RcmdSearch rcchk = new RcmdSearch();
String gender = rcchk.getPi_gubun(), season = rcchk.getPi_rc_season(), age = rcchk.getPi_rc_age(), keyword = rcchk.getPi_rc_keyword();

String [] arrGender = gender.split(",");
String [] arrSeason = season.split(",");
String [] arrAge= age.split(",");
String [] arrKeyword = keyword.split(",");

String sch = pageInfo.getSch();
if (sch != null && !sch.equals("")) {
	String[] arrSch = sch.split(",");
	for (int i = 0; i < arrSch.length; i++){
		char c = arrSch[i].charAt(0);
		if (c == 'g')	gender = arrSch[i].substring(1);
		if (c == 's')	season = arrSch[i].substring(1);
		if (c == 'a')	age = arrSch[i].substring(1);
		if (c == 'k')	keyword = arrSch[i].substring(1);
	}
}
%>
<head><script src="/docs/5.3/assets/js/color-modes.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="Mark Otto, Jacob Thornton, 그리고 Bootstrap 기여자들">
<meta name="generator" content="Hugo 0.115.4">
<link rel="canonical" href="https://getbootstrap.kr/docs/5.3/examples/album/">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@docsearch/css@3">
    <!-- Favicons -->
<link rel="apple-touch-icon" href="/docs/5.3/assets/img/favicons/apple-touch-icon.png" sizes="180x180">
<link rel="icon" href="/docs/5.3/assets/img/favicons/favicon-32x32.png" sizes="32x32" type="image/png">
<link rel="icon" href="/docs/5.3/assets/img/favicons/favicon-16x16.png" sizes="16x16" type="image/png">
<link rel="manifest" href="/docs/5.3/assets/img/favicons/manifest.json">
<link rel="mask-icon" href="/docs/5.3/assets/img/favicons/safari-pinned-tab.svg" color="#712cf9">
<link rel="icon" href="/docs/5.3/assets/img/favicons/favicon.ico">
<meta name="theme-color" content="#712cf9">
<script>
function makeSch(){
	// 검색폼의 조건을 쿼리스트링 sch의 값으로 만듦 
	var frm = document.frm;	var sch = "";

	var arr1 = frm.pi_gubun;	
	var isFirst = true;	
	for (var i = 0; i < arr1.length ; i++){
		if(arr1[i].checked){
			if (isFirst){	// 첫번째로 선택한 체크박스이면
				if (sch != "")	sch += ",";	// 기존에 검색어가 있을 경우 ','로 구분
				isFirst = false;
				sch += "g"+ arr1[i].value;
			}else {
				sch += ":" + arr1[i].value;
			}
		}
	}
	
	var arr2 = frm.pi_rc_season;	
	var isFirst = true;	
	for (var i = 0; i < arr2.length ; i++){
		if(arr2[i].checked){
			if (isFirst){
				if (sch != "")	sch += ",";	// 기존에 검색어가 있을 경우 ','로 구분
				isFirst = false;
				sch += "s"+ arr2[i].value;
			}else {
				sch += ":" + arr2[i].value;
			}
		}
	}
	
	var arr3 = frm.pi_rc_age;	
	var isFirst = true;	
	for (var i = 0; i < arr3.length ; i++){
		if(arr3[i].checked){
			if (isFirst){	
				if (sch != "")	sch += ",";	// 기존에 검색어가 있을 경우 ','로 구분
				isFirst = false;
				sch += "a"+ arr3[i].value;
			}else {
				sch += ":" + arr3[i].value;
			}
		}
	}
	
	var arr4 = frm.pi_rc_Keyword;
	var isFirst = true;		
	for (var i = 0; i < arr4.length ; i++){
		if(arr4[i].checked){
			if (isFirst){
				if (sch != "")	sch += ",";	// 기존에 검색어가 있을 경우 ','로 구분
				isFirst = false;
				sch += "k"+ arr4[i].value;
			}else {
				sch += ":" + arr4[i].value;
			}
		}
	}
	document.frm.sch.value = sch;
	document.frm.submit();		// 검색조건들을 가져가기 위해서 submit시킴
}

function handleCheckboxClick(groupName, clickedCheckbox) {
    var checkboxes = document.getElementsByName(groupName);

    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i] !== clickedCheckbox) {
            // 클릭된 체크박스가 아닌 나머지 체크박스는 선택을 해제
            checkboxes[i].checked = false;
        }
    }
}
function initSch(){
// 검색조건(상품명, 브랜드, 가격대)들을 모두 없애주는 함수
	location.href="rcmd";	
}
</script>
<style>
.bd-placeholder-img {
  font-size: 1.125rem;
  text-anchor: middle;
  -webkit-user-select: none;
  -moz-user-select: none;
  user-select: none;
}

@media (min-width: 768px) {
  .bd-placeholder-img-lg {
    font-size: 3.5rem;
  }
}

.b-example-divider {
  width: 100%;
  height: 3rem;
  background-color: rgba(0, 0, 0, .1);
  border: solid rgba(0, 0, 0, .15);
  border-width: 1px 0;
  box-shadow: inset 0 .5em 1.5em rgba(0, 0, 0, .1), inset 0 .125em .5em rgba(0, 0, 0, .15);
}

.b-example-vr {
  flex-shrink: 0;
  width: 1.5rem;
  height: 100vh;
}

.bi {
  vertical-align: -.125em;
  fill: currentColor;
}

.nav-scroller {
  position: relative;
  z-index: 2;
  height: 2.75rem;
  overflow-y: hidden;
}

.nav-scroller .nav {
  display: flex;
  flex-wrap: nowrap;
  padding-bottom: 1rem;
  margin-top: -1px;
  overflow-x: auto;
  text-align: center;
  white-space: nowrap;
  -webkit-overflow-scrolling: touch;
}

.btn-bd-primary {
  --bd-violet-bg: #712cf9;
  --bd-violet-rgb: 112.520718, 44.062154, 249.437846;

  --bs-btn-font-weight: 600;
  --bs-btn-color: var(--bs-white);
  --bs-btn-bg: var(--bd-violet-bg);
  --bs-btn-border-color: var(--bd-violet-bg);
  --bs-btn-hover-color: var(--bs-white);
  --bs-btn-hover-bg: #6528e0;
  --bs-btn-hover-border-color: #6528e0;
  --bs-btn-focus-shadow-rgb: var(--bd-violet-rgb);
  --bs-btn-active-color: var(--bs-btn-hover-color);
  --bs-btn-active-bg: #5a23c8;
  --bs-btn-active-border-color: #5a23c8;
}
.bd-mode-toggle {
  z-index: 1500;
    }
.page-link {color:black;}
a {color:black; text-decoration: none;}
</style>

  </head>
<body>
<br />
<h3 align="center">추천 검색</h3>
<form name="frm">
<table width="700" align="center">
<input type="hidden" name="sch" value="" />
<tr>
<th align="left">성별</th>
<th align="left">계절</th>
<th align="left">연령별</th>
<th align="left">키워드</th>
</tr>
<tr>
<td align="left">
<%
for (String st : arrGender) {
%>
    <input type="checkbox" name="pi_gubun" value="<%=st %>" <% if (st.equals(pageInfo.getSchG())) {%> checked="checked" <%} %>  onclick="handleCheckboxClick('pi_gubun', this)" />
    <label for="<%=st %>"><% if (st.equals("m")){st = "남자";} 
    else if (st.equals("w")){st = "여자";}	
    else if (st.equals("k")){st = "키즈";} %><%=st %></label><br />
<%
} 
%>
</td>
<td align="left">
<%
for (String st : arrSeason) {
%>
    <input type="checkbox" name="pi_rc_season" value="<%=st %>" <% if (st.equals(pageInfo.getSchS())) {%> checked="checked" <%} %> onclick="handleCheckboxClick('pi_rc_season', this)" />
    <label for="<%=st %>"><%=st %></label><br />
<%
}
%>
</td>
<td align="left">
<%
for (String st : arrAge) {
%>
    <input type="checkbox" name="pi_rc_age" value="<%=st %>" <% if (st.equals(pageInfo.getSchA())) {%> checked="checked" <%} %> onclick="handleCheckboxClick('pi_rc_age', this)" />
    <label for="<%=st %>"><%=st %></label><br />
<%
} 
%>
</td>
<td align="left">
<%
for (String st : arrKeyword) {
%>
    <input type="checkbox" name="pi_rc_Keyword" value="<%=st %>" <% if (st.equals(pageInfo.getSchK())) {%> checked="checked" <%} %> onclick="handleCheckboxClick('pi_rc_Keyword', this)" />
    <label for="<%=st %>"><%=st %></label><br />
<%
} 
%>
</td></tr>
</table>
<br />
<p align="center"><input type="button"  class="btn btn-outline-dark" value="추천 검색" onclick="makeSch();"/>&nbsp;&nbsp;
<input type="button"  class="btn btn-outline-dark" value="조건 초기화" onclick="initSch();"/></p>
</form>
<br />
<div class="album py-5 bg-body-tertiary" >
    <div class="container">

	<div class="row row-cols-4 g-4"><!-- row-cols-md가 한 열에 카드 수 컬럼 수 조절 -->
<%
int i = 0;
for(i = 0 ; i < rcmdList.size(); i++) {
if (i % 4 == 0) out.println("<tr>");
ProductInfo pi = rcmdList.get(i);
String price = pi.getPi_price() + "원";      
if (pi.getPi_dc() > 0) {  //할인율이 있으면
  price = Math.round(pi.getPi_price() * (1 - pi.getPi_dc()) )+ "원"; //실제 판매가
  price = "<del>" + pi.getPi_price() + "</del>&nbsp;&nbsp;&nbsp;" + price;
}
%>
	<div class="col-2" style="margin : 50px;">
	  <div class="card shadow-sm">
	  	<a href="productView?piid=<%=pi.getPi_id()%>">
	    <img src="resources/img/product/<%=pi.getPi_img1() %>" width="100%" style="height: 11rem; object-fit:cover;" /></a>
	    <div class="card-body">
	      <p class="card-text" style="font-size:80%;"><%=pi.getPi_name() %></p>
	      <div class="d-flex justify-content-between align-items-center">
	        	<div width="50%" style="font-family: inherit;"><%=price %></div>
	         </div>
	    </div>
	  </div>
	</div>
<%
}
%>
      </div>
    </div>
  </div>
<!-- 페이징 영역 시작 -->
<table width="100%" align="center" cellpadding="6" border="0">
<tr>
<td align="center" colspan="7">
<% 
if (rcnt > 0) {
	String link = "rcmdList?1=1&cpage=";
%>
	<nav aria-label="Page navigation example" style="text-align:center; width:200px;">
		<ul class="pagination">
<%
	if (cpage == 1) {
%>
			<li class="page-item"><a class="page-link" href="#">이전</a></li>
<%
	} else {
%>
			<li class="page-item"><a class="page-link" href="<%=link + (cpage - 1)%>">이전</a></li>
<% 
	}
	int spage = (cpage -1) / bsize * bsize + 1; //현재 블록에서의 시작 페이지 번호
	int j = 0;
	for (i = 1, j = spage ; i<= bsize && j <= pcnt ; i++, j++){
		// i : 블록에서 보여줄 페이지의 개수만큼 루프를 돌릴 조건으로 사용되는 변수
		// j :실제 출력할 페이지 번호로 전체 페이지 개수(마지막 페이지 번호)를 넘지 않게 사용해야 함
		if (cpage == j){
%>
			<li class="page-item"><a class="page-link" href="#"><%=j %></a></li>
<%
		} else {
%>			<li class="page-item"><a class="page-link" href="<%=link + j%>"><%=j %></a></li> <%
		}
	}
	if (cpage == pcnt){
%>
			<li class="page-item"><a class="page-link" href="#">다음</a></li>
<%
	} else{ 
%>
			<li class="page-item"><a class="page-link" href="<%=link +(cpage + 1) %>">다음</a></li>
<%
	}
%>
		</ul>
	</nav>
<%
}
%>
</td>
</tr>
</table>
<!-- 페이징 영역 종료 -->
</main>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
</body>
</html>
