<%@page import="java.io.Console"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.json.simple.*"%>
<%@ page import="org.json.simple.parser.*"%>
<%@ include file="../_inc/inc_head_fr.jsp" %>
<%
request.setCharacterEncoding("utf-8");
JSONArray itemList = (JSONArray)request.getAttribute("itemList");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div id="app"></div>
<table width="800" align="center" cellpadding="5" border="1">
<th align="center">날씨</th>
<th align="center">강수량</th>

<%
if(itemList.size() > 0){
	for(int i = 0; i<itemList.size(); i++){
		JSONObject jo = (JSONObject)itemList.get(i);
%>
<tr align="center"><td><%=jo.get("wf3Pm") %><img v-bind:src="ss"></td><td><%=jo.get("rnSt3Pm") %></td></tr>
<tr align="center"><td><%=jo.get("wf4Pm") %></td><td><%=jo.get("rnSt4Pm") %></td></tr>
<tr align="center"><td><%=jo.get("wf5Pm") %></td><td><%=jo.get("rnSt5Pm") %></td></tr>
<tr align="center"><td><%=jo.get("wf6Pm") %></td><td><%=jo.get("rnSt6Pm") %></td></tr>
<tr align="center"><td><%=jo.get("wf7Pm") %></td><td><%=jo.get("rnSt7Pm") %></td></tr>
<tr align="center"><td><%=jo.get("wf8") %></td><td><%=jo.get("rnSt8") %></td></tr>
<tr align="center"><td><%=jo.get("wf9") %></td><td><%=jo.get("rnSt9") %></td></tr>
<tr align="center"><td><%=jo.get("wf10") %></td><td><%=jo.get("rnSt10") %></td></tr>
<%
	}
}
%>
</table>
</div>
<script>
new Vue({
	el: "#app",
	data : {
		
	},
	computed :
});
</script>

</body>
</html>