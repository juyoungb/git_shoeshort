<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="resources/js/jquery-3.6.4"></script>
</head>
<body>
<h2>월드컵시작</h2>
<%-- ${getWcup.getMi_id() }
${getWcup.getEwj_seq() }
${getWcup.getEwj_ing() }
<c:forEach items="${detailList}" var="dl">
<img src="resources/img/style/${dl.getSi_img()}" />
</c:forEach> --%>
${el.getEw_title() }
<!--  [시작하기]- 참여한 여부를 리턴해줌 1.이미참여하였씁니다. 2. 시작하겠습니다. --> 
<br>
<input type="button" onclick="location.href='wcupEnjoy?uid=<%=loginInfo.getMi_id() %>&ewrule=${el.getEw_rule()}&ewidx=${ewidx}&title=${el.getEw_title() }&rand=0&seq=1&sys=${el.getEw_rule()==4 ? "1111":"11111111" }'" value="시작하기" />
<input type="button" onclick="window.close();opener.location.reload(); " value="나가기" />
<input type="button" onclick="" value="전체 순위 보기" />

<!--
db pk 변경  wcup
 1. [월드컵 참여하기]- 새 창 
2.스타일 이미지 
 [시작하기]- 참여한 여부를 리턴해줌 1.이미참여하였씁니다. 2. 시작하겠습니다. 
2-1. 랜덤으로 생성
8강 1/4 2/4 3/4 4/4 1/2 2/2  결승전 height:100% width50%
스타일 월드컵 4강 1/2  2/2  - 결승전 - 
11 11                             01 10
제목 
[현재 순위 보기]
전체 아이디 우승비율  -->
</body>
</body>
</html>
