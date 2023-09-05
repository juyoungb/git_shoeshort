<%@page import="org.apache.catalina.startup.SetAllPropertiesRule"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head_fr.jsp" %>
<%@ page import="java.util.*" %>
<%
request.setCharacterEncoding("utf-8");
List<StyleInfo> styleList = (List<StyleInfo>)request.getAttribute("styleList");
PageInfo pageInfo = (PageInfo)request.getAttribute("pageInfo");
int rcnt = pageInfo.getRcnt(), cpage = pageInfo.getCpage(), bsize = pageInfo.getBsize(), pcnt = pageInfo.getPcnt();

%>
<head><script src="/docs/5.3/assets/js/color-modes.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="Mark Otto, Jacob Thornton, 그리고 Bootstrap 기여자들">
<meta name="generator" content="Hugo 0.115.4">
<title>ShoeShort</title>

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
<div id="tmp">
<div><h2 align="center">STYLE</h2></div>
<div align="right" style="margin-right: 100px;"><a href="style?cpage=1&ob=a">인기순</a> | <a href="style?cpage=1&ob=b">최신순</a></div>
</div>
<div class="album py-5 bg-body-tertiary" >
    <div class="container">

	<div class="row row-cols-4 g-4"><!-- row-cols-md가 한 열에 카드 수 컬럼 수 조절 -->
<%
	int i = 0;
	for(i = 0;i < styleList.size();i++) {
		StyleInfo si = styleList.get(i);
%>
        <div class="col">
          <div class="card shadow-sm">
          	<a href="styleView?siidx=<%=si.getSi_idx() %>&piid=<%=si.getPi_id() %>">
            <img src="resources\img\style_img\<%=si.getSi_img() %>" width="100%" height="350" /></a>
            <div class="card-body">
              <p class="card-text"><a href="memStyle?miid=<%=si.getMi_id() %>" style="color:black; text-decoration-line: none;"><%=si.getMi_id() %></a></p>
              <div class="d-flex justify-content-between align-items-center">
                	<div width="50%"><%=si.getSi_content() %></div>
                <div align="right"><img src="resources\img\style_img\style_good02.png" width="20px" height="20px"/><%=si.getSi_good() %></div>
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
	String link = "styleList?1=1&cpage=";
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
