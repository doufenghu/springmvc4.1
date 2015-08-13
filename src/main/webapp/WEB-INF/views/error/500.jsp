<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="error_page">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Error Page - 500</title>
		<!-- Bootstrap framework -->
            <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.min.css" />
            <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap-responsive.min.css" />
		<!-- main styles -->
            <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css" />
			
            <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/font_Jockey_one.css" />
            
	</head>
	<body>

		<div class="error_box">
			<h1>500 Page/服务器内部错误</h1>
			<p>您正在访问的网站出现了服务器问题，该问题阻止了页面的显示！</p>
			<a href="javascript:history.back()" class="back_link btn btn-small">Go back</a>
		</div>

	</body>
</html>