<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../_inc/inc_head_fr.jsp" %>
<%@ include file="../_inc/inc_mypage.jsp" %>
<style>
table{
	width:100%;
}
</style>
<script src="resources/js/memInfoJs.js"></script>
<br>
<h2>내 정보 변경</h2><br>
<table>
<tr>
	<td colspan="4"><strong>이름 :</strong> ${mi.getMi_name() }(${mi.getMi_id()})</td><td>
</tr>
<tr>
	<td colspan="4"><hr></td>
</tr>
<tr>
<td>
<strong>비밀번호 변경</strong>
</td>
<td colspan="3" align="right">
<input class="btn btn-outline-secondary" onclick="popUp('changeForm?kind=pw','','500','300');" type="button" value="변경"/>
</td>
</tr>
<tr>
<tr><td colspan="4"><hr></td></tr>
<td width="15%">
<strong>이메일</strong> 
<br>${mi.getMi_email()}  
</td>
<td  align="left">
<input class="btn btn-outline-secondary" onclick="popUp('changeForm?kind=email','','500','300');" type="button" value="변경"/>
</td>
<td align="right">
<strong>휴대폰 전화번호</strong>&nbsp;&nbsp;&nbsp;
 <br>${mi.getMi_phone() } 
&nbsp;&nbsp;&nbsp;
</td>
<td width="5%" align="right">
<input class="btn btn-outline-secondary" onclick="popUp('changeForm?kind=phone','','500','300');" type="button" value="변경"/>
</td>
</tr>
<tr><td colspan="4"><hr></td></tr>
<tr>
<td colspan="2"><span id="miTitle">주소록</span></td>
<td colspan="2" align="right">
<input  class="btn btn-secondary" onclick="popUp('addrForm?kind=in&n=0&u=${mi.getMi_id()}','','500','640');" type="button" value="+ 배송지 추가"/>
</td>
</tr>
</table>
<br>
<table>
<c:forEach items="${ma}" var="m">
  <tr>
    <th align="left">${m.getMa_name() } ${m.getMa_basic() == 'y' ? "기본배송지":"배송지" }</th>
  </tr>
  <tr>
    <td>
      ${m.getMa_phone() }<br>
      (${m.getMa_zip() })
      ${m.getMa_addr1() }
      ${m.getMa_addr2() }
     </td>
     <td align="right">
      <input class="btn btn-outline-secondary" onclick="popUp('addrForm?kind=up&n=${m.getMa_idx()}&u=${mi.getMi_id()}','','500','640');" type="button" value="수정"/>
      &nbsp;&nbsp;&nbsp;&nbsp;
      <c:if test="${m.getMa_basic() != 'y' }">
        <input class="btn btn-outline-secondary" type="button" onclick="delAddr(${m.getMa_idx()})" value="삭제"/> &nbsp;&nbsp;&nbsp;&nbsp;
      </c:if>
    </td>
    <tr><td colspan="2"><hr></td></tr>
</c:forEach>
</table>
</div>
</div>
<%@ include file="../_inc/inc_foot_fr.jsp" %>