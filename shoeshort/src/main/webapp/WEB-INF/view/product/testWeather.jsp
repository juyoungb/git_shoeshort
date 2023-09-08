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
<%
if(itemList.size() > 0){
	for(int i = 0; i<itemList.size(); i++){
		JSONObject jo = (JSONObject)itemList.get(i);
		out.println(jo.get("wf3Am"));// 
		out.println("<hr>");
		out.println(jo.get("wf4Am"));
		out.println("<hr>");
		out.println(jo.get("wf5Am"));
		out.println("<hr>");
		out.println(jo.get("wf6Am"));
		out.println("<hr>");
		out.println(jo.get("wf7Am"));
		out.println("<hr>");
		out.println(jo.get("wf8"));
		out.println("<hr>");
		out.println(jo.get("wf9"));
		out.println("<hr>");
		out.println(jo.get("wf10"));
		out.println("<hr>");
		out.println(jo.get("wf7Pm"));
		out.println("<hr>");
		out.println(jo.get("wf8"));
		out.println("<hr>");
		out.println(jo.get("wf9"));
		out.println("<hr>");
		out.println(jo.get("wf10"));
		out.println("<hr>");
	}
} else{
	out.println("데이터가 없습니다.");
}
%>
<div>
	
</div>
</body>
</html>