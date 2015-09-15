<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<%@include file="/WEB-INF/include/header.jsp" %>	
	<title>登录平台</title>
	  <style type="text/css">
	      html,body,table{background-color:#f5f5f5;width:100%;text-align:center;}.form-signin-heading{font-family:Helvetica, Georgia, Arial, sans-serif, 黑体;font-size:36px;margin-bottom:20px;color:#0663a2;}
	      .form-signin{position:relative;text-align:left;width:300px;padding:25px 29px 29px;margin:0 auto 20px;background-color:#fff;border:1px solid #e5e5e5;
	        	-webkit-border-radius:5px;-moz-border-radius:5px;border-radius:5px;-webkit-box-shadow:0 1px 2px rgba(0,0,0,.05);-moz-box-shadow:0 1px 2px rgba(0,0,0,.05);box-shadow:0 1px 2px rgba(0,0,0,.05);}
	      .form-signin .checkbox{margin-bottom:10px;color:#0663a2;} .form-signin .input-label{font-size:16px;line-height:23px;color:#999;}
	      .form-signin .input-block-level{font-size:16px;height:auto;margin-bottom:15px;padding:7px;*width:283px;*padding-bottom:0;_padding:7px 7px 9px 7px;}
	      .form-signin .btn.btn-large{font-size:16px;} .form-signin #themeSwitch{position:absolute;right:15px;bottom:10px;}
	      .form-signin div.validateCode {padding-bottom:15px;} .mid{vertical-align:middle;}
	      .header{height:80px;padding-top:20px;} .alert{position:relative;width:300px;margin:0 auto;*padding-bottom:0px;}
	      label.error{background:none;width:270px;font-weight:normal;color:inherit;margin:0;}
	      #captacha_image {
	      	cursor:pointer;
	      }
    </style>
	
</head>
<body>
	 
	 <div class="header">
		<div id="messageBox" class="alert alert-error ${empty message ? 'hide' : ''}">
			<label id="loginError" class="error">${message}</label>
		</div>
	</div>
	<h1 class="form-signin-heading">${fns:getStringProperty('productName','NIS')}</h1>
	<form id="loginForm" class="form-signin" action="${pageContext.request.contextPath }/login" method="post">
		<label class="input-label" for="username">登录名</label>
		<input type="text" id="username" name="username" class="input-block-level required" value="${username}" placeholder="请填写登录账号"/>
		<label class="input-label" for="password">密码</label>
		<input type="password" id="password" name="password" class="input-block-level required" placeholder="请填写登录密码"/>
		
		<c:if test="${not empty isValidateCodeLogin or isValidateCodeLogin==true}">
			 <div class="formRow ${ (empty isValidateCodeLogin or isValidateCodeLogin==false) ?'hide':''  }">
                <label class="input-label mid" for="captcha">验证码</label>
                <input type="text" id="captcha" name="captcha" maxlength="5" class="txt required" style="font-weight:bold;width:60px;margin-bottom:0;"/>
				<img alt="未获取验证码" src="${pageContext.request.contextPath}/captcha-image" id="captacha_image" class="mid"/>
             </div>
                    
		</c:if>
		<%--
		<label for="mobile" title="手机登录"><input type="checkbox" id="mobileLogin" name="mobileLogin" ${mobileLogin ? 'checked' : ''}/></label> --%>
		
		<input class="btn btn-large btn-primary" type="submit" value="登 录"/>&nbsp;&nbsp;
		<label for="rememberMe" title="下次不需要再登录"><input type="checkbox" id="rememberMe" name="rememberMe" ${rememberMe ? 'checked' : ''}/> 记住我（公共场所慎用）</label>
		
		
			<!--[if lte IE 6]><script type="text/javascript">$('#themeSwitch').hide();</script><![endif]-->
	</form>
	<div class="footer">
		Copyright &copy; 2015-${fns:getStringProperty('copyrightYear','2015')} <a href="${pageContext.request.contextPath}${fns:getStringProperty('frontPath','/i')}">${fns:getStringProperty('productName','NIS')}</a> - Powered By <a href="${pageContext.request.contextPath}" target="_blank">JeeSite</a> ${fns:getStringProperty('version','1.0.0')} 
	</div>
	<script src="${pageContext.request.contextPath}/static/flash/zoom.min.js" type="text/javascript"></script>

        <script>
            $(document).ready(function(){
            	
            	$("#captacha_image").on("click",function(){
            		$(this).hide().attr("src","${pageContext.request.contextPath }/captcha-image?"+ Math.floor(Math.random()*100))
            			.fadeIn();
            	});
            	
            	
            	$("#loginForm").validate({
    				rules: {
    					username: { required: true},
                        password: { required: true},
                        captcha: {remote: "${pageContext.request.contextPath}/validateCode"}
    				},
    				messages: {
    					username: {required: "请填写用户名."},password: {required: "请填写密码."},
    					captcha: {remote: "验证码不正确.", required: "请填写验证码."}
    				},
    				errorLabelContainer: "#messageBox",
    				errorPlacement: function(error, element) {
    					error.appendTo($("#loginError").parent());
    				} 
    			});
                
                
                
             // 如果在框架或在对话框中，则弹出提示并跳转到首页
        		if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
        			alert('未登录或登录超时。请重新登录，谢谢！');
        			top.location = "${pageContext.request.contextPath }";
        		}
                
                
            });
        </script>

</body>
</html>