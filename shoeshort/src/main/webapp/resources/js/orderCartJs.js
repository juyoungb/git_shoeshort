function chkAll(all){
//전체 선택 체크박스 클릭시 모든 체크박스에 대한 체크 여부를 처리하는 함수
	var frmCart = document.frmCart.value;
	var arr = document.frmCart.chk;	
	for(var i =1; i<arr.length; i++)	arr[i].checked = all.checked;

}
function chkOne(one){
// 특정 체크박스 클릭시 체크 여부에 따른 '전체 선택' 체크박스의 체크 여부를 처리하는 함수
	var frm = document.frmCart;
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
function cartUp(ocidx, opt, cnt){
// 장바구니내 특정 상품의 옵션 및 수량을 변경하는 함수
// cnt가 0이면 옵션 변경이고, opt가 0이면 수량 변경을 의미
	$.ajax({
		type: "POST", url: "cartUpCnt",
		data: {"ocidx":ocidx, "opt":opt, "cnt":cnt},
		success: function(chkRs){
			if(chkRs ==0){
				alert("상품 변경에 실패했습니다.\n다시 시도하세요.");
			}
			location.reload();
		}
	});		
}

function cartDel(ocidx){	
//장바구니내 특정 상품을 삭제하는 함수
	alert(ocidx);
	if(confirm("정말 삭제하시겠습니까?")){
		$.ajax({
			type: "POST", url: "cartDel",
			data:{"ocidx": ocidx},
			success : function(chkRs){
				if(chkRs == 0){					
					alert("상품 삭제에 실패했습니다.\n다시 시도하세요.");
				}
				location.reload();
			}		
		});
	}	
}
function getSelectedChk(){
// 체크박스들 중 선택된 체크박스들의 값(value)들을 쉼표로 구분하여 문자열로 리턴하는 함수
	var chk =document.frmCart.chk;
	var idxs ="";	// chk컨트롤 배열에서 선택된 체크박스의 값들을 누적 저장할 변수
	for(var i= 1; i<chk.length ; i++){
		if(chk[i].checked)	idxs +=","+chk[i].value;
	}
	return idxs.substring(1);
}
function chkDel(){
//사용자가 선택한 상품(들)을 삭제하는 함수
	var ocidx =getSelectedChk();
	//선택한 체크박스의  oc_idx 값들이 쉼표를 기준으로 '1,2,3,4' 형태의 문자열로 저장됨
	if(ocidx == "")	alert("삭제할 상품을 선택하세요.");
	else			cartDel(ocidx);
}
function chkBuy(){
//사용자가 선택한 상품(들)을 구매하는 함수
	var ocidx =getSelectedChk();
	if(ocidx == "")	alert("구해할 상품을 선택하세요.");
	else			document.frmCart.submit();
}
function allBuy(){
// 전체 상품 구매를 처리하는 함수
	var arr = document.frmCart.chk;	
	for(var i =1; i<arr.length; i++)	arr[i].checked = true;
	document.frmCart.submit();
}