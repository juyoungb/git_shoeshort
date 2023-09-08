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
<c:forEach begin="1" end="${el.getEw_rule()}" var="i">
<c:set var="sys" value="${sys}${1}"/>
</c:forEach>
<input type="button" onclick="location.href='wcupEnjoy?uid=<%=loginInfo.getMi_id() %>&ewrule=${el.getEw_rule()}&rand=${el.getRand()}&ewidx=${ewidx}&title=${el.getEw_title() }&stage=1&sys=${sys}'" value="시작하기" />
<input type="button" onclick="window.close();opener.location.reload(); " value="나가기" />
<input type="button" onclick="" value="전체 순위 보기" />

</body>
</body>
</html>
