<%@page import="java.util.Comparator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.TreeMap"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>VCS 接口</title>
<%
String path = request.getContextPath();
%>
<script type="text/javascript" src="<%= path %>/js/jquery-1.6.min.js"></script>
<style type="text/css">
html {
    width:99.9%;
    height:99.8%;
}

</style>

<%
	Map<String,String> map = new TreeMap<String,String>(new Comparator(){
		public int compare(Object o1, Object o2){
			int i1 = Integer.valueOf(o1.toString().split(",")[0]);
			int i2 = Integer.valueOf(o2.toString().split(",")[0]);
			return i1-i2;
		}
	});
	int index = 1;
	
	
	map.put(index++ +",查找 测试",path +  "/controller/hello/find?id=1");
	map.put(index++ +",删除缓存 测试",path +  "/controller/hello/save?id=1&name=11111");
	map.put(index++ +",更新缓存 测试",path +  "/controller/hello/update?id=1&name=22222");
%>
</head>
<script type="text/javascript">
	$(document).ready(function(){
		$("#content").load(function(){
			var url = document.getElementById("content").contentWindow.location.href;
			$("#text_url").val(url);
		});


		$("#content").contents().find("body").css("word-wrap:","break-word").css("word-break:","normal");

		
		$("#button_go").click(function(){
			var url = $("#text_url").val();
			if($.trim(url)!=''){
				$("#content").attr("src",url);
			}
		});
	});
	
</script>
<body style="width: 98%;height: 98%">
	
<%-- 	<form action="${ctx}/restful/tzxProxyService/product/dishes_list?storeid=1" method="post"> --%>
<!-- 		<input type="hidden" name="ccc" value="123"> -->
<!-- 		<input type="submit"> -->
<!-- 	</form> -->


	<table cellpadding="0" cellspacing="0"  height="98%" width="98%" >
		
		<tr height="10%">
			<td colspan="2">
				url:<input type="text" style="width: 900px" id="text_url">
				
				<input type="button" value="go" id="button_go">
			</td>
		</tr>
		
		<tr>
			<td valign="top" width="30%" style="border:solid 1px red;">
				<div>
					<ul>
						<%
							for(Entry<String,String> entry:map.entrySet()){
								out.print("<li><a href='"+entry.getValue()+"' target='content'>"+entry.getKey()+"</a></li>");								
							}
						%>
					</ul>
				</div>
			</td>
			<td valign="top" height="100%" style="word-wrap: break-word; word-break: normal;">
				<iframe id="content" name="content" width="96%" style="height: 96%; word-wrap: break-word; word-break: normal; " height="100%">
				</iframe>				
			</td>
		</tr>
	</table>
</body>
</html>