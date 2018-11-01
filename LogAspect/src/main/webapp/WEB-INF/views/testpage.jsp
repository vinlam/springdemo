<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>接口测试</title>

</head>
<body>
	<div>
		<input type="button" value="testget" id="sendGet">
		<input type="button" value="testgetids" id="sendGetIds">
		<input type="button" value="testpost" id="sendPost">
	</div>
</body>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/jquery.min.js"></script>
<script type="text/javascript">
       function testget(){
    	   var users = [];
    	   var user1={
    		   Id:11111,
    		   name:"jack",
    		   password:"123456"
    	   }
    	   var user2={
    		   Id:22222,
    		   name:"tom",
    		   password:"654321"
    	   }
    	   users.push(user1);
    	   users.push(user2);
           var param = {
        		   ids:['11111111','22222222'],
                   users:users
           }
           $.ajax({
<%--                    	url : "<%=request.getContextPath()%>/t/getUser", --%>
                   	url : "<%=request.getContextPath()%>/userController/getUser",
					type : 'get',
					//async:true, 
					dataType : "json",
					contentType : "application/x-www-form-urlencoded; charset=UTF-8",
					//traditional: true, 
					data : param,
					success : function(result) {
						alert(JSON.stringify(result));
					},error : function(XMLHttpRequest, textStatus, errorThrown) {
						//异常处理；
						alert("ajaxerror: " + JSON.stringify(XMLHttpRequest) + " textStatus: " + XMLHttpRequest.statusText);
					}
		});
	}
       
       function testgetIds(){
    	   var param = {
    			   ids:[11111111,22222222]
    	   }
                   
           $.ajax({
                   	url : "<%=request.getContextPath()%>/userController/getIds",
					type : 'get',
					//async:true, 
					dataType : "json",
					//contentType : "application/json",
					//traditional: true, 
					data : param,
					success : function(result) {
						alert(JSON.stringify(result));
					},error : function(XMLHttpRequest, textStatus, errorThrown) {
						//异常处理；
						alert("ajaxerror: " + JSON.stringify(XMLHttpRequest) + " textStatus: " + XMLHttpRequest.statusText);
					}
		});
	}
       
       function testpost(){
    	   var users = [];
    	   var user1={
    		   Id:11111,
    		   name:"jack",
    		   password:"123456"
    	   }
    	   var user2={
    		   Id:22222,
    		   name:"tom",
    		   password:"654321"
    	   }
    	   users.push(user1);
    	   users.push(user2);
           var param = {
        		   ids:[11111111,22222222],
                   users:users
           }
           $.ajax({
                   	url : "<%=request.getContextPath()%>/userController/getPostUser",
					type : 'post',
					//async:true, 
					dataType : "json",
					contentType : "application/json",
					//traditional: true, 
					data : JSON.stringify(param),
					success : function(result) {
						alert(JSON.stringify(result));
					},error : function(XMLHttpRequest, textStatus, errorThrown) {
						//异常处理；
						alert("ajaxerror: " + JSON.stringify(XMLHttpRequest) + " textStatus: " + XMLHttpRequest.statusText);
					}
		});
	}

	$("#sendGet").off("click").on("click", testget);
	$("#sendGetIds").off("click").on("click", testgetIds);
	$("#sendPost").off("click").on("click", testpost);
</script>
</html>