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
				<td>昵称：</td><td>${data}</td>
			</tr>
		</table>
	</div>
</body>
<script type="text/javascript">
	console.log("${data}");
</script>
</html>