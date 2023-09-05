<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head_fr.jsp" %>
<%
	LocalDate today = LocalDate.now();
	int cyear = today.getYear();
	int cmonth = today.getMonthValue();
	int cday = today.getDayOfMonth();
	int last = today.lengthOfMonth();
%>
<style>
#fontBlue {
	font-weight: bold;
	color: blue;
}

#fontRed {
	font-weight: bold;
	color: red;
}
 body {
    font-family: Arial, sans-serif;
    background-color: #f9f9f9;
  }

  form {
    width: 400px; /* Increased table width */
    margin: 0 auto;
    padding: 20px;
    background-color: #fff;
    border: 1px solid #ddd;
    border-radius: 5px;
  }

  table {
    width: 100%;
    border-collapse: collapse;
  }

  th, td {
  	
    padding: 10px;
    border-bottom: 1px solid #ddd;
  }

  th {
    text-align: left;
    font-weight: bold;
    white-space: nowrap; /* Prevent th from wrapping */
  }

  td {
    vertical-align: top; /* Align td content to the top */
  }

  input[type="text"], 
  input[type="password"] {
  	height:30px;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 3px;
    box-sizing: border-box;
  }
  input[type="button"] {
    display: inline-block; 
    margin-right: 10px; 
    padding: 8px 16px;
    border: none;
    border-radius: 3px;
    cursor: pointer;
    color: #fff;
  }
  input[type="button"] {
    background-color: #fff;
    color: #555;
    border: 1px solid yellowgreen;
  }

  input[type="button"]:hover{
    background-color: #ddd;
  }

  input[type="checkbox"] {
    margin-right: 5px;
    vertical-align: middle;
  }

  input[name="p1"],
  input[name="p2"],
  input[name="p3"] {
    width: 50px;
    font-size:16px;
    text-align:center;
  }

  input[type="checkbox"] + span {
    color: #555;
  }

  input[type="checkbox"]:checked + span {
    color: gray;
    font-weight:bold;
  }

  #select {
    background-color: #4CAF50;
    color: #fff;
  }

  #select:hover {
    background-color: #45a049;
  }

  .address-container {
    display: flex;
    align-items: center;
  }

  #zipcode {
    width: 40%;
    margin-bottom: 10px;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 3px;
    box-sizing: border-box;
  }

  #address {
    width: 100%;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 3px;
    box-sizing: border-box;
    margin-bottom: 10px;
  }

  #select-address {
    width: 20%; 
    height: 70%;
    margin-bottom: 8px;
    padding: 6px; 
    font-size: 12px; 
    background-color: #4CAF50;
    color: #fff;
  }

  #select-address:hover {
    background-color: #45a049;
  }
</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="resources/js/memInfoJs.js"></script>
<script>
	$(document).ready(function() {
		$("#e2").change(function() {
			if ($(this).val() == "") {
				$("#e3").val("");
			} else if ($(this).val() == "direct") {
				$("#e3").val("");
				$("#e3").focus();
			} else {
				$("#e3").val($(this).val());
			}
		});
	});

	function chkDupId(uid) {
		if (uid.length >= 4) {
			$.ajax({
				type : "POST",
				url : "./dupId",
				data : {
					"uid" : uid
				},
				success : function(chkRs) {
					var msg = "";
					if (chkRs == 0) {
						msg = "<span id='fontBlue'>사용하실 수 있는 ID입니다.</span>";
						$("#idChk").val("y");
					} else {
						msg = "<span id='fontRed'>이미 사용중인 ID입니다.</span>";
						$("#idChk").val("n");
					}
					$("#idMsg").html(msg);
				}
			});
		} else {
			$("#idMsg").text("아이디는 4~20자로 입력하세요.");
			$("#idChk").val("n");
		}
	}
</script>
</head>
<body>
<br>
<form id="frm" action="memberJoin" method="POST"  style="width:1000px;">
<input type="hidden" name="idChk" id="idChk" value="n" />
<h2 align="center">회원 가입</h2><br>
<div align="center">
    <table>
      <tr>
		<th>아이디</th>
		<td><input type="text" name="mi_id"
			onkeyup="chkDupId(this.value);" maxlength="20" /> <span id="idMsg"
			style="font-size: 0.8em;">아이디는 4~20자로 입력하세요.</span></td>
		</tr>
      <tr>
        <th>이름</th>
        <td><input type="text" name="mi_name" value="" /></td>
      </tr>
      <tr>
				<th>비밀번호</th>
				<td><input type="password" name="mi_pw" /></td>
			</tr>
			<tr>
				<th>비밀번호 확인</th>
				<td><input type="password" name="mi_pw2" /></td>
			</tr>
			<tr>
				<th>성별</th>
				<td><input type="radio" name="mi_gender" value="남" id="male" <c:if test="${empty userInfo || userInfo.get('gender') == 'male'}">
					checked="checked"</c:if> /> <label for="male">남</label>&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="mi_gender" value="여" id="female" <c:if test="${ !empty userInfo && userInfo.get('gender') != 'male'}">
					checked="checked"</c:if> /> <label for="female">여</label></td>
			</tr>
			<tr>
				<th>생년월일</th>
				<c:set var="birthM" value="${fn:substring(userInfo.get('birthday'),0,2)}" />
				<c:set var="birthD" value="${fn:substring(userInfo.get('birthday'),2,4)}" />
				<td><select name="year"
					onchange="resetday(this.value, from.month.value, this.form.day);">
						<%
							for (int i = 1950; i <= cyear; i++) {
						%>
						<option <%if (i == cyear) {%> selected="selected" <%}%>><%=i%></option>
						<%
							}
						%>
				</select>년 <select name="month"
					onchange="resetday(this.form.year.value, this.value, this.form.day);">
						<% String bm  =(String)pageContext.getAttribute("birthM");
							if(!bm.equals("")) cmonth = Integer.parseInt(bm);
							for (int i = 1; i <= 12; i++) {
								String tmp = (i < 10 ? "0" + i : i + "");
								String slt = (i == cmonth ? " selected='selected'" : "");
						%>
						<option <%=slt%>><%=tmp%></option>
						<%
							}
						%>
				</select>월 <select name="day">
						<% String bd  =(String)pageContext.getAttribute("birthD");
						if(!bm.equals("")) cday = Integer.parseInt(bd);
							for (int i = 1; i <= last; i++) {
								String tmp = (i < 10 ? "0" + i : i + "");
								String slt = (i == cday ? " selected='selected'" : "");
						%>
						<option <%=slt%>><%=tmp%></option>
						<%
							}
						%>
				</select>일</td>
			</tr>
			<tr>
			<c:set var="email" value="${fn:split(userInfo.get('email'),'@')}" />
				<th>이메일</th>
				<td><input type="text" name="e1" id="e1" size="10" value="${email[0]}" /> @ <select
					name="e2" id="e2">
						<option value="">도메인 선택</option>
						<option value="naver.com">네이버</option>
						<option value="nate.com">네이트</option>
						<option value="gmail.com">지메일</option>
						<option value="direct"<c:if test="${email[1] != ''}"> selected="selected"</c:if>>직접입력</option>
				</select> <input type="text" name="e3" id="e3" size="10" value="${email[1]}" /></td>
			</tr>
        <th>전화번호</th>
        <td>
          <input type="text" name="p1" size="3" maxlength="3" value="010"  > - 
          <input type="text" name="p2" size="4" maxlength="4" value=""> - 
          <input type="text" name="p3" size="4" maxlength="4" value="">
        </td>
      </tr>
      <tr>
        <th>우편번호</th>
        <td class="address-container">
          <input type="text" id="zipcode" name="zip" value="" style="width:70px;" readonly>&nbsp;
          <input type="button" id="select-address" style="width:70px;" name="address" value="우편 검색">
        </td>
      </tr>
      <tr>
        <th>주소</th>
        <td>
          <input type="text" id="address" name="addr1" value="" readonly>
        </td>
      </tr>
      <tr>
        <th>상세 주소</th>
        <td><input type="text" name="addr2" value="" style="width:100%"></td>
      </tr>
      <tr>
        <td colspan="2" align="center" style="border: none">
          <input type="submit" class="btn btn-dark" style="width:100%;" value="가입하기" >
        </td>
      </tr>
    </table>
  </div>
</form>
</body>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
window.onload = function(){
    document.getElementById("select-address").addEventListener("click", function(){ //주소입력칸을 클릭하면
        //카카오 지도 발생
        new daum.Postcode({
            oncomplete: function(data) { //선택시 입력값 세팅
            	document.getElementById("zipcode").value = data.zonecode;
                document.getElementById("address").value = data.address; // 주소 넣기
                document.querySelector("input[name=addr2]").focus(); //상세입력 포커싱
            }
        }).open();
    });
}
</script>	
</body>
</html>
