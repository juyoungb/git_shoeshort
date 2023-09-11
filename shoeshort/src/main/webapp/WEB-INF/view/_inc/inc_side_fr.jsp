<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
  body {
    font-family: Arial, sans-serif;
    background-color: #ffffff; /* 흰색 배경 설정 */
    margin: 0;
    padding: 0;
    color: #000000; /* 검은색 글씨 설정 */
  }

  #side_container {
    display: flex;
  }

  #side_tabs {
  	height:100%;
    width: 200px;
    background-color: white;
    color: black;
    padding: 10px;
  }

  #side_content {
    flex: 1;
    padding: 20px;
  }

  #side_banner h2 {
    text-align: left;
  }

  #side_list {
  	color: black;
    list-style: none;
    padding: 0;
  }

  #side_list li {
    margin-bottom: 10px;
    text-align:center;
  }

  #side_list li a {
    color: black;
    text-decoration: none;
    font-size: 16px;
    display: block;
    padding: 8px 10px;
    border-radius: 5px;
  }

  #side_list li a:hover {
    font-weight: bold;
  }
</style>
<br />
  <div id="side_container">
    <div id="side_tabs">
      <div id="side_banner">
        <h2 style="text-align:center; margin-left:7%;">고객 센터</h2>
      </div>
      <ul id="side_list">
        <li><a href="noticeList">공지 사항</a></li>
        <li><a href="faqList">자주 묻는 질문</a></li>
      </ul>
    </div>
    <div id="side_content">
      <!-- 여기에 마이페이지 컨텐츠를 추가하면 됩니다. -->
      <!-- 예: 회원 정보 변경, 포인트 내역, 주문 내역, 장바구니 내용 등 -->
