<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
  body {
    font-family: Arial, sans-serif;
    background-color: #ffffff; /* 흰색 배경 설정 */
    margin: 0;
    padding: 0;
    color: #000000; /* 검은색 글씨 설정 */
  }

  #mypage_container {
    display: flex;
  }

  #mypage_tabs {
  	height:100%;
    width: 250px;
    background-color: white;
    color: black;
    padding: 30px;
  }

  #mypage_content {
    flex: 1;
    padding: 20px;
  }

  #mypage_banner h2 {
    text-align: left;
  }

  #mypage_list {
  	color: black;
    list-style: none;
    padding: 0;
  }

  #mypage_list li {
    margin-bottom: 10px;
    text-align:center;
  }

  #mypage_list li a {
    color: black;
    text-decoration: none;
    font-size: 16px;
    display: block;
    padding: 8px 10px;
    border-radius: 5px;
    text-align:left;
    margin: 10px;
  }

  #mypage_list li a:hover {
    font-weight: bold;
  }
</style>
<div align="center">
<div style="width:1300px">
  <div id="mypage_container">
	    <div id="mypage_tabs">
	      <div id="mypage_banner">&nbsp;&nbsp;
	        <h2>&nbsp;&nbsp;마이페이지</h2>
	      </div>
	      <ul id="mypage_list">
	        <li><a href="memberInfo">회원 정보 변경</a></li>
	        <li><a href="pointInfo">포인트 내역</a></li>
	        <li><a href="orderInfo">주문내역</a></li>
	        <li><a href="orderCart">장바구니</a></li>
	      </ul>
	 </div>
    <div id="mypage_content">
      <!-- 여기에 마이페이지 컨텐츠를 추가하면 됩니다. -->
      <!-- 예: 회원 정보 변경, 포인트 내역, 주문 내역, 장바구니 내용 등 -->
