<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<body>
<h2>Hello Success! ${name}</h2>
<h2>${r}</h2>
</body>
<script type="text/javascript">
	document.cookie = "ck=2; expires=1; domain=test.com;path=/";
	console.log(window.location.href);
	console.log(window.location.href+"${r}");
	console.log("${m.k}"+"---------------"+"${m.a}");
	console.log(window.location.protocol);
</script>
</html>