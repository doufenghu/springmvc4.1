package com.nis.web.controller;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nis.datasource.CustomerContextHolder;
import com.nis.domain.SysFunctionMenu;
import com.nis.domain.SysUser;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
	
	
	@RequestMapping("/list")
	public String userList(){
		SysUser user = userService.getUserByIdWithDepartment(1l);
		System.out.println(user.getUserDeptList().size());
		
		return "success";
	}
	
	
	@RequestMapping("/tree")
	public String treeList(ModelMap map){
		List<SysFunctionMenu> menuList = userService.getTopTreeListByUserId(5l);
		map.put("menuList", menuList);
		return "success";
	}
	
	@RequestMapping("/addUser")
	public String addUser(){
		SysUser user = new SysUser();
		user.setLoginId("zhangsan");
		user.setPassword("123");
		user.setEmail("zhangsan@intranet.com");
		user.setCreateTime(new Date());
		user.setStatus(1);
		userService.userRegister(user);
		
		return "success";
	}
	
	

}
