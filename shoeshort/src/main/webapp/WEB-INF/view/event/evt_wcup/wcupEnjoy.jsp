<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page import="vo.*" %>
<%
MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${ewi.getEw_title()}</title>
<style>
      html,
      body {
        width: 100%;
        height: 100%;
      }
      .container {/* . :class # :id */
      	text-align:center;
        width: 1400px;
        height: 700px;
        background: white;
        z-index:13;
      }
</style>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
</head>
<body>


<div class="container" align="center">
<c:if test="${ewi.getStage() != (ewi.getEw_rule())}" >
<h2>${ewi.getEw_title()} </h2>
<a href="wcupEnjoy?uid=${ewi.getMi_id()}&ewrule=${ewi.getEw_rule()}&ewidx=${ewi.getEw_idx()}&title=${ewi.getEw_title() }&stage=${ewi.getStage()+1 }&sys=${ewi.getSys()}&rand=${ewi.getRand()}&choice=1">
<img src="resources/img/style_img/${ewi.getImg1()}" style="height:700px; width:650px;"/></a>
<!-- <div style="position:absolute; top:300px; left:560px; z-index:1;"> -->
<div style="position:absolute; top:300px; left:560px; z-index:1;"><img src="resources/img/icon/vs.gif" style="height:300px; width:300px;disabled:disabled;"/></div>

<a href="wcupEnjoy?uid=${ewi.getMi_id() }&ewrule=${ewi.getEw_rule()}&ewidx=${ewi.getEw_idx()}&title=${ewi.getEw_title() }&stage=${ewi.getStage()+1 }&sys=${ewi.getSys()}&rand=${ewi.getRand()}&choice=2">
<img src="resources/img/style_img/${ewi.getImg2()}" style="height:700px; width:600px;"/></a>
</c:if>
<c:if test="${ewi.getStage() == (ewi.getEw_rule())}" >
<h2>${ewi.getEw_title()}(우승)</h2>
<a href=""><img src="resources/img/style_img/${ewi.getImg1()}" style="height:600px; width:500px;"/></a>
<form action="wcupWin" method="POST" >
<input type="hidden" name="ewidx" value="${ewi.getEw_idx() }"/>
<input type="hidden" name="rule" value="${ewi.getEw_rule() }"/>
<input type="hidden" name="winner" value="${ewi.getWinner() }"/>
<br>
<input type="submit" class="btn btn-success" value="확정하기" />
<input type="button" class="btn btn-outline-secondary" onclick="window.close();opener.location.reload(); " value="나가기" />
</form>
</c:if>
</div>
</body>
</html>