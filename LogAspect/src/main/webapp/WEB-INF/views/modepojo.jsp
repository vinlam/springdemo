<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">

    <title>My JSP 'index.jsp' starting page</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.min.js"></script>
  </head>
  <script type="text/javascript">
  $(function(){
      $("#modelTest").on("click",function(){

          window.location.href="<%=basePath%>model/pojo?userName="+encodeURI('小明')+"&sex="+encodeURI('男');
      })
  });
</script>
<body>

<input type="button" id="modelTest" value="测试">
<input type="text" value="${pojo.userName }">
<input type="text" value="${pojo.sex }">

  </body>
</html>