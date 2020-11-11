<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String newbasePath = "//"+request.getServerName()+path+"/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=basePath%>">
<title>ModelAttribute</title>
</head>
<body>
<%=newbasePath%>
	<form action="<%=newbasePath%>view/helloshow" method="post">
		<input name="name" type="text" /> 
		<input name="password" type="text" /> 
		<input type="submit" value="submit">
	</form>

	<br>
	<br> users: ${users.name } ${users.password }
	<br>
	<br> string: ${string}
	<br>
	<br> cat: ${cat.speed }
	<br>
	<br> username: ${username }
	<br>
	<br> password: ${password }
</body>
<script type="text/javascript">
</script>
</html>