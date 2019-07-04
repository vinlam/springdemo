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
		Please input you want to say:
		<form action="show.html" method="post">
			<table>
				<tr>
					<td><input type="text" id="author" name="author" placeholder="昵称" /></td>
				</tr>
				<tr>
					<td><input type="text" id="email" name="email" placeholder="邮箱" /></td>
				</tr>
				<tr>
					<td><input type="text" id="url" name="url"placeholder="网址"></td>
				</tr>
				<tr>
					<td><textarea name="comment" rows="5" placeholder="来都来了，何不XSS一下"></textarea></td>
				</tr>
				<tr>
					<td align="center"><input type="submit" value="Go" />
				</tr>	
			</table>
		</form>
	</div>
</body>
</html>