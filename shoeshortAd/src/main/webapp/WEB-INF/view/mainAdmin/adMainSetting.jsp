<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head_ad.jsp" %>
<%
request.setCharacterEncoding("utf-8");
%>
<h2>메인 관리</h2>
<hr>
<h2>메인 동영상</h2>
<hr>
<span></label><input type="button" value="업로드"  onclick="popUp('adMainBox?kind=v','','700','440');" />
<br>
<h2>슬라이더 설정</h2>
<hr>
<label></label><input type="button" value="이미지 추가"  onclick="popUp('adMainBox?kind=i','','700','440');" />

<%@ include file="../_inc/inc_foot_ad.jsp" %>
