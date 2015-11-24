package com.nis.web.security;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.code.kaptcha.Constants;
import com.nis.domain.SysFunctionMenu;
import com.nis.domain.SysMenu;
import com.nis.domain.SysRole;
import com.nis.domain.SysUser;
import com.nis.util.Configurations;
import com.nis.util.Encodes;
import com.nis.util.LogUtils;
import com.nis.util.StringUtil;
import com.nis.util.StringUtils;
import com.nis.util.TreeUtil;
import com.nis.web.service.SystemService;


/**
 * 系统安全认证实现类
 * @author 
 * @version 2014-7-5
 */
@Service
public class SystemAuthorizingRealm extends AuthorizingRealm {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SystemService systemService;

	/**
	 * 认证回调函数, 登录时调用
	 * Authentication 存放用户名、密码地方，身份认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
		
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		
		int activeSessionSize = systemService.getActiveSessions(false).size();
		
		
		if (logger.isDebugEnabled()){
			logger.debug("login submit, active session size: {}, username: {}", activeSessionSize, token.getUsername());
		}
		
		// 校验登录验证码
		if (UserUtils.isValidateCodeLogin(token.getUsername(), false, false)){
			
			if (UserUtils.validateCodeIsValid(token.getCaptcha())){
				throw new AuthenticationException("msg:验证码错误, 请重试.");
			}
		}
		
		// 校验用户名密码
		SysUser user = systemService.getUserByLoginName(token.getUsername());
		if (user != null) {
			if (com.nis.util.Constants.NO.equals(user.getStatus())){
				throw new AuthenticationException("msg:该已帐号禁止登录.");
			}
			byte[] salt = Encodes.decodeHex(user.getPassword().substring(0,16));
			return new SimpleAuthenticationInfo(new Principal(user, token.isMobileLogin()), user.getPassword().substring(16), ByteSource.Util.bytes(salt), getName());
		}
		return null;
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
	 * Authorzation 授权，存放用户权限
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Principal principal = (Principal) getAvailablePrincipal(principals);
		
		// 获取当前已登录的用户
		if (!com.nis.util.Constants.TRUE.equals(Configurations.getStringProperty("user.multiAccountLogin","true"))){
			Collection<Session> sessions = systemService.getActiveSessions(true, principal, UserUtils.getSession());
			if (sessions.size() > 0){
				// 如果是登录进来的，则踢出已在线用户
				if (UserUtils.getSubject().isAuthenticated()){
					for (Session session : sessions){
						systemService.deleteSession(session);
					}
				}
				// 记住我进来的，并且当前用户已登录，则退出当前用户提示信息。
				else{
					UserUtils.getSubject().logout();
					throw new AuthenticationException("msg:账号已在其它地方登录，请重新登录。");
				}
			}
		}
		SysUser user = systemService.getUserByLoginName(principal.getLoginName());
		if (user != null) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			List<SysMenu> list = UserUtils.getMenuList();
			if(!StringUtil.isEmpty(list)) {
				for (SysMenu menu :list) {
					if (!StringUtil.isBlank(menu.getPermission())) {
						// 添加基于Permission的权限信息
						for (String permission : StringUtils.split(menu.getPermission(),",")){
							info.addStringPermission(permission);
						}
					}
				}
			}
		
			// 添加用户权限
			info.addStringPermission("user");
			// 添加用户角色信息
			for (SysRole role : user.getUserRoleList()){
				info.addRole(role.getName());
			}
			// 更新登录IP和时间,集成用户日志记录
			//systemService.updateUserLoginInfo(user);
			// 记录登录日志
			LogUtils.saveLog(Servlets.getRequest(), "系统登录");
			return info;
		}
		return null;
		
	}
	
	@Override
	protected void checkPermission(Permission permission, AuthorizationInfo info) {
		authorizationValidate(permission);
		super.checkPermission(permission, info);
	}
	
	@Override
	protected boolean[] isPermitted(List<Permission> permissions, AuthorizationInfo info) {
		if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
        		authorizationValidate(permission);
            }
        }
		return super.isPermitted(permissions, info);
	}
	
	@Override
	public boolean isPermitted(PrincipalCollection principals, Permission permission) {
		authorizationValidate(permission);
		return super.isPermitted(principals, permission);
	}
	
	@Override
	protected boolean isPermittedAll(Collection<Permission> permissions, AuthorizationInfo info) {
		if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
            	authorizationValidate(permission);
            }
        }
		return super.isPermittedAll(permissions, info);
	}
	
	/**
	 * 授权验证方法
	 * @param permission
	 */
	private void authorizationValidate(Permission permission){
		// 模块授权预留接口
	}
	
	/**
	 * 设定密码校验的Hash算法与迭代次数
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(com.nis.util.Constants.HASH_ALGORITHM);
		matcher.setHashIterations(com.nis.util.Constants.HASH_INTERATIONS);
		setCredentialsMatcher(matcher);
	}
	
//	/**
//	 * 清空用户关联权限认证，待下次使用时重新加载
//	 */
//	public void clearCachedAuthorizationInfo(Principal principal) {
//		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
//		clearCachedAuthorizationInfo(principals);
//	}


	/**
	 * 授权用户信息
	 */
	public static class Principal implements Serializable {

		private static final long serialVersionUID = 1L;
		
		private Long id; // 编号
		private String loginName; // 登录名
		private String name; // 姓名
		private boolean mobileLogin; // 是否手机登录
		private String email;
		
//		private Map<String, Object> cacheMap;

		public Principal(SysUser user, boolean mobileLogin) {
			this.id = user.getId();
			this.loginName = user.getLoginId();
			this.name = user.getName();
			this.email = user.getEmail();
			this.mobileLogin = mobileLogin;
			
		}

		

		public String getEmail() {
			return email;
		}



		public void setEmail(String email) {
			this.email = email;
		}



		public Long getId() {
			return id;
		}



		public void setId(Long id) {
			this.id = id;
		}



		public String getLoginName() {
			return loginName;
		}

		public String getName() {
			return name;
		}

		public boolean isMobileLogin() {
			return mobileLogin;
		}

//		@JsonIgnore
//		public Map<String, Object> getCacheMap() {
//			if (cacheMap==null){
//				cacheMap = new HashMap<String, Object>();
//			}
//			return cacheMap;
//		}

		/**
		 * 获取SESSIONID
		 */
		public String getSessionid() {
			try{
				return (String) UserUtils.getSession().getId();
			}catch (Exception e) {
				return "";
			}
		}
		
		@Override
		public String toString() {
			return String.valueOf(id);
		}
		
		

	}
}
