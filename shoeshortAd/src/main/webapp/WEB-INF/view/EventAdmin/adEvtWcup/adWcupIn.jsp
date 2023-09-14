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

	$( "#ew_csdate" ).datepicker({
		//defaultDate: "+1w",
		changeMonth: true,
		//numberOfMonths: 3,
		dateFormat:"yy-mm-dd",
		onClose: function( selectedDate ) {
			//$( "#eday" ).datepicker( "option", "minDate", selectedDate );
		}
	});
	$( "#ew_cedate" ).datepicker({
		//defaultDate: "+1w",
		changeMonth: true,
		//numberOfMonths: 3,
		dateFormat:"yy-mm-dd",
		onClose: function( selectedDate ) {
			//$( "#sday" ).datepicker( "option", "maxDate", selectedDate );
		}
	});
	$( "#ew_vsdate" ).datepicker({
		//defaultDate: "+1w",
		changeMonth: true,
		//numberOfMonths: 3,
		dateFormat:"yy-mm-dd",
		onClose: function( selectedDate ) {
			//$( "#sday" ).datepicker( "option", "maxDate", selectedDate );
		}
	});
	$( "#ew_vedate" ).datepicker({
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
<body>
<div align="center">
<h3>스타일 월드컵 ${kind == 'in' ? '등록':'수정'}</h3>
<form method="post">
<input type="hidden" name="kind" value="${kind}"/>
<input type="hidden" name="ewidx" value="${wl.getEw_idx()}"/>

<table>
<tr><th>제목</th><td><input style="width:65%" type="text" name="title"value="${wl.getEw_title()}" />

<select name="rule"  style="width:25%">
	<option value="4" <c:if test="${wl.getEw_rule() == 4 }">selected</c:if>>4강</option>
	<option value="8" <c:if test="${wl.getEw_rule() == 8 }">selected</c:if>>8강</option>
	<option value="16" <c:if test="${wl.getEw_rule() == 16 }">selected</c:if>>16강</option>
</select>
</td></tr>
<tr><th>모집기간</th><td>
<input type="text" style="width:40%" name="ew_csdate"  id="ew_csdate" value="${fn:substring(wl.getEw_csdate(),0,10) }" size="10" class="ipt" /> ~
<input type="text" style="width:40%" name="ew_cedate"  id="ew_cedate" value="${fn:substring(wl.getEw_cedate(),0,10) }" size="10" class="ipt" />
</td></tr>
<tr><th>투표기간</th><td>
<input type="text" style="width:40%" name="ew_vsdate" id="ew_vsdate" value="${fn:substring(wl.getEw_vsdate(),0,10) }" size="10" class="ipt" /> ~
<input type="text" style="width:40%" name="ew_vedate" id="ew_vedate" value="${fn:substring(wl.getEw_vedate(),0,10) }" size="10" class="ipt" />
</td></tr>

<c:if test="${kind == 'up' }">
<tr><th>상태설정</th><td>
<select name=ewstatus style="width:40%">
	<option value="a" <c:if test="${wl.getEw_status() == 'a' }">selected</c:if>>모집</option>
	<option value="b" <c:if test="${wl.getEw_status() == 'b' }">selected</c:if>>투표진행</option><!-- 투표 -->
</select>
</td></tr>
</c:if>
</table><br>
<input type="submit" class="btn btn-dark" style="width:20%" value="${kind == 'in' ? '등록':'수정'}" />&nbsp;&nbsp;&nbsp;
<input type="button" class="btn btn-outline-dark" onclick="window.close();" style="width:20%" value="취소" />
</form>
</div>


</body>
</html>