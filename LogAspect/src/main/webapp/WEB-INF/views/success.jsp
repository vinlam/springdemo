<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<body>
<h2>Hello Success! ${name}</h2>
<h2>${r}</h2>
</body>
<script type="text/javascript">
	console.log(window.location.href);
	console.log(window.location.href+"${r}");
</script>
</html>