<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE>
<html>
<head>
<title>iframe redirect</title>

</head>
<body>
	<iframe id="myframe" name="myframe" src="${vc}" onload="frameonload(this)" frameborder="3" style="width:300;height:200;border-width:1;border-color:red;border-style:solid"></iframe>
</body>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.min.js"></script>
<script type="text/javascript">
function frameonload(obj){
}
</script>
</html>