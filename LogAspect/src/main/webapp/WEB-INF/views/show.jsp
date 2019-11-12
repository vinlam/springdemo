<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>    
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=basePath%>">
<title>FilterDemo</title>
</head>
<body>
	<div align="center">
		<table>
			<tr>
				<td>昵称：</td><td>${author}</td>
			</tr>
			<tr>
				<td>邮箱：</td><td>${email}</td>
			</tr>
			<tr>
				<td>网址：</td><td>${url}</td>
			</tr>
			<tr>
				<td>留言：</td><td>${comment}</td>
			</tr>
			<!-- <tr>
				<td><img alt="x" src=${comment}></td>
			</tr> -->
		</table>
	</div>
</body>
<script type="text/javascript">
	console.log("${comment}");
</script>
</html>