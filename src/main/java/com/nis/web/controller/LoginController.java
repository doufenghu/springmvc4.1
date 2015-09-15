package com.nis.web.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nis.util.Configurations;
import com.nis.util.Constants;
import com.nis.util.CookieUtil;
import com.nis.util.StringUtils;
import com.nis.web.security.IdGen;
import com.nis.web.security.SystemAuthorizingRealm.Principal;
import com.nis.web.security.UserUtils;
import com.nis.web.service.SystemService;

@Controller
public class LoginController extends BaseController {
	
	@Autowired
	private SystemService systemService;
	
	
	/**
	 * 默认进入系统动作，即跳转登录操作
	 * @return
	 */
	@RequestMapping(value="login",method=RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response, Model model){
		Principal principal = UserUtils.getPrincipal();
		
		logger.debug("login, active session size: {}"+systemService.getActiveSessions(false).size());
		
		if (Constants.TRUE.equals(Configurations.getStringProperty("notAllowRefreshIndex", "false"))) {
			try {
				CookieUtil.addCookie(response, "LOGINED", "false");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		// 如果已经登录，则跳转到管理首页
		if(principal != null && !principal.isMobileLogin()){
			return "redirect:" + adminPath + "/index";
		}
		
		return "login";
	}
	
	
	/**
	 * 登录失败时调用的方法，真正登录方法在shiro filter时实现。
	 * @return
	 */
	@RequestMapping(value="login",method=RequestMethod.POST)
	public String loginFail(HttpServletRequest request, HttpServletResponse response, Model model){
		
		Principal principal = UserUtils.getPrincipal();
		// 如果已经登录，则跳转到管理首页
		if(principal != null){
			return "redirect:" + adminPath + "/index";
		}

		String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
		boolean rememberMe = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM);
		boolean mobile = WebUtils.isTrue(request, Constants.DEFAULT_MOBILE_PARAM);
		String exception = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		String message = (String)request.getAttribute(Constants.DEFAULT_MESSAGE_PARAM);
		
		if (StringUtils.isBlank(message) || StringUtils.equals(message, "null")){
			message = "用户或密码错误, 请重试！";
		}
		
		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM, rememberMe);
		model.addAttribute(Constants.DEFAULT_MOBILE_PARAM, mobile);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, exception);
		model.addAttribute(Constants.DEFAULT_MESSAGE_PARAM, message);
		
		if (logger.isDebugEnabled()){
			logger.debug("login fail, active session size: "+systemService.getActiveSessions(false).size()+", message: "+message+", exception: "+exception);
		}
		
		// 非授权异常，登录失败，验证码加1。
		if (!UnauthorizedException.class.getName().equals(exception)){
			model.addAttribute("isValidateCodeLogin", UserUtils.isValidateCodeLogin(username, true, false));
		}
		
		// 验证失败清空验证码
		request.getSession().setAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY, IdGen.uuid());
		
		// 如果是手机登录，则返回JSON字符串
		if (mobile){
	        return renderString(response, model);
		}
		
		return "login";
	}
	
	@RequiresPermissions("user")
	@RequestMapping("/nis/index")
	public String index(HttpServletRequest request, HttpServletResponse response,ModelMap model){
		Principal principal = UserUtils.getPrincipal();
		// 登录成功后，验证码计算器清零
		UserUtils.isValidateCodeLogin(principal.getLoginName(), false, true);
		
		if (logger.isDebugEnabled()){
			logger.debug("show index, active session size: {}"+ systemService.getActiveSessions(false).size());
		}
		
		// 如果已登录，再次访问主页，则退出原账号。
		if (Constants.TRUE.equals(Configurations.getStringProperty("notAllowRefreshIndex","false"))){
			
			String logined;
			try {
				logined = CookieUtil.getValue(request, "LOGINED");
				if (StringUtils.isBlank(logined) || "false".equals(logined)){
					CookieUtil.addCookie(response, "LOGINED", "true");
				}else if (StringUtils.equals(logined, "true")){
					UserUtils.getSubject().logout();
					return "redirect:"  + "/login";
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			
		}
				
		// 如果是手机登录，则返回JSON字符串
		if (principal.isMobileLogin()){
			if (request.getParameter("login") != null){
				return renderString(response, principal);
			}
			if (request.getParameter("index") != null){
				return "home";
			}
			return "redirect:" + "/login";
		}
				
		model.addAttribute("adminPath", adminPath);
		return "/home";
	}
	
	@RequestMapping(value="/validateCode")
	public void validateCode(HttpServletRequest request, HttpServletResponse response,String captcha) {
		renderString(response, UserUtils.validateCodeIsValid(captcha));
	}
	
	
	
	
	 

}
