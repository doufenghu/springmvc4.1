<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SpringMVC Test</title>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
<script type="text/javascript">
	$(function(){
		
		$("#testJson").click(function(){
			$.post("${pageContext.request.contextPath}/helloworld/testJson",{},function(data){
				 var menuName="";
				for(var i=0;i<data.length;i++){
					menuName+=data[i].menuName+"_";
				}
				
			});
		});
	});
</script>
</head>
<body>

	<a href="javascript:void(0);" id="testJson">testJSON</a>

	<br/>
	<a href="/SpringMvc4.1/helloworld/hello">testMVC</a>
	
	<form action="/SpringMvc4.1/helloworld/testMethod" method="post">
		<input type="submit" value="testMethod"/>
	</form>
	
	<a href="/SpringMvc4.1/helloworld/testParams?username=darnell&age=11">testparams</a>
	
	<a href="/SpringMvc4.1/helloworld/testAntPath/abc/test">testANtpath1</a>
	<a href="/SpringMvc4.1/helloworld/testAntPath/test">testANtpath1</a>
	<br/>
	<a href="/SpringMvc4.1/helloworld/testPathVariable/100">testPathVariable</a>
	<a href="/SpringMvc4.1/helloworld/testRequestParam?username=darnell&age=28">testRequestParam</a>
	
	
	<form action="/SpringMvc4.1/helloworld/testpojo" method="post">
		username:<input type="text" name="username"/><br/>
		passowrd:<input type="password" name="password"/><br/>
		email:<input type="text" name="email"/><br/>
		age:<input type="text" name="age"/><br/>
		
		city:<input type="text" name="address.city"/><br/>
		province:<input type="text" name="address.province"/><br/>
		
		<input type="submit" name="sumit pojo"/><br/>
	</form>
	
	<br/>
	
	<a href="/SpringMvc4.1/helloworld/testModelAndView">testModelAndView</a>
	
	<a href="/SpringMvc4.1/helloworld/testMap">testMap</a>
	
	<a href="/SpringMvc4.1/helloworld/testSessionAttribute">testSessionAttribute</a>
	
</body>
</html>