<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head_fr.jsp" %>
<%@ page import="java.util.*" %>
<%
request.setCharacterEncoding("utf-8");
StyleInfo styleView = (StyleInfo)request.getAttribute("styleView");
ProductInfo styleProduct =(ProductInfo)request.getAttribute("styleProduct");
String date = styleView.getSi_date().substring(0,10);
String lnkG = "";
boolean isPms = false; // 수정, 삭제 버튼을 보여줄지 여부를  저장할 변수
String upLink = "", delLink = "";   // 수정, 삭제용 링크를 저장할 변수

if (isLogin && loginInfo.getMi_id().equals(styleView.getMi_id())) {
   // 현재 사용자가 로그인이 되어있고, 현 게시글의 작성자일 경우
	isPms = true;
	upLink = "location.href='styleProcUp?siidx=" + styleView.getSi_idx() +"&piid="+ styleView.getPi_id() +"'";
	delLink = "styleDel();";
} else {
	upLink = "alert('해당게시글에 대한 권한이 없습니다.')";
	delLink = "alert('해당게시글에 대한 권한이 없습니다.')";
}

if (isLogin){	// 현재 로그인한 상태
	lnkG = "styleGood("+ styleView.getSi_idx() +", '" + styleView.getPi_id() + "');";
} else {	// 로그인하지 않은 상태
	lnkG = "goLogin('좋아요는');";
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script>
function goLogin(msg) {
	if (confirm(msg + " 로그인이 필요합니다.\n로그인 화면으로 이동하시겠습니까?")){
		location.href="login?siidx=<%=styleView.getSi_idx() %>";
	}
}

function styleDel() {
	if (confirm("정말 삭제하시겠습니까?\n삭제된 글은 다시 복구할 수 없습니다.")) {
		location.href="styleProcDel?siidx=<%=styleView.getSi_idx()%>&piid=<%=styleView.getPi_id() %>";
	}
}
function styleGood(siidx, piid){ // ajax를 이용한 스타일 좋아요 함수
	$.ajax({
		type : "POST", url : "styleGood", data : {"siidx" : siidx , "piid" : piid},
		success : function(chkRs) {			
			if (chkRs > 1) {
				location.reload();
			} else {
				alert("이미 참여했습니다.");
			}
		}
	});
}
// 카카오 메시지 보내기 메소드
try {
	function sendLinkDefault() {
	Kakao.init('ce64818598c9b1b5266e9c0368dcd930')
	Kakao.Link.sendDefault({
	  objectType: 'feed',
	  content: {
	    title: '좋아요 요청이 도착했습니다.',
	    description: '클릭 후 친구에게 좋아요를 눌러보세요',
	    imageUrl:'http://localhost:8085/shoeshort/resources/img/style_img/style_img_CC01.png',
	    link: {
	      mobileWebUrl: 'https://developers.kakao.com',
	      webUrl: 'http://localhost:8085/shoeshort/styleView?siidx=<%=styleView.getSi_idx() %>&piid=<%=styleView.getPi_id()%>',
	    },
	  },
	  social: {
	    likeCount: <%=styleView.getSi_good() %>,
	  },
	  buttons: [
	    {
	      title: '웹으로 보기',
	      link: {
	        mobileWebUrl: 'https://developers.kakao.com',
	        webUrl: 'http://localhost:8085/shoeshort/styleView?siidx=<%=styleView.getSi_idx() %>&piid=<%=styleView.getPi_id()%>',
	      },
	    },
	  ],
	})
}
; window.kakaoDemoCallback && window.kakaoDemoCallback() }
catch(e) { window.kakaoDemoException && window.kakaoDemoException(e) }
</script>
<style>
h4 { margin-left:400px;}
</style><head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="${pageContext.request.contextPath}/resources/js/date_change.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.4.js"></script>
<script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
</head>
<body>
<br />
<div width="">
<table style="width:20%;" cellpadding="5" align="center" >
<tr><th align="center"><%=styleView.getMi_id() %></th></tr>
<tr><th align="center"><%=date %></th></tr>
<tr>
<td colspan="3" align="center">
			<img src="resources/img/style_img/<%=styleView.getSi_img() %>" width="600" height="600" border="0" /><br />
	</td> 
</tr>
</table>
<br />
<table style="width:35%;" align="center" >
<tr>
<td align="left" rowspan="2">
	<a href="product/product_view" />
	<img src="resources/img/product_img/<%=styleProduct.getPi_img1() %>" width="100" height="100" /><br /><%=styleProduct.getPi_name() %><br /><%=styleProduct.getPi_price() %>원
</td>
<td width="60%"  rowspan="3" style="vertical-align: top;" ><%=styleView.getSi_content() %></td>
<td width="15%" align="right">
	<img src="resources/img/style_img/style_good02.png" onclick="<%=lnkG %>" style="cursor:pointer;"/>&nbsp;<%=styleView.getSi_good() %>
</td>
</tr>
<tr>
<td colspan="4" align="right" style=" vertical-align: bottom;">
    <input type="button" class="btn btn-outline-dark" onclick="sendLinkDefault();" value="좋아요 추천"/>
	<input type="button" class="btn btn-outline-dark" width="20" value="수정"  onclick="<%=upLink %>"/>
	<input type="button" class="btn btn-outline-dark" width="20" value="삭제"  onclick="<%=delLink %>" />
</td>
</tr>
</table>
</div>
</body>
</html>