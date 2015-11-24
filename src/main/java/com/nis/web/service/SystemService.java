package com.nis.web.service;

import java.util.Collection;
import java.util.List;

import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.nis.domain.SysRole;
import com.nis.domain.SysUser;
import com.nis.web.dao.SysRoleDao;
import com.nis.web.dao.UserDao;
import com.nis.web.security.SessionDAO;
import com.nis.web.security.UserUtils;

@Service
public class SystemService {
	
	@Autowired
	private SessionDAO sessionDao;
	
	@Autowired
	private UserDao userDao;
	
	
	
	public Collection<Session> getActiveSessions(boolean includeLeave) {
		return  sessionDao.getActiveSessions(includeLeave);
	}
	
	
	public Collection<Session> getActiveSessions(boolean includeLeave, Object principal, Session filterSession) {
		return  sessionDao.getActiveSessions(includeLeave, principal, filterSession);
	}
	
	public void deleteSession(Session session) {
		sessionDao.delete(session);
	}
	
	
	public SysUser getUserByLoginName(String loginName) {
		return UserUtils.getByLoginName(loginName);
	}
	
	
	public SysUser assignUserToRole(SysRole role, SysUser user) {
		if (user == null){
			return null;
		}
		List<Long> roleIds = user.getRoleIdList();
		if (roleIds.contains(role.getId())) {
			return null;
		}
		user.getUserRoleList().clear();
		user.getUserRoleList().add(role);
		userDao.insertUserRole(user);
		UserUtils.clearCache(user);
		return user;
	}
	
	public Boolean outUserInRole(SysRole role, SysUser user) {
		List<SysRole> roles = user.getUserRoleList();
		for (SysRole e : roles){
			if (e.getId().equals(role.getId())){
				roles.remove(e);
				userDao.removeUserInRole(user.getId(),role.getId());
				return true;
			}
		}
		return false;
	}
	
	
	
	
	
	
	

}
