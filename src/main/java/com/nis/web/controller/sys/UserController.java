package com.nis.web.controller.sys;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nis.domain.Page;
import com.nis.domain.SysRole;
import com.nis.domain.SysUser;
import com.nis.util.DateUtils;
import com.nis.util.StringUtil;
import com.nis.util.StringUtils;
import com.nis.util.excel.ExportExcel;
import com.nis.web.controller.BaseController;
import com.nis.web.security.UserUtils;

@Controller
@RequestMapping("${adminPath}/sys/user")
public class UserController extends BaseController{
	
	@ModelAttribute
	public SysUser get(@RequestParam(required=false) Long id) {
		if (!StringUtil.isEmpty(id)){
			return userService.getUserByIdWithRelation(id);
		}else{
			return new SysUser();
		}
	}
	
	
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = {"index"})
	public String index(SysUser user, Model model) {
		return "/sys/userIndex";
	}
	
	/**
	 * 进入用户添加或修改页面
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping(value={"form"})
	public String form(SysUser user, Model model) {
		
		if (user.getOffice() == null || user.getOffice().getId() == null) {
			user.setOffice(UserUtils.getUser().getUserOfficeList().get(0));
		} else {
			user.setOffice(user.getUserOfficeList().get(0));
		}
		
		model.addAttribute("user", user);
		model.addAttribute("allRoles", roleService.findAllRole());
		
		return "/sys/userForm";
	}
	
	
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "saveOrUpdate")
	public String saveOrUpdate(SysUser user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		
		
		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(user.getNewPassword())) {
			user.setPassword(StringUtils.entryptPassword(user.getNewPassword()));
		}
		/*if (!beanValidator(model, user)){
			return form(user, model);
		}*/
		if (!"true".equals(checkLoginName(user.getOldLoginId(), user.getLoginId()))){
			addMessage(model, "保存用户'" + user.getLoginId() + "'失败，登录名已存在");
			return form(user, model);
		}
		// 角色数据有效性验证，过滤不在授权内的角色
		List<SysRole> roleList = Lists.newArrayList();
		List<Long> roleIdList = user.getRoleIdList();
		for (SysRole r : roleService.findAllRole()) {
			if (roleIdList.contains(r.getId())){
				roleList.add(r);
			}
		}
		user.setUserRoleList(roleList);
		// 保存用户信息
		userService.saveOrUpdate(user);
		// 清除当前用户缓存
		if (user.getLoginId().equals(UserUtils.getUser().getLoginId())){
			UserUtils.clearCache();
		}
		addMessage(redirectAttributes, "保存用户'" + user.getLoginId() + "'成功");
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}
	
	
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "delete")
	public String delete(SysUser user, RedirectAttributes redirectAttributes) {
		
		if (UserUtils.getUser().getId().equals(user.getId())){
			addMessage(redirectAttributes, "删除用户失败, 不允许删除当前用户");
		}else if (user.isAdmin()){
			addMessage(redirectAttributes, "删除用户失败, 不允许删除超级管理员用户");
		}else{
			userService.deleteUser(user);
			addMessage(redirectAttributes, "删除用户成功");
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}
	
	
	/**
	 * 验证登录名是否有效
	 * @param oldLoginName
	 * @param loginName
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "checkLoginName")
	public String checkLoginName(String oldLoginId, String loginId) {
		if (loginId !=null && loginId.equals(oldLoginId)) {
			return "true";
		} else if (loginId !=null && systemService.getUserByLoginName(loginId) == null) {
			return "true";
		}
		return "false";
	}
	
	
	
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = {"list"})
	public String list(SysUser user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysUser> page = userService.findUser(new Page<SysUser>(request, response), user);
        model.addAttribute("page", page);
		return "/sys/userList";
	}
	
	/**
	 * 导出用户数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:view")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(SysUser user, HttpServletRequest request, HttpServletResponse response, 
    		RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户数据-"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SysUser> page = userService.findUser(new Page<SysUser>(request, response, -1), user);
    		new ExportExcel("用户数据", SysUser.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
    }
    
    

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) Long officeId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<SysUser> list = userService.findUserByOfficeId(officeId);
		for (int i=0; i<list.size() ; i++){
			SysUser e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", "u_"+e.getId());
			map.put("pId", officeId);
			map.put("name", StringUtils.replace(e.getName(), " ", ""));
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 用户信息显示及保存
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "info")
	public String info(SysUser user, HttpServletResponse response, Model model) {
		SysUser currentUser = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getName())){
			currentUser.setEmail(user.getEmail());
			currentUser.setPhoto(user.getPhoto());
			userService.updateUserInfo(currentUser);
			model.addAttribute("message", "保存用户信息成功");
		}
		model.addAttribute("user", currentUser);
		return "/sys/userInfo";
	}
    
	
	/**
	 * 返回用户信息
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "infoData")
	public SysUser infoData() {
		return UserUtils.getUser();
	}
	
	
	/**
	 * 修改个人用户密码
	 * @param oldPassword
	 * @param newPassword
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "modifyPwd")
	public String modifyPwd(String oldPassword, String newPassword, Model model) {
		SysUser user = UserUtils.getUser();
		if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)){
			
			if (StringUtils.validatePassword(oldPassword, user.getPassword())){
				userService.updatePasswordById(user.getId(), user.getLoginId(), newPassword);
				model.addAttribute("message", "修改密码成功");
			}else{
				model.addAttribute("message", "修改密码失败，旧密码错误");
			}
		}
		model.addAttribute("user", user);
		return "/sys/userModifyPwd";
	}
	
	
	

}
