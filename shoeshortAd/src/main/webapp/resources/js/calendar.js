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

	$( "#sdate" ).datepicker({
		//defaultDate: "+1w",
		changeMonth: true,
		//numberOfMonths: 3,
		dateFormat:"yy-mm-dd",
		onClose: function( selectedDate ) {
			//$( "#eday" ).datepicker( "option", "minDate", selectedDate );
		}
	});
	$( "#edate" ).datepicker({
		//defaultDate: "+1w",
		changeMonth: true,
		//numberOfMonths: 3,
		dateFormat:"yy-mm-dd",
		onClose: function( selectedDate ) {
			//$( "#sday" ).datepicker( "option", "maxDate", selectedDate );
		}
	});
});

function chkAll(all){
	//전체 선택 체크박스 클릭시 모든 체크박스에 대한 체크 여부를 처리하는 함수
		var frm = document.frm.value;
		var arr = document.frm.chk;	
		for(var i =1; i<arr.length; i++)	arr[i].checked = all.checked;

	}
function chkOne(one){
	// 특정 체크박스 클릭시 체크 여부에 따른 '전체 선택' 체크박스의 체크 여부를 처리하는 함수
		var frm = document.frm;
		var all =frm.all;
		if(one.checked){		//특정 체크박스를 체크했을 경우
			var arr = frm.chk;
			var isChk = true;
			for(var i =1; i<arr.length; i++){
				if(arr[i].checked == false){
					isChk = false;		break;
				}
			}  
			all.checked =isChk;
		} else {				//특정 체크박스를 해제했을 경우
			all.checked =false;
		}
	}
function getSelectedChk(){
	// 체크박스들 중 선택된 체크박스들의 값(value)들을 쉼표로 구분하여 문자열로 리턴하는 함수
		var chk =document.frm.chk;
		var idxs ="";	// chk컨트롤 배열에서 선택된 체크박스의 값들을 누적 저장할 변수
		for(var i= 1; i<chk.length ; i++){
			if(chk[i].checked)	idxs +=","+chk[i].value;
		}
		return idxs.substring(1);
	}

function chkChange(){	
		var ocidx =getSelectedChk();
		if(ocidx == "")	alert("변경할 목록을 선택하세요.");
		else			document.frm.submit();
	}
function allChk(val){
	if(val.name =="period"){ 
	var chks =document.frm1.period;
		for(var i=0; i<chks.length-1; i++){
			chks[i].checked=false;
		}
	}else{
		var chks =document.frm1.status;
		for(var i=1; i<chks.length; i++){
			chks[i].checked=false;
		}
	}
}
function selChk(val){	
	if(val.name =="period"){
		var chks =document.frm1.period;
		chks[3].checked=false;
	}
	else{
		var chks =document.frm1.status;
		chks[0].checked=false;	
	}
}
$("#reset").click(function(){
	  $("#frm1")[0].reset();
	});
