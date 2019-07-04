<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	//response.setStatus(200);//避免拦截
	
	String errCode = "";
	String errMsg = "";
	Object traceId = request.getAttribute("traceId");
	
	if(null == traceId){
		traceId = "404";
	}
	if(exception != null){
		errCode = "";
		errMsg = exception.getMessage();
	}else{
		errCode = "404";
		errMsg = "不好意思，你请求的页面不存在。";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div>
		<p>交易流水号：<%=traceId%></p>
		<p>交易错误码：<%=errCode%></p>
		<p>交易错误信息：<%=errMsg%></p>
	</div>
</body>
</html>