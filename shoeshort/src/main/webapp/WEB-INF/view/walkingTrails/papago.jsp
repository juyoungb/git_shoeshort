<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head_fr.jsp" %>
<%@ page import="org.json.simple.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
</head>
<body>
<div id="app">
    <form action="trans">
        내용
        <input type="text" v-model="text" /> 
        결과 <p>{{result}}</p>
        <input type="button" value="번역하기" v-on:click="translate()"/>
    </form>
</div>
<script>
new Vue({
    el: "#app", 
    data: {
        text: "",
        result: ""
    }, 
    methods: {
        translate: function() {
            axios.get(
                "./trans?text=" + this.text, {
                    headers: { "Content-Type": "application/json; charset=UTF-8" }
                }
            )
            .then(response => {
                alert(JSON.stringify(response.data));

                this.result = response.data;
            })
            .catch(e => {    // GET 요청 실패시 실행할 코드
                console.log(e);
            })
        }
    }
});
</script>
</body>
</html>
