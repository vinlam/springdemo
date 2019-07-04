<%@ page language="java"  contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.min.js"></script>
<title>生产者</title>
</head>
<body>
    <h1>生产者${name}</h1>

    <h2>去队列发消息<%=basePath%></h2>
    <form action="${pageContext.request.contextPath}/views/onsend" method="post">
        发送的内容:<textarea name="message" ></textarea>
        <input type="text" style="display: none" name="sendflag" value="2" />
        <input type="submit" value="提交" />
    </form>

    <h2>去主题发消息</h2>
    <form action="${pageContext.request.contextPath}/views/onsend" method="post">
        发送的内容:<textarea name="message" ></textarea>
        <input type="text" style="display: none" name="sendflag" value="1" />
        <input type="submit" value="提交" />
    </form>

    <h2><a href="${pageContext.request.contextPath}/">返回主页</a></h2>
</body>
<script>
	$(document).ready(function(){
		//alert("ha ha");
		var p = "${pageContext.request.contextPath}";
		var p2 = '<%=basePath%>';
		console.log(p);
	})
</script>
</html>