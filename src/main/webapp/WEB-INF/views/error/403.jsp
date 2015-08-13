<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="error_page">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Error Page - 403</title>
		<!-- Bootstrap framework -->
            <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.min.css" />
            <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap-responsive.min.css" />
		<!-- main styles -->
            <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css" />
			
            <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/font_Jockey_one.css" />
            
	</head>
	<body>

		<div class="error_box">
			<h1>403 Page/页面未找到</h1>
			<p>禁止访问，请确定您是否有权限！</p>
			<a href="javascript:history.back()" class="back_link btn btn-small">Go back</a>
		</div>

	</body>
</html>