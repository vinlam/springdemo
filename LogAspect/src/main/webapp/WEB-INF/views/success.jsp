<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<body>
	<h2>Hello Success! ${name}</h2>
	<h2>${r}</h2>
	<c:forEach items="${m}" var="item">
		<span>key:${item.key}</span>
		<span>val:${item.value}</span>
		<br>
	</c:forEach>
</body>
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
	
	
	<c:if test="${not empty(users) && users.size() > 0}">
	<c:forEach items="${users}" var="user">
    	//console.log("${user.name}");
    	var obj = {};
    	obj.name = "${user.name}";
    	obj.age = "${user.age}";
    	obj.password = "${user.password}";
    	users.push(obj);
  
//     	<c:if test="${!empty user}">
//     	<c:forEach items="${user}" var="u">
    		
//     	</c:forEach>
//     	</c:if>
	</c:forEach>
	</c:if>
	console.log(JSON.stringify(users));
</script>
</html>