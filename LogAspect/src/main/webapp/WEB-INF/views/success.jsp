<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="com.entity.User"%>
<%@ page import="org.apache.commons.lang3.StringUtils"%>
<html>
<head>
		<script>
	     	if(window.location.protocol=="https:"){
	     		document.cookie = "protocol=https; path=/; domain=.test.com;";
	     	}else{
	     		document.cookie = "protocol=http; path=/; domain=.test.com;";
	     	}
    	</script>
	</head>
<body>
	<% pageContext.setAttribute("date", new java.util.Date());%>
	<h3>
		中文日期显示：
		<fmt:setLocale value="zh_CN" />
		<%-- 		<fmt:setLocale value="en_US" /> --%>
		<fmt:formatDate value="${date}" />
		<fmt:formatNumber value="1234567890" type="currency"
			currencySymbol="¥" />
		－－ ＄1,234,567,890.00(那个货币的符号和当前web服务器的 local 设定有关)

		<fmt:formatNumber value="3345345.5646" var="result" type="currency"
			maxFractionDigits="2" groupingUsed="true" />

		人民币3345345.5646格式化：
		<c:out value="${result}" />
		<br> <br>

		<fmt:formatNumber value="3.1415926" var="result" maxFractionDigits="2"
			groupingUsed="false" />
		3.1415926保留两位小数格式化：
		<c:out value="${result}" />
		<br> <br>

		<fmt:formatNumber value="0.4334534" type="percent" var="result"
			maxFractionDigits="2" groupingUsed="false" />

		0.4334534按百分比式化：
		<c:out value="${result}" />
	</h3>
	<h3>
		英文日期显示：
		<fmt:setLocale value="en_US" />
		<fmt:formatDate value="${date}" />
	</h3>
	<c:set var="now" value="<%=new java.util.Date()%>" />

	<p>
		日期格式化 (1):
		<fmt:formatDate type="time" value="${now}" />
	</p>
	<p>
		日期格式化 (2):
		<fmt:formatDate type="date" value="${now}" />
	</p>
	<p>
		日期格式化 (3):
		<fmt:formatDate type="both" value="${now}" />
	</p>
	<p>
		日期格式化 (4):
		<fmt:formatDate type="both" dateStyle="short" timeStyle="short"
			value="${now}" />
	</p>
	<p>
		日期格式化 (5):
		<fmt:formatDate type="both" dateStyle="medium" timeStyle="medium"
			value="${now}" />
	</p>
	<p>
		日期格式化 (6):
		<fmt:formatDate type="both" dateStyle="long" timeStyle="long"
			value="${now}" />
	</p>
	<p>
		日期格式化 (7):
		<fmt:formatDate pattern="yyyy-MM-dd" value="${now}" />
	</p>
	<h2>Hello Success! ${name}</h2>
	<h2>${r}</h2>
	<h2>URI:${uri}</h2>
	<h2>URL:${url}</h2>
	<c:forEach items="${m}" var="item">
		<span>key:${item.key}</span>
		<span>val:${item.value}</span>
		<br>
	</c:forEach>
	<c:set var="salary" scope="session" value="${2000*2}" />
	<c:out value="${salary}" />
	<c:set var="str" value="stringStRiNg" />
	<%-- 	<c:set var="strs" value="${fn:split('a#b','#'}"/> --%>
	<c:set var="string1" value="www runoob com" />
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
	<c:set var="t" value="tr" />
	<c:out value="${str} = ${fn:length(str)}" />
	<c:out value="${fn:toLowerCase(str)}" />
	<c:out value="${fn:toUpperCase(' ')}" />
	<c:out value="${fn:toUpperCase('AbCdEfg')}" />
	<br>
	<c:out value="${fn:substring('asdfefg',0,3)}" />
	<br>
	<c:out value="${fn:substringAfter('asdf','s')}" />
	<Br>
	<c:out value="${fn:substringBefore(str,'g')}" />
	<Br>
	<c:out value="${fn:trim(' sd dew e ')}" />
	<Br>
	<c:out value=" d sd dew e " />
	<Br>
	<c:out value="${fn:replace(str,'ing','IN')}" />
	<Br>
</body>
<%
	//String usercode = request.getParameter("usercode");//用request得到 
	User user = (User) request.getAttribute("u");
	String uname = "";
	if(user != null && StringUtils.isNotBlank(user.getName())){
		uname = user.getName();
		out.println(user.getPassword());
	}else{
		user = new User();
	}
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
	var n1 = "<%if(StringUtils.isNotBlank(user.getName())){%><%=user.getName()%><%}%>";
	var name = "${u.name}";
	var arrstr = "${users}";
	var list = "${list}";
	var mapuser = "${mapuser}";
	
	<c:forEach items="${mapuser}" var="item">
	console.log("key:${item.key} val:${item.value}");
	</c:forEach>
	//console.log(u);
	<c:if test="${not empty(users)}">
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