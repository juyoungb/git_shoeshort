<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head_fr.jsp"%>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f9f9f9;
}

form {
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
	padding: 20px;
	border-bottom: 1px solid #ddd;
}

th {
	text-align: left;
	font-weight: bold;
	white-space: nowrap; /* Prevent th from wrapping */
	width:10%;
}

td {
	vertical-align: top; /* Align td content to the top */
}

input[type="text"] {
	height: 30px;
	padding: 8px;
	border: 1px solid #ccc;
	border-radius: 3px;
	box-sizing: border-box;
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

</head>
<body>
	<br>
	<div align="center">
	<div style=" width:1300px;display:flex; margin:5px;">
	<form id="frm" action=memberSch method="POST" style="width: 625px;">
	<input type="hidden" name="kind" value="id" />
		<br>
		<h2 align="center">아이디 찾기</h2>
		<br>
		<div align="center">
			<span>아이디는 가입시 입력하신 이메일을 통해 찾을 수 있습니다.</span>
		</div>
		<br>
		<div align="center">
			<table>
				<tr>
				<th>
				<label for="name" class="form-label">이름</label>
				</th>
					<td> <input class="form-control" id="name" type="text" name="uname" style="height:30%"  placeholder="user_name"  ></td>
				</tr>
				<tr>
				<th><label for="exampleFormControlInput1" class="form-label">이메일</label> </th>
				<td>
					<input type="email"	class="form-control" id="exampleFormControlInput1" name="email" placeholder="name@example.com">
				</td>
				</tr>
				<tr>
				<td colspan="2" align="center" style="border: none"><br><input
					type="submit" class="btn btn-dark" style="width: 95%;"
					value="아이디 찾기"></td>
				</tr>
			</table>
		</div>
	</form>
	<form id="frm" action="memberSch" method="POST" style="width: 625px;">
		<input type="hidden" name="kind" value="pw" />
		<h2 align="center">비밀번호 찾기</h2>
		<br>
		<div align="center">
			<span>비밀번호는 이름, 가입한 아아디, 이메일을 통해 찾을 수 있습니다.</span>
		</div>
		<div align="center">
			<table>
				<tr>
				<th>
				<label for="uid" class="form-label">아이디</label>
				</th>
					<td><input class="form-control" id="uid" name="uid" type="text" style="height:30%" placeholder=" user_id" ></td>
				</tr>
				<tr>
				<tr>
				<th>
				<label for="uname" class="form-label">이름</label>
				</th>
					<td><input class="form-control" id="uname" type="text" name="uname" style="height:30%" placeholder="user_name" ></td>
				</tr>
				<tr>
				<tr>
				<th><label for="exampleFormControlInput1" class="form-label">이메일</label> </th>
				<td>
					<input type="email"	class="form-control" id="exampleFormControlInput1" name="email" placeholder="name@example.com">
				</td>
				</tr>
				<tr>
				<td colspan="2" align="center" style="border: none">
					<input type="submit" class="btn btn-dark" style="width: 95%;" value="비밀번호 찾기">
				</td>
				</tr>
			</table>
		</div>
	</form>
</div>
</div>
	<%@ include file="../_inc/inc_foot_fr.jsp"%>