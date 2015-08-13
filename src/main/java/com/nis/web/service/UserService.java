package com.nis.web.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.management.RuntimeErrorException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nis.domain.SysFunctionMenu;
import com.nis.domain.SysUser;
import com.nis.web.dao.SysFunctionMenuDao;
import com.nis.web.dao.UserDao;

@Service
public class UserService {
	/**@Resource**/
	@Autowired
	private UserDao userDao;
	@Autowired
	private SysFunctionMenuDao sysFunctionMenuDao;
	
	public SysUser getUserById(Long id) {
		return userDao.getUserById(id);
	}
	
	public SysUser getUserByLoginName(String loginName) {
		return userDao.getUserByLoginName(loginName);
	}
	
	public void userRegister(SysUser user){
		
		userDao.insertUser(user);
		
	}
	
	public SysUser getUserByIdWithDepartment(Long userId) {
		return userDao.getUserWithRelation(new SysUser(userId, null));
	}
	
	/**
	 * 通过当前用户的具有的功能菜单
	 * @param userId
	 * @return
	 */
	public List<SysFunctionMenu> getTreeListByUserId(Long userId) {
		return  sysFunctionMenuDao.getSysFunctionMenuByUserId(userId);
	}
	
	/**
	 * 获取顶级菜单树目录
	 * @param userId
	 * @return
	 */
	public List<SysFunctionMenu> getTopTreeListByUserId(Long userId) {
		
		List<SysFunctionMenu> sysFunctionMenuList = new ArrayList<SysFunctionMenu>();
		
		for (SysFunctionMenu menu:getTreeListByUserId(userId)) {
			if (menu.getMenuLevel() == 1) {
				sysFunctionMenuList.add(menu);
			}
		}
		
		return sysFunctionMenuList;
	}
	
	

	

}
