<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head_fr.jsp" %>
<%@ page import="java.util.*" %>
<%
List<StoreInfo> si = (List<StoreInfo>)request.getAttribute("si");
%>
<style>
#map {width:70%; height:80%; padding-left:20%; float: right; flex-grow: 1;}
span{cursor:pointer}
</style>
<!-- <script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=a8932fd37db41a867de928eea7507b60&libraries=services"></script> -->
<!-- 사이트 도메인 http://localhost:8087 appkey: 00cbe202e843f23e025cb4b005a51223 -->
<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=00cbe202e843f23e025cb4b005a51223&libraries=services"></script>
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script src="resources/js/kakaoMap.js"></script>
<br />
<div id="app" align="center" style="width:70%; height:800px; position:absolute; left:300px; ">
<h2 align="center">매장 찾기</h2>
	<div width="200px" align="left" style="margin-left:60px;">
		<span v-on:click="brandStore('NN')" style="font-size:20px;">나이키</span>&nbsp;&nbsp;
		<span v-on:click="brandStore('DD')" style="font-size:20px;">닥터마틴</span>&nbsp;&nbsp;
		<span v-on:click="brandStore('CC')" style="font-size:20px;">크록스</span><br />
	</div>
	<br />
	<div style="float:left;">
		<h4>검색가능 매장 : {{arrStore.length}}</h4>
		<div v-for="item in arrStore" v-bind:key="item.bsiidx" >
			<store-list v-bind:object="item" v-on:addr-search="addrSearch"></store-list>
		</div>
	</div>
	<div id="btn" style="float:right;">
		<input type="button" v-if="isview" value="지도 축소" class="btn btn-outline-secondary" onclick="zoom('+1')" />
		<input type="button" v-if="isview" value="지도 확대" class="btn btn-outline-secondary" onclick="zoom('-1');" />
		<br />
	</div>
	<div id="map"></div>
	<br/>
		<input type="hidden" v-model="bsiname" id="bsiname" />
</div>
<script>
var StoreList = {
		props : ["object"],
		template : `
		<div style="float:left;">
		<table cellpadding="4" >
		<tr>
		<td width="350px">{{ object.bsiname }}</td>
		</td>
		<tr>
		<td colspan="2" style="border-bottom:1px dotted black;" ><span style="cursor:pointer;" v-html="object.bsiaddr" v-on:click="addrSearch(object.bsiaddr,object.bsiname)"></span></td>
		</tr>
		</table>
		</div>
		`,
		methods : {
			addrSearch(addr,bsiname){
				this.$emit("addr-search", addr,bsiname);
			}
		}
	}
new Vue({
	el : "#app",
	data : {
		arrStore : [
<%
String obj = "";
for (int i = 0; i < si.size(); i++){
	StoreInfo s = si.get(i);
	obj = (i == 0 ? "" : ",") + 
			"{ bsiidx:\""+ s.getBsi_idx() +"\", bsiname:\""+ s.getBsi_name() +"\", bsiaddr:\""+ s.getBsi_addr() +"\", pbid:\""+ s.getPb_id() +
		"\", bsiname:\""+ s.getBsi_name() +"\"}";
	out.println(obj);
}
%>
		],
		addr : "",
		bsiname : "",
		isview : false
	},
	methods : {
		brandStore(brand){
			location.href="storeInfo?pbid="+ brand;
		},
		addrSearch(addr,bsiname){
			this.isview = true;
			showMap();
			addr2Geo(addr,bsiname);
		}
	},
	components : {
		"store-list" : StoreList
	}
});
</script>
</body>
</html>
