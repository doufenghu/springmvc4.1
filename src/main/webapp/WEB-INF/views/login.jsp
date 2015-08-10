<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="login_page">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>登录平台</title>
	  <!-- Bootstrap framework -->
          <link rel="stylesheet" href="${pageContext.request.contextPath }/static/bootstrap/css/bootstrap.min.css" />
          <link rel="stylesheet" href="${pageContext.request.contextPath }/static/bootstrap/css/bootstrap-responsive.min.css" />
      <!-- theme color-->
          <link rel="stylesheet" href="${pageContext.request.contextPath }/static/css/blue.css" />
      <!-- tooltip -->    
          <link rel="stylesheet" href="${pageContext.request.contextPath }/static/lib/qtip2/jquery.qtip.min.css" />
      <!-- main styles -->
          <link rel="stylesheet" href="${pageContext.request.contextPath }/static/css/style.css" />
  
      <!-- Favicon -->
          <link rel="shortcut icon" href="${pageContext.request.contextPath }/static/img/favicon.ico" />
  
      <link href="${pageContext.request.contextPath }/static/css/css.css" rel='stylesheet' type='text/css'>
  
      <!--[if lte IE 8]>
          <script src="js/ie/html5.js"></script>
          <script src="js/ie/respond.min.js"></script>
      <![endif]-->
	
</head>
<body>
	  <div class="login_box">
            
            <form action="dashboard.html" method="post" id="login_form">
                <div class="top_b">NIS 平台登录系统</div>    
                <div class="alert alert-danger ${empty message ?'hide':'' } alert-login">
                   ${message }
                </div>
                <div class="cnt_b">
                    <div class="formRow">
                        <div class="input-prepend">
                            <span class="add-on"><i class="icon-user"></i></span><input type="text" id="username" name="username" placeholder="请填写登录账号"/>
                        </div>
                    </div>
                    <div class="formRow">
                        <div class="input-prepend">
                            <span class="add-on"><i class="icon-lock"></i></span><input type="password" id="password" name="password" placeholder="请填写登录密码" />
                        </div>
                    </div>
                    
                    <div class="formRow">
                        <div class="input-prepend">
                            <span class="add-on"><i class="icon-lock"></i></span>
                            <input type="text" id="captcha" name="captcha" style="width:100px"/>
                            <img alt="未获取验证码" src="${pageContext.request.contextPath }/captcha-image" id="captacha_image" style="cursor:pointer">
                        </div>
                    </div>
                    
                    <div class="formRow clearfix">
                        <label class="checkbox"><input type="checkbox" id="" name=""/>记住我（公共场所慎用）</label>
                    </div>
                </div>
                <div class="btm_b clearfix">
                	<input type="hidden" name="mobileLogin" value="true">
                    <button class="btn btn-inverse pull-right" type="submit">登录</button>
                    <span class="link_reg"><a href="#reg_form">Not registered? Sign up here</a></span>
                </div>  
            </form>
            
            <form action="dashboard.html" method="post" id="pass_form" style="display:none">
                <div class="top_b">Can't sign in?</div>    
                    <div class="alert alert-info alert-login">
                    Please enter your email address. You will receive a link to create a new password via email.
                </div>
                <div class="cnt_b">
                    <div class="formRow clearfix">
                        <div class="input-prepend">
                            <span class="add-on">@</span><input type="text" placeholder="Your email address" />
                        </div>
                    </div>
                </div>
                <div class="btm_b tac">
                    <button class="btn btn-inverse" type="submit">Request New Password</button>
                </div>  
            </form>
            
            <form action="dashboard.html" method="post" id="reg_form" style="display:none">
                <div class="top_b">Sign up to Gebo Admin</div>
                <div class="alert alert-login">
                    By filling in the form bellow and clicking the "Sign Up" button, you accept and agree to <a data-toggle="modal" href="#terms">Terms of Service</a>.
                </div>
                <div id="terms" class="modal hide fade" style="display:none">
                    <div class="modal-header">
                        <a class="close" data-dismiss="modal">×</a>
                        <h3>Terms and Conditions</h3>
                    </div>
                    <div class="modal-body">
                        <p>
                            Nulla sollicitudin pulvinar enim, vitae mattis velit venenatis vel. Nullam dapibus est quis lacus tristique consectetur. Morbi posuere vestibulum neque, quis dictum odio facilisis placerat. Sed vel diam ultricies tortor egestas vulputate. Aliquam lobortis felis at ligula elementum volutpat. Ut accumsan sollicitudin neque vitae bibendum. Suspendisse id ullamcorper tellus. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Vestibulum at augue lorem, at sagittis dolor. Curabitur lobortis justo ut urna gravida scelerisque. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Aliquam vitae ligula elit.
                            Pellentesque tincidunt mollis erat ac iaculis. Morbi odio quam, suscipit at sagittis eget, commodo ut justo. Vestibulum auctor nibh id diam placerat dapibus. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Suspendisse vel nunc sed tellus rhoncus consectetur nec quis nunc. Donec ultricies aliquam turpis in rhoncus. Maecenas convallis lorem ut nisl posuere tristique. Suspendisse auctor nibh in velit hendrerit rhoncus. Fusce at libero velit. Integer eleifend sem a orci blandit id condimentum ipsum vehicula. Quisque vehicula erat non diam pellentesque sed volutpat purus congue. Duis feugiat, nisl in scelerisque congue, odio ipsum cursus erat, sit amet blandit risus enim quis ante. Pellentesque sollicitudin consectetur risus, sed rutrum ipsum vulputate id. Sed sed blandit sem. Integer eleifend pretium metus, id mattis lorem tincidunt vitae. Donec aliquam lorem eu odio facilisis eu tempus augue volutpat.
                        </p>
                    </div>
                    <div class="modal-footer">
                        <a data-dismiss="modal" class="btn" href="#">Close</a>
                    </div>
                </div>
                <div class="cnt_b">
                    
                    <div class="formRow">
                        <div class="input-prepend">
                            <span class="add-on"><i class="icon-user"></i></span><input type="text" placeholder="Username" />
                        </div>
                    </div>
                    <div class="formRow">
                        <div class="input-prepend">
                            <span class="add-on"><i class="icon-lock"></i></span><input type="text" placeholder="Password" />
                        </div>
                    </div>
                    <div class="formRow">
                        <div class="input-prepend">
                            <span class="add-on">@</span><input type="text" placeholder="Your email address" />
                        </div>
                        <small>The e-mail address is not made public and will only be used if you wish to receive a new password.</small>
                    </div>
                     
                </div>
                <div class="btm_b tac">
                    <button class="btn btn-inverse" type="submit">Sign Up</button>
                </div>  
            </form>
            
            <div class="links_b links_btm clearfix">
                <span class="linkform"><a href="#pass_form">Forgot password?</a></span>
                <span class="linkform" style="display:none">Never mind, <a href="#login_form">send me back to the sign-in screen</a></span>
            </div>
        </div>
        
        <script src="${pageContext.request.contextPath }/static/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath }/static/js/jquery-migrate.min.js"></script>
        <script src="${pageContext.request.contextPath }/static/js/jquery.actual.min.js"></script>
        <script src="${pageContext.request.contextPath }/static/lib/validation/jquery.validate.min.js"></script>
        <script src="${pageContext.request.contextPath }/static/bootstrap/js/bootstrap.min.js"></script>
        <script>
            $(document).ready(function(){
            	
            	$("#captacha_image").click(function(){
            		$(this).hide().attr("src","${pageContext.request.contextPath }/captcha-image?"+ Math.floor(Math.random()*100))
            			.fadeIn();
            	});
                
                //* boxes animation
                form_wrapper = $('.login_box');
                function boxHeight() {
                    form_wrapper.animate({ marginTop : ( - ( form_wrapper.height() / 2) - 24) },400);   
                };
                form_wrapper.css({ marginTop : ( - ( form_wrapper.height() / 2) - 24) });
                $('.linkform a,.link_reg a').on('click',function(e){
                    var target  = $(this).attr('href'),
                        target_height = $(target).actual('height');
                    $(form_wrapper).css({
                        'height'        : form_wrapper.height()
                    }); 
                    $(form_wrapper.find('form:visible')).fadeOut(400,function(){
                        form_wrapper.stop().animate({
                            height   : target_height,
                            marginTop: ( - (target_height/2) - 24)
                        },500,function(){
                            $(target).fadeIn(400);
                            $('.links_btm .linkform').toggle();
                            $(form_wrapper).css({
                                'height'        : ''
                            }); 
                        });
                    });
                    e.preventDefault();
                });
                
                //* validation
                $('#login_form').validate({
                    onkeyup: false,
                    errorClass: 'error',
                    validClass: 'valid',
                    rules: {
                        username: { required: true},
                        password: { required: true}
                    },
	  				  messages: {
	  				  	'username':{
	  						required:'登录账号必须填写'
	  					},
	  				  	 'password':{
	  				       required: '密码必须填写'
	  				     }
	  				  }  ,
                    highlight: function(element) {
                        $(element).closest('div').addClass("f_error");
                        setTimeout(function() {
                            boxHeight()
                        }, 200)
                    },
                    unhighlight: function(element) {
                        $(element).closest('div').removeClass("f_error");
                        setTimeout(function() {
                            boxHeight()
                        }, 200)
                    },
                    errorPlacement: function(error, element) {
                        $(element).closest('div').append(error);
                    }
                });
            });
        </script>

</body>
</html>