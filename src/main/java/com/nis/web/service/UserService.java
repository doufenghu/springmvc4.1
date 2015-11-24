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

import com.nis.domain.Page;
import com.nis.domain.SysFunctionMenu;
import com.nis.domain.SysRole;
import com.nis.domain.SysUser;
import com.nis.exceptions.ServiceException;
import com.nis.util.CacheUtils;
import com.nis.util.StringUtil;
import com.nis.util.StringUtils;
import com.nis.web.dao.SysFunctionMenuDao;
import com.nis.web.dao.UserDao;
import com.nis.web.security.UserUtils;

@Service
public class UserService extends BaseService {
	/**@Resource**/
	@Autowired
	private UserDao userDao;
	@Autowired
	private SysFunctionMenuDao sysFunctionMenuDao;
	
	
	/**
	 * 获取用户
	 * @param id
	 * @return
	 */
	public SysUser getUser(String id) {
		return UserUtils.get(id);
	}

	
	
	public Page<SysUser> findUser(Page<SysUser> page, SysUser user) {
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		user.getSqlMap().put("dsf", dataScopeFilter(user.getCurrentUser(), "o", "u"));
		// 设置分页参数
		user.setPage(page);
		// 执行分页查询
		page.setList(userDao.findList(user));
		return page;
	}
	
	
	
	public SysUser getUserById(Long id) {
		return userDao.getUserById(id);
	}
	
	public SysUser getUserByLoginName(String loginName) {
		return userDao.getUserByLoginName(loginName);
	}
	
	
	public SysUser getUserByIdWithRelation(Long userId) {
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



	public void saveOrUpdate(SysUser user) {
		
		if (StringUtil.isEmpty(user.getId())) {
			user.setCreateTime(new Date());
			user.setStatus(1);
			userDao.insert(user);
		} else {
			SysUser oldUser = userDao.getUserWithRelation(user);
			// 清除原用户机构用户缓存
			if (!StringUtil.isEmpty(oldUser.getUserOfficeList())) {
				CacheUtils.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + oldUser.getUserOfficeList().get(0).getId());
			}
			userDao.update(user);
			userDao.deleteUserRole(user.getId());
			userDao.deleteUserOffice(user.getId());
		}
		
		
		if (!StringUtil.isEmpty(user.getOffice())) {
			user.addOffice(user.getOffice());
			userDao.insertUserOffice(user);
		}else {
			throw new ServiceException(user.getLoginId() + "没有设置部门！");
		}
		
		if (!StringUtil.isEmpty(user.getUserRoleList())) {
			userDao.insertUserRole(user);
		} else {
			throw new ServiceException(user.getLoginId() + "没有设置角色！");
		}
		
		// 将当前用户同步到Activiti
		//saveActivitiUser(user);	
		UserUtils.clearCache(user);	
		// 清除权限缓存
		//systemRealm.clearAllCachedAuthorizationInfo();
		
		
		
		
	}
	
	
	public void deleteUser(SysUser user) {
		userDao.delete(user);
		// 同步到Activiti
		//deleteActivitiUser(user);
		// 清除用户缓存
		UserUtils.clearCache(user);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}
	
	
	public void updatePasswordById(Long id, String loginId, String newPassword) {
		SysUser user = new SysUser(id,loginId);
		user.setPassword(StringUtils.entryptPassword(newPassword));
		userDao.updatePasswordById(user);
		UserUtils.clearCache(user);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}




	public List<SysUser> findUserByRoleId(Long id) {
		return userDao.findUserByRoleId(id);
	}



	public List<SysUser> findUserByOfficeId(Long id) {
		return userDao.findUserByOfficeId(id);
	}



	public void updateUserInfo(SysUser user) {
		userDao.updateUserInfo(user);
		// 清除用户缓存
		UserUtils.clearCache(user);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
		
		
	}
	

	

}
