<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		<input type="button" value="testgetview" id="sendGetview"> 
		<input type="button" value="testgetids List&lt;Long&gt;" id="sendGetIds">
		<input type="button" value="testgids String[]" id="sendGids">
		<input type="button" value="getlistgids" id="sendGds"> 
		<input type="button" value="delids" id="delids"> 
	    <input type="button" value="testpost" id="sendPost">
	    <input type="button" value="testpostData" id="sendTestPost">
	    <input type="button" value="mvcpost" id="mvcPost">
	    <input type="button" value="tmvpost" id="tmvPost">
	    <input type="button" value="del" id="deldata">
	    <input type="button" value="delnotreturn" id="delbtn">
	    <input type="button" value="testput" id="putbtn">
	    <input type="button" value="putPathVariable" id="putvbtn">
	    <input type="button" value="dellistids" id="dellistids">
	    <input type="button" value="get" id="getdata">
		<img src="http://localhost:8080/LogAspect/api/getCodeByte" />
		<form action="<%=request.getContextPath()%>/t/rd1" method="post">
			<input type="submit" value="formsub" id="subbtn">
		</form>
	</div>
	<div>
		UserName:<input type="text" id="uname">
		UserId<input type="text" id="uid">
		UserPWD<input type="password" id="upwd">
		<input type="button" value="subData" id="subdata">
		<input type="button" value="ShowData" id="showdata">
		<span>输入用户ID：</span>
		<input type="text" id="inpid">
		<span>Type：</span>
		<input type="text" id="typeid">
		<textarea id="txt"></textarea>
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
       
       function testgetview(){
           $.ajax({
                   	url : "<%=request.getContextPath()%>/t/testget?name=xt",
					type : 'get',
					//async:true, 
					dataType : "html",
					//contentType : "application/x-www-form-urlencoded; charset=UTF-8",
					//traditional: true, 
					//data : param,
					//data : JSON.stringify(param),
					success : function(result) {
						alert(result);
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
                   	url : "<%=request.getContextPath()%>/api/getIds",
<%--                    	url : "<%=request.getContextPath()%>/api/userController/getIds", --%>
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
                   	url : "<%=request.getContextPath()%>/api/gids",
<%--                    	url : "<%=request.getContextPath()%>/api/userController/gids", --%>
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
                   	url : "<%=request.getContextPath()%>/api/gds",
<%--                    	url : "<%=request.getContextPath()%>/api/userController/gds", --%>
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
    		   Id:11,
    		   name:"jack",
    		   password:"123456"
    	   }
    	   var user2={
    		   Id:"12",
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
           $.ajax({
                   	url : "<%=request.getContextPath()%>/api/postdata",
				type : 'post',
				//async:true, 
				dataType : "json",
				contentType : "application/json",
				//traditional: true, 
				data : JSON.stringify(user2),
				success : function(result) {
					alert(result);
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
       	
       	function sendData(){
       		var param = {};
       		
       		param.name = $("#uname").val();
       		param.id = $("#uid").val();
       		param.password = $("#upwd").val();
       		$.ajax({
               	url : "<%=request.getContextPath()%>/api/saveandreturnurl",
				type : 'post',
				//async:true, 
				dataType : "json",
				contentType : "application/json",
				//traditional: true, 
				data : JSON.stringify(param),
				success : function(result) {
					alert(JSON.stringify(result));
					//alert(result);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					//异常处理；
					alert("ajaxerror: " + JSON.stringify(XMLHttpRequest)
							+ " textStatus: " + XMLHttpRequest.statusText);
				}
			});
       	}
       	
       	function showData(){
       		var uid = $("#inpid").val();
       		
       		
       		$.ajax({
               	url : "<%=request.getContextPath()%>/api/getSaveData/"+uid,
				type : 'get',
				//async:true, 
				dataType : "json",
				contentType : "application/json",
				//traditional: true, 
				//data : JSON.stringify(param),
				success : function(result) {
					if(result)
						alert(JSON.stringify(result));
					else
						alert("暂无数据"+result);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					//异常处理；
					alert("ajaxerror: " + JSON.stringify(XMLHttpRequest)
							+ " textStatus: " + XMLHttpRequest.statusText);
				}
			});
       	}
       	
       	function delData(){
       		var uid = $("#inpid").val();
       		$.ajax({
               	url : "<%=request.getContextPath()%>/api/deldata/"+uid,
				type : 'delete',
				//async:true, 
				dataType : "json",
				contentType : "application/json",
				//traditional: true, 
				//data : JSON.stringify(param),
				success : function(result) {
						alert("删除数据");
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					//异常处理；
					alert("ajaxerror: " + JSON.stringify(XMLHttpRequest)
							+ " textStatus: " + XMLHttpRequest.statusText);
				}
			});
       		$.ajax({
               	url : "<%=request.getContextPath()%>/api/deldata/",
				type : 'delete',
				//async:true, 
				dataType : "json",
				contentType : "application/json",
				//traditional: true, 
				//data : JSON.stringify(param),
				success : function(result) {
						alert("删除数据"+result);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					//异常处理；
					alert("ajaxerror: " + JSON.stringify(XMLHttpRequest)
							+ " textStatus: " + XMLHttpRequest.statusText);
				}
			});
       	}
       	
       	function del(){
       		var uid = 111222;
       		$.ajax({
               	url : "<%=request.getContextPath()%>/api/del/"+uid,
				type : 'delete',
				//async:true, 
				dataType : "text",//默认不设置或者设置text--不返回设置，
				//dataType : "json",
				contentType : "application/json",
				//traditional: true, 
				//data : JSON.stringify(param),
				success : function(result) {
						alert("删除数据");
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					//异常处理；
					alert("ajaxerror: " + JSON.stringify(XMLHttpRequest)
							+ " textStatus: " + XMLHttpRequest.statusText);
				}
			});
       	}
       	
       	
       	function put(){
       		var uid = 111222;
       		var param={
       				type:"1"
       		}
       		$.ajax({
<%--                	url : "<%=request.getContextPath()%>/api/testput/"+uid+"?type=1", --%>
               	url : "<%=request.getContextPath()%>/api/testput/"+uid,
				type : 'put',
				//async:true, 
				dataType : "text",//默认不设置或者设置text--不返回设置，
				//dataType : "json",
				//contentType : "application/json",
				//traditional: true, 
				//data : JSON.stringify(param),
				success : function(result) {
						alert("数据更新了");
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					//异常处理；
					alert("ajaxerror: " + JSON.stringify(XMLHttpRequest)
							+ " textStatus: " + XMLHttpRequest.statusText);
				}
			});
       	}
       	
       	function putreq(){
       		var uid = $("#inpid").val()?$("#inpid").val():"";
       		var type = $("#typeid").val()?$("#typeid").val():"";
       		var requrl = "<%=request.getContextPath()%>/api/testputreq/";
       		if(uid != null && uid != ""){
       			requrl = requrl+uid;
       			if(type != null && type != ""){
       				requrl = requrl + "?type="+type;
       			}
       		}
       		$.ajax({
               	url : requrl,
<%--                	url : "<%=request.getContextPath()%>/api/testputreq/"+uid, --%>
				type : 'put',
				//async:true, 
				dataType : "text",//默认不设置或者设置text--不返回设置，
				//dataType : "json",
				//contentType : "application/json",
				//traditional: true, 
				//data : JSON.stringify(param),
				success : function(result) {
						alert("数据更新了");
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					//异常处理；
					alert("ajaxerror: " + JSON.stringify(XMLHttpRequest)
							+ " textStatus: " + XMLHttpRequest.statusText);
				}
			});
       	}
       	
       	function delListWithIds(){
       	 var ids=[11111111,1231234234,56456456]
       		$.ajax({
               	url : "<%=request.getContextPath()%>/api/delids",
				type : 'delete',
				//async:true, 
				dataType : "json",
				contentType : "application/json",
				//traditional: true, 
				data : JSON.stringify(ids),
				success : function(result) {
						alert("删除数据"+result);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					//异常处理；
					alert("ajaxerror: " + JSON.stringify(XMLHttpRequest)
							+ " textStatus: " + XMLHttpRequest.statusText);
				}
			});
       	}
       	
       	function getData(){
       		//var uid = $("#inpid").val();
       		$.ajax({
               	url : "<%=request.getContextPath()%>/api/getdata",
				type : 'get',
				//async:true, 
				dataType : "text",
				//contentType : "application/json",
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",
				//traditional: true, 
				//data : JSON.stringify(param),
				success : function(result) {
						alert(result);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					//异常处理；
					alert("ajaxerror: " + JSON.stringify(XMLHttpRequest)
							+ " textStatus: " + XMLHttpRequest.statusText);
				}
			});
       	}
       	
       	function testpostdata(){
       		//var uid = $("#inpid").val();
       		var param={
    		   Id:1234,
    		   name:"tom",
    		   password:"1223"
    	   }
       		$.ajax({
               	url : "<%=request.getContextPath()%>/api/testpostdata",
				type : 'post',
				//async:true, 
				dataType : "json",
				contentType : "application/json",
// 				contentType : "application/x-www-form-urlencoded; charset=UTF-8",
				//traditional: true, 
				data : JSON.stringify(param),
// 				data:param,
				success : function(result) {
						alert(result);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					//异常处理；
					alert("ajaxerror: " + JSON.stringify(XMLHttpRequest)
							+ " textStatus: " + XMLHttpRequest.statusText);
				}
			});
       	}

		$("#sendGet").off("click").on("click", testget);
		$("#sendGetview").off("click").on("click", testgetview);
		$("#sendGetIds").off("click").on("click", testgetIds);
		$("#sendGids").off("click").on("click", testgids);
		$("#sendGds").off("click").on("click", testgds);
		$("#delids").off("click").on("click", testdelids);
		$("#sendPost").off("click").on("click", testpost);
		$("#sendTestPost").off("click").on("click", testpostdata);
		$("#mvcPost").off("click").on("click", mvcpost);
		$("#tmvPost").off("click").on("click", tmvpost);
		$("#subdata").off("click").on("click", sendData);
		$("#showdata").off("click").on("click", showData);
		$("#deldata").off("click").on("click", delData);
		$("#delbtn").off("click").on("click", del);
		$("#putbtn").off("click").on("click", put);
		$("#putvbtn").off("click").on("click", putreq);
		$("#dellistids").off("click").on("click", delListWithIds);
		$("#getdata").off("click").on("click", getData);
		
</script>
</html>