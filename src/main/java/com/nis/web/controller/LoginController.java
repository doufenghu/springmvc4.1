package com.nis.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController extends BaseController {
	
	
	/**
	 * 默认进入系统动作，即跳转登录操作
	 * @return
	 */
	@RequestMapping(value="login",method=RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response, Model model){
		
		model.addAttribute("message", "info");
		
		return "login";
	}
	

}
