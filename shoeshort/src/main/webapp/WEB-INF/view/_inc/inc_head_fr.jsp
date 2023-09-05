<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.*" %>
<%@ page import="java.time.*" %>
<%@ page import="java.net.*"%>
<%@ page import="vo.*"%>
<%!
public static String maskMiddle(String input) {
    int length = input.length();
    
    if (length <= 2) {
        return input;
    }
    
    int startIndex = (length - 1) / 2;
    int endIndex = length % 2 == 0 ? startIndex + 1 : startIndex + 2;

    StringBuilder masked = new StringBuilder(input);
    for (int i = startIndex; i < endIndex; i++) {
        masked.setCharAt(i, '*');
    }

    return masked.toString();
}
%>
<%
String loginUrl = request.getRequestURI();
//System.out.println(loginUrl); 
if(request.getQueryString() != null) loginUrl+="?"+ URLEncoder.encode(request.getQueryString().replace('&','~'),"UTF-8");
MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");
boolean isLogin = false;
String mypagesrc = "login", cartsrc="login";
if(loginInfo != null){
    isLogin = true;
    mypagesrc = "memberInfo";
    cartsrc = "orderCart";
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.4.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/resources/js/date_change.js"></script>
<script src="resources/js/popUpJs.js"></script>
<title>shoeShort</title>
<link rel="stylesheet" href="resources/css/incHeadStyle.css">
</head>
<body>
<div id="header" align="center">
<div id="top_inner" align="right">
  <ul id="top_list">
    <li><a href="storeInfo">매장찾기</a></li>
    <li><a href="noticeList">고객센터</a></li>
    <li><a href="<%=mypagesrc %>">마이페이지</a></li>
    <li><a href="<%=cartsrc %>">장바구니</a></li>
    <% if (!isLogin) { %>
    <li><a href="login?url=<%=request.getAttribute("url")%>">로그인</a></li>
    <% } else { %>
    <li>(<%=maskMiddle(loginInfo.getMi_name()) %>님)<a href="logout?url=<%=request.getAttribute("url")%>">로그아웃</a></li>
    <% } %>
  </ul>
</div>
<div id="brand" onmouseover="hideAllSubmenu()">
  <a href="/shoeshort/">
    <img src="resources/img/logo.png" alt="Logo" class="logo" />
  </a>
</div>
<div id="center_wrap">
<div id="center_inner"   >
  <ul id="center_list" class="main_tabs">
    <li onmouseover="hideAllSubmenu()"><a href="newProduct">NEW</a></li>
    <li >
      <a href="productList?g=m" onmouseover="showEventSubmenu(1)" >MEN</a>
      <div class="small_tab"  id="event_submenu_1">
        <div align="left"style="width:150px;" onmouseover="showEventSubmenu(1)"  onmouseout="hideEventSubmenu(1)">
          <a href="">스니커즈</a><br>
          <a href="">구두</a><br>
          <a href="">슬리퍼</a>
        </div>
      </div>      
    </li>  
    <li>
      <a href="productList?g=w" onmouseover="showEventSubmenu(2)">WOMEN</a>
      <div class="small_tab" id="event_submenu_2">
        <div align="left"style="width:150px;"  onmouseover="showEventSubmenu(2) "  onmouseout="hideEventSubmenu(2)">
          <a href="">스니커즈</a>
          <a href="">구두</a>
          <a href="">슬리퍼</a>
        </div>
      </div>      
    </li>
    <li>
       <a href="productList?g=k" onmouseover="showEventSubmenu(3)" >KIDS</a>
      <div class="small_tab"  id="event_submenu_3">
        <div align="left"style="width:150px;"  onmouseover="showEventSubmenu(3)"  onmouseout="hideEventSubmenu(3)">
          <a href="">스니커즈</a>
          <a href="">구두</a>
          <a href="">슬리퍼</a>
        </div>
      </div>
    </li>
<li onmouseover="hideAllSubmenu()"><a href="rcmd" >추천검색</a></li>
    <li  onmouseover="hideAllSubmenu()"><a href="style" >STYLE</a></li>
    <li>
      <a onmouseover="showEventSubmenu(4)" >EVENT</a>
      <div class="small_tab" id="event_submenu_4">
         <div align="left"style="width:180px;" onmouseover="showEventSubmenu(4)"  onmouseout="hideEventSubmenu(4)">
          <a style="font-size:14px;" href="wcup">스타일 월드컵</a><br>
          <a style="font-size:14px;" href="luckyMain">행운 번호 찾기</a>
        </div>
      </div>
    </li>
  </ul>
</div>
</div>
</div>

<script>
function showEventSubmenu(idx) {
  var eventSubmenu = document.getElementById("event_submenu_"+idx);
  //eventSubmenu.style.display = "block";
  eventSubmenu.style.display = "block";  
  //alert(idx);
  for(var i=1; i<5; i++){
	  eventSubmenu = document.getElementById("event_submenu_"+i); 
	  if(idx != i){
		  if(eventSubmenu.style.display == "block"){
			  //alert(idx);
			  console.log(idx);
			  eventSubmenu.style.display = "none";  
		  }		  
	  }   
   } 
 }

function hideEventSubmenu(idx) {
  var eventSubmenu = document.getElementById("event_submenu_"+idx);
  eventSubmenu.style.display = "none";  
}
function hideAllSubmenu() { 
	  for(var i=1; i<5; i++){
		var eventSubmenu = document.getElementById("event_submenu_"+i);
	  	eventSubmenu.style.display = "none"; 
	  }
}
</script>
<div id="indexall" >