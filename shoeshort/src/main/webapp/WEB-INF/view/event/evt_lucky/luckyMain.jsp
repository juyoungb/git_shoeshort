<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.*" %>
<%@ page import="java.time.*" %>
<%@ page import="vo.*" %>
<%@ include file="../../_inc/inc_head_fr.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
    .button-wrapper {
        display: flex;
        justify-content: center;
    }

    /* 추가한 스타일 */
    .center-links {
        display: flex;
        justify-content: center;
    }
    body {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 0;
    background-color: #f5f5f5;
	}
</style>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>

</head>
<body class="p-3 m-0 border-0 bd-example m-0 border-0 bd-example-row">

<form name="frm">
    <div class="mb-5">
        <img src="resources/img/product/header.png" width="100%" height="850px">
    </div> 
    <div class="row row-cols-6">
        <div class="col-12 mb-5">
            <p align="center"> *가격을 맞춰주세요!(익일 9:00AM기준)<br>
            * 참여 가능 시간 : 오전9시~오전10시<br>
            * 당첨자 발표 : 익일 오전 9시 자동 공지 (당첨자 발표 공지사항에서 발표)<br>
            * 가격 기준 : 제일 낮은 금액 중 중복되지 않는 금액을 입력하신 분<br>
            * 당첨자가 나오지 않을 시 무효처리 됩니다.<br>
            * 응모 포인트는 500p 소요 됩니다.
            </p>
        </div>  
    </div>  
    <c:forEach var="el" items="${evtList }" >        
        <div class="row">
            <div class="col mb-5 mt-5">      
            </div>      
            <div class="col-3 mb-5 mt-5">
                 <div class="card shadow">
                        <img  src="resources/img/product/${el.getPi_img1() }" class="bd-placeholder-img card-img-top" width="100px" height="350px" ></img>
                    <div class="card-body">
                        <p class="card-text">${el.getPi_name() }</p>
                    <div class="d-flex justify-content-between align-items-center" width="100%" height="100%">
                        <!-- 변경한 부분: 링크의 클래스에 center-links 추가 -->
                     	<div class="d-grid gap-2 col-6 mx-auto" >
                            <a href="luckyView?piid=${el.getPi_id()}" class="btn btn-dark btn-block" >참여하기</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col mb-5 mt-5">     
        </div>  
    </div>
    </c:forEach> 
</form>
</body>
</html>
