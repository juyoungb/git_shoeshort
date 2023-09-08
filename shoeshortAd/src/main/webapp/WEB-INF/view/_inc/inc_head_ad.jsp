<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.*" %>
<%@ page import="java.time.*" %>
<%@ page import="vo.*"%>
<%
AdminInfo loginInfo = (AdminInfo)session.getAttribute("loginInfo");
boolean isLogin = false;
if(loginInfo != null){
	isLogin = true;
}
String aiid = loginInfo.getAi_id();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Coding by CodingNepal | www.codingnepalweb.com -->
<html lang="en" dir="ltr">
  <head>
    <meta charset="UTF-8">
    <title>shoeshortAd</title>
    <style>@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap');</style>
	<link rel="stylesheet" href="resources/css/indexCss.css">
    <!-- Boxicons CDN Link -->
    <link href='https://unpkg.com/boxicons@2.0.7/css/boxicons.min.css' rel='stylesheet'>
     <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	<script src="resources/js/jquery-3.6.4.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script> 
	<script src="resources/js/calendar.js"></script>
	<script src="resources/js/popUpJs.js"></script>
</head>
<body>
  <div class="sidebar close">
    <div class="logo-details">
      <i class='bx bxl-c-plus-plus'></i>
      <span class="logo_name"><%=loginInfo.getAi_name() %>님</span>
    </div>
    <ul class="nav-links">
      <li>
        <div class="iocn-link">
          <a href="allMem">
            <i class='bx bx-collection' ></i>
            <span class="link_name">회원관리</span>
          </a>
          <i class='bx bxs-chevron-down arrow' ></i>
        </div>
        <ul class="sub-menu">
          <li><a class="link_name" href="allMem">회원 관리</a></li>
          <li><a href="allMem">전체 회원 관리</a></li>
          <li><a href="pointMem">포인트 관리</a></li>
        </ul>
      </li>
      <li>
        <div class="iocn-link">
          <a href="adProductList">
            <i class='bx bx-collection' ></i>
            <span class="link_name">상품 관리</span>
          </a>
          <i class='bx bxs-chevron-down arrow' ></i>
        </div>
        <ul class="sub-menu">
          <li><a class="link_name" href="#">상품 관리</a></li>
          <li><a href="adProductList">상품 조회</a></li>
          <li><a href="adProductProc">상품 등록</a></li>
          <li><a href="#">상품 수정</a></li>
        </ul>
      </li>
      <li>
        <div class="iocn-link">
          <a href="adOrderList">
            <i class='bx bx-book-alt' ></i>
            <span class="link_name">주문 관리</span>
          </a>
          <i class='bx bxs-chevron-down arrow' ></i>
        </div>
        <ul class="sub-menu">
          <li><a class="link_name" href="adOrderList">주문 관리</a></li>
        </ul>
      </li>
      <li>
        <a href="adStyle">
          <i class='bx bx-pie-chart-alt-2' ></i>
          <span class="link_name">스타일 관리</span>
        </a>
        <ul class="sub-menu blank">
          <li><a class="link_name" href="adStyle">스타일 관리</a></li>
        </ul>
      </li>
      <li>
        <div class="iocn-link">
          <a href="adNoticeList">
            <i class='bx bx-collection' ></i>
            <span class="link_name">고객센터</span>
          </a>
          <i class='bx bxs-chevron-down arrow' ></i>
        </div>
        <ul class="sub-menu">
          <li><a class="link_name" href="adNoticeList">고객센터</a></li>
          <li><a href="adNoticeList">공지사항</a></li>
          <li><a href="adFaqList">FAQ</a></li>
        </ul>
      </li>
      <li>
        <div class="iocn-link">
          <a href="#">
            <i class='bx bx-plug' ></i>
            <span class="link_name">이벤트 관리</span>
          </a>
          <i class='bx bxs-chevron-down arrow' ></i>
        </div>
        <ul class="sub-menu">
          <li><a class="link_name" href="#">이벤트 관리</a></li>
          <li><a href="adEvtWcupMain">스타일 월드컵 관리</a></li>
          <li><a href="adLuckyList">행운의 숫자 찾기</a></li>
        </ul>
      </li>
      <li>
        <a href="adMainSet">
          <i class='bx bx-compass' ></i>
          <span class="link_name">메인 관리</span>
        </a>
        <ul class="sub-menu blank">
          <li><a class="link_name" href="adMainSet">메인 관리</a></li>
        </ul>
      </li>
      <li>
        <a href="logout">
          <i class='bx bx-history'></i>
          <span class="link_name">logout</span>
        </a>
        <ul class="sub-menu blank">
          <li><a class="link_name" href="logout">logout</a></li>
        </ul>
      </li>
      <li>
        <a href="adStore">
          <i class='bx bx-cog' ></i>
          <span class="link_name">매장관리</span>
        </a>
        <ul class="sub-menu blank">
          <li><a class="link_name" href="adStore">매장관리</a></li>
        </ul>
      </li>
</ul>
  </div>
  <section class="home-section">
    <div class="home-content">
      <i class='bx bx-menu' ></i>
	<a href="/shoeshortAd/" style="color:black;text-decoration:none;"><span class="text">ShoeShort</span></a>
    </div>

    