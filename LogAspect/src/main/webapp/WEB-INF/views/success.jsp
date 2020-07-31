<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="com.entity.User" %>
<html>
<body>
	<h2>Hello Success! ${name}</h2>
	<h2>${r}</h2>
	<c:forEach items="${m}" var="item">
		<span>key:${item.key}</span>
		<span>val:${item.value}</span>
		<br>
	</c:forEach>
	<c:set var="salary" scope="session" value="${2000*2}"/>
	<c:out value="${salary}"/>
	<c:set var="str" value="stringStRiNg"/>
<%-- 	<c:set var="strs" value="${fn:split('a#b','#'}"/> --%>
	<c:set var="string1" value="www runoob com"/>
	<c:set var="string2" value="${fn:split(string1, ' ')}" />
	<c:set var="string3" value="${fn:join(string2, '-')}" />
	
	<p>string3 字符串 : ${string3}</p>
	
	<c:set var="string4" value="${fn:split(string3, '-')}" />
	<c:set var="string5" value="${fn:join(string4, ' ')}" />
	
	<p>string5 字符串: ${string5}</p>
	<c:forEach items="${string2}" var="item">
		<span>${item}</span>
		<br>
	</c:forEach>
	<c:set var="t" value="tr"/>
	<c:out value="${str} = ${fn:length(str)}"/>
	<c:out value="${fn:toLowerCase(str)}"/>
	<c:out value="${fn:toUpperCase(' ')}"/>
	<c:out value="${fn:toUpperCase('AbCdEfg')}"/><br>
	<c:out value="${fn:substring('asdfefg',0,3)}"/><br>
	<c:out value="${fn:substringAfter('asdf','s')}"/><Br>
	<c:out value="${fn:substringBefore(str,'g')}"/><Br>
	<c:out value="${fn:trim(' sd dew e ')}"/><Br>
	<c:out value=" d sd dew e "/><Br>
	<c:out value="${fn:replace(str,'ing','IN')}"/><Br>
</body>
<% 
//String usercode = request.getParameter("usercode");//用request得到 
User user=(User)request.getAttribute("u");
String uname = user.getName();
out.println(user.getPassword());
%>
<script type="text/javascript"
	src="/LogAspect/resources/js/jquery.min.js"></script>
<script type="text/javascript">
	document.cookie = "ck=2; expires=1; domain=test.com;path=/";
	console.log(window.location.href);
	console.log(window.location.href+"${r}");
	console.log("${m}");
	console.log("${m.k}"+"---------------"+"${m.a}");
	console.log(window.location.protocol);
	var users = [];
	var u = "${pojo}";
	var n = "${u}";
	var n = "<%=uname%>";
	var n1 = "<%=user.getName()%>";
	var name = "${u.name}";
	var arrstr = "${users}";
	var list = "${list}";
	var mapuser = "${mapuser}";
	
	<c:forEach items="${mapuser}" var="item">
	console.log("key:${item.key} val:${item.value}");
	</c:forEach>
	//console.log(u);
	<c:if test="${not empty(users) && users.size() > 0}">
	<c:forEach items="${users}" var="user">
    	console.log("${user.name}");
    	var obj = {};
    	obj.name = "${user.name}";
    	obj.age = "${user.age}";
    	obj.password = "${user.password}";
    	users.push(obj);
  
	</c:forEach>
	</c:if>
	
	console.log(JSON.stringify(users));
</script>
</html>