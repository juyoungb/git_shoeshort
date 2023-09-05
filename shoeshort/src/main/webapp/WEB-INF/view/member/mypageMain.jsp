<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../_inc/inc_head_fr.jsp" %>
<%@ include file="../_inc/inc_mypage.jsp" %>
<h2>마이페이지 메인</h2>
<div style="height:300px; outline: solid 1px black;">
<%=loginInfo.getMi_name() %>님 환영 합니다. <br>
잔여포인트<%=loginInfo.getMi_point() %> <br>
회원가입일<%=loginInfo.getMi_date() %><br>

</div><br>
<div style="height:300px; outline: solid 1px black;">

</div>
<%@ include file="../_inc/inc_foot_fr.jsp" %>