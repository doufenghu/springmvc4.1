<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>success!</h2>
	<h4>time:${requestScope.time }</h4>
	<h4>names:${requestScope.names }</h4>
	
	<h4> session object:${sessionScope.userObject }</h4>
	<h4> session name:${sessionScope.username }</h4>
	<h4>权限树数据：${menuList }</h4>
	
</body>
</html>