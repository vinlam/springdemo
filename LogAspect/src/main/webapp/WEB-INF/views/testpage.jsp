<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
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
		<input type="button" value="testgetids List&lt;Long&gt;" id="sendGetIds">
		<input type="button" value="testgids String[]" id="sendGids">
		<input type="button" value="getlistgids" id="sendGds"> 
		<input type="button" value="delids" id="delids"> 
	    <input type="button" value="testpost" id="sendPost">
	    <input type="button" value="mvcpost" id="mvcPost">
	    <input type="button" value="tmvpost" id="tmvPost">
		<img src="http://localhost:8080/LogAspect/api/getCodeByte" />
		<form action="<%=request.getContextPath()%>/t/rd1" method="post">
			<input type="submit" value="formsub" id="subbtn">
		</form>
	</div>
</body>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.min.js"></script>
<script type="text/javascript">
       function testget(){
    	   var arr =['11111111','222222222'];
           var param = {
        		   ids:arr.toString(),
        		   age:"26",
        		   name:"jack",
        		   sex:"F"
           }
           $.ajax({
                   	url : "<%=request.getContextPath()%>/api/userController/getUser",
					type : 'get',
					//async:true, 
					dataType : "json",
					//contentType : "application/x-www-form-urlencoded; charset=UTF-8",
					//traditional: true, 
					data : param,
					//data : JSON.stringify(param),
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
                   	url : "<%=request.getContextPath()%>/api/userController/getIds",
					type : 'get',
					//async:true, 
					dataType : "json",contentType : "application/x-www-form-urlencoded; charset=UTF-8",
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
		
       	function testgids(){
    	   var param = {
    			   ids:[11111111,22222222]
    	   }
           $.ajax({
                   	url : "<%=request.getContextPath()%>/api/userController/gids",
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
       
       	function testgds(){
    	   var arr = [11111111,22222222];
    	   var param = {
    			   ids:arr.toString()
    	   }
           $.ajax({
                   	url : "<%=request.getContextPath()%>/api/userController/gds",
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
       	
       	function testdelids(){
    	   var arr = [11111111,22222222];
    	  
           $.ajax({
                   	url : "<%=request.getContextPath()%>/api/userController/delids",
					type : 'delete',
					//async:true, 
					dataType : "json",
					contentType : "application/json",
					//traditional: true, 
					data : JSON.stringify(arr),
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
                   	url : "<%=request.getContextPath()%>/api/userController/getPostUser",
				type : 'post',
				//async:true, 
				dataType : "json",
				contentType : "application/json",
				//traditional: true, 
				data : JSON.stringify(param),
				success : function(result) {
					alert(JSON.stringify(result));
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					//异常处理；
					alert("ajaxerror: " + JSON.stringify(XMLHttpRequest)
							+ " textStatus: " + XMLHttpRequest.statusText);
				}
			});
		}
       
       	function mvcpost(){
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
                //url : "<%=request.getContextPath()%>/t/getPostUser",
                url : "<%=request.getContextPath()%>/t/postUser",
				type : 'post',
				//async:true, 
				dataType : "json",
				//contentType : "application/json",
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",
				//traditional: true, 
				data : user2,
				success : function(result) {
					alert(JSON.stringify(result));
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					//异常处理；
					alert("ajaxerror: " + JSON.stringify(XMLHttpRequest)
							+ " textStatus: " + XMLHttpRequest.statusText);
				}
			});
		}
       	
       	function tmvpost(){
    	   
           $.ajax({
                   	url : "<%=request.getContextPath()%>/t/rd",
				type : 'post',
				//async:true, 
				dataType : "html",
				//contentType : "application/json",
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",
				//traditional: true, 
				data : "testPost",
				success : function(result) {
					alert(JSON.stringify(result));
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					//异常处理；
					alert("ajaxerror: " + JSON.stringify(XMLHttpRequest)
							+ " textStatus: " + XMLHttpRequest.statusText);
				}
			});
		}

		$("#sendGet").off("click").on("click", testget);
		$("#sendGetIds").off("click").on("click", testgetIds);
		$("#sendGids").off("click").on("click", testgids);
		$("#sendGds").off("click").on("click", testgds);
		$("#delids").off("click").on("click", testdelids);
		$("#sendPost").off("click").on("click", testpost);
		$("#mvcPost").off("click").on("click", mvcpost);
		$("#tmvPost").off("click").on("click", tmvpost);
</script>
</html>