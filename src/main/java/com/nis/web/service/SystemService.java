package com.nis.web.service;

import java.util.Collection;

import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nis.domain.SysUser;
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
	
	
	
	
	
	
	
	

}
