<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script> 
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script> 
<script>
$(function() {
	$.datepicker.regional['ko'] = {
		closeText: '닫기',
		prevText: '이전달',
		nextText: '다음달',
		currentText: '오늘',
		monthNames: ['1월','2월','3월','4월','5월','6월',
		'7월','8월','9월','10월','11월','12월'],
		monthNamesShort: ['1월','2월','3월','4월','5월','6월',
		'7월','8월','9월','10월','11월','12월'],
		dayNames: ['일','월','화','수','목','금','토'],
		dayNamesShort: ['일','월','화','수','목','금','토'],
		dayNamesMin: ['일','월','화','수','목','금','토'],
		//buttonImage: "/images/kr/create/btn_calendar.gif",
		buttonImageOnly: true,
		// showOn :"both",
		weekHeader: 'Wk',
		dateFormat: 'yy-mm-dd',
		firstDay: 0,
		isRTL: false,
		duration:200,
		showAnim:'show',
		showMonthAfterYear: false
		// yearSuffix: '년'
	};
	$.datepicker.setDefaults($.datepicker.regional['ko']);

	$( "#el_sdate" ).datepicker({
		//defaultDate: "+1w",
		changeMonth: true,
		//numberOfMonths: 3,
		dateFormat:"yy-mm-dd",
		onClose: function( selectedDate ) {
			//$( "#eday" ).datepicker( "option", "minDate", selectedDate );
		}
	});
	$( "#el_edate" ).datepicker({
		//defaultDate: "+1w",
		changeMonth: true,
		//numberOfMonths: 3,
		dateFormat:"yy-mm-dd",
		onClose: function( selectedDate ) {
			//$( "#sday" ).datepicker( "option", "maxDate", selectedDate );
		}
	});

});
</script>
</head>
<body style="padding:20px;">
<div align="center">
<h3>행운 숫자 찾기${kind == 'in' ? '등록':'수정'}</h3>
<form name="frm" method="post" >
<input type="hidden" name="kind" value="${kind}"/>
<input type="hidden" name="elidx" value="${el.getEl_idx()}"/>
<table>
<tr><th>제목</th><td><input style="width:65%" type="text" name="title" value="${ul.getEl_title()}" /></td></tr>
<tr><th>상품명</th><td><input style="width:65%" type="text" name="piid" value="${ul.getPi_id()}" /></td></tr>
<tr><th>참여가능 날짜</th>
<td>
	<input type="text" style="width:20%" name="el_sdate"  id="el_sdate" value="${ul.getEl_sdate() }" size="10" class="ipt" /> 
	<input type="time" name="stime" value="${ul.getStime()}"> ~
	<input type="text" style="width:20%" name="el_edate"  id="el_edate" value="${ul.getEl_edate() }" size="10" class="ipt" /> 
	<input type="time" name="etime" value="${ul.getEtime()}">
</td></tr>
<tr>
<th>금액</th>
<td>
	<input type="number" name="min" value="${ul.getEl_min_price()}"> ~
	<input type="number" name="max" value="${ul.getEl_max_price()}">
</td>
</tr>
<tr>
<th>당첨자</th>
<td>
	<input type="text" name="mi_id" value="${ul.getMi_id()}"> 
</td>
</tr>
<tr>
<th>당첨 금액</th>
<td>
	<input type="number" name="final_price" value="${ul.getEl_final_price()}">
</td>
</tr>
<tr>
<th>게시여부</th>
<td>
<select name="isview" style="width:40%">
	<option value="a" <c:if test="${ul.getEl_isview() == 'a' }">selected</c:if>>종료</option>
	<option value="y" <c:if test="${ul.getEl_isview() == 'y' }">selected</c:if>>진행중</option>
	<option value="n" <c:if test="${ul.getEl_isview() == 'n' }">selected</c:if>>미게시</option><!-- 투표 -->
</select>
</td>
</tr>
</table><br>
<input type="submit" class="btn btn-dark" style="width:20%" value="${kind == 'in' ? '등록':'수정'}" />&nbsp;&nbsp;&nbsp;
<input type="button" class="btn btn-outline-dark" onclick="window.close();" style="width:20%" value="취소" />
</form>
</div>
</body>
</html>