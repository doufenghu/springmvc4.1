package com.nis.web.security;

import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.google.code.kaptcha.Constants;
import com.google.common.collect.Maps;
import com.nis.domain.SysArea;
import com.nis.domain.SysFunctionButton;
import com.nis.domain.SysFunctionMenu;
import com.nis.domain.SysMenu;
import com.nis.domain.SysOffice;
import com.nis.domain.SysRole;
import com.nis.domain.SysUser;
import com.nis.util.CacheUtils;
import com.nis.util.StringUtil;
import com.nis.util.TreeUtil;
import com.nis.web.dao.SysAreaDao;
import com.nis.web.dao.SysFunctionMenuDao;
import com.nis.web.dao.SysMenuDao;
import com.nis.web.dao.SysOfficeDao;
import com.nis.web.dao.SysRoleDao;
import com.nis.web.dao.UserDao;
import com.nis.web.security.SystemAuthorizingRealm.Principal;
import com.nis.web.service.BaseService;
import com.nis.web.service.SpringContextHolder;


/**
 * 用户工具类
 * @author darnell
 * @version 
 */
public class UserUtils {

	private static UserDao userDao = SpringContextHolder.getBean(UserDao.class);
	private static SysFunctionMenuDao menuDao = SpringContextHolder.getBean(SysFunctionMenuDao.class);
	private static SysOfficeDao officeDao = SpringContextHolder.getBean(SysOfficeDao.class);
	private static SysRoleDao roleDao = SpringContextHolder.getBean(SysRoleDao.class);
	private static SysMenuDao sysMenuDao = SpringContextHolder.getBean(SysMenuDao.class);
	private static SysAreaDao areaDao = SpringContextHolder.getBean(SysAreaDao.class);


	public static final String USER_CACHE = "userCache";
	public static final String USER_CACHE_ID_ = "id_";
	public static final String USER_CACHE_LOGIN_NAME_ = "ln";
	public static final String USER_CACHE_LIST_BY_OFFICE_ID_ = "oid_";

	public static final String CACHE_ROLE_LIST = "roleList";
	public static final String CACHE_MENU_LIST = "menuList";
	public static final String CACHE_AREA_LIST = "areaList";
	public static final String CACHE_OFFICE_LIST = "officeList";
	public static final String CACHE_OFFICE_ALL_LIST = "officeAllList";
	/**
	 * 根据ID获取用户
	 * @param id
	 * @return 取不到返回null
	 */
	public static SysUser get(String id){
		SysUser user = (SysUser)CacheUtils.get(USER_CACHE, USER_CACHE_ID_ + id);
		if (user ==  null){
			user = userDao.getUserWithRelation(new SysUser(Long.valueOf(id),null));
			if (user == null){
				return null;
			}
			CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
			CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginId(), user);
		}
		return user;
	}
	
	/**
	 * 根据登录名获取用户
	 * @param loginName
	 * @return 取不到返回null
	 */
	public static SysUser getByLoginName(String loginName){
		SysUser user = (SysUser)CacheUtils.get(USER_CACHE, USER_CACHE_LOGIN_NAME_ + loginName);
		if (user == null){
			user = userDao.getUserWithRelation(new SysUser(null,loginName));
			if (user == null){
				return null;
			}
			CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
			CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginId(), user);
		}
		return user;
	}
	
	/**
	 * 清除当前用户缓存
	 */
	public static void clearCache(){
		removeCache(CACHE_ROLE_LIST);
		removeCache(CACHE_MENU_LIST);
		removeCache(CACHE_AREA_LIST);
		removeCache(CACHE_OFFICE_LIST);
		removeCache(CACHE_OFFICE_ALL_LIST);
		UserUtils.clearCache(getUser());
	}
	
	/**
	 * 清除指定用户缓存
	 * @param user
	 */
	public static void clearCache(SysUser user){
		CacheUtils.remove(USER_CACHE, USER_CACHE_ID_ + user.getId());
		CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginId());
		CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getOldLoginId());
		if (user.getOffice() != null && user.getOffice().getId() != null){
			CacheUtils.remove(USER_CACHE, USER_CACHE_LIST_BY_OFFICE_ID_ + user.getOffice().getId());
		}

	}
	
	/**
	 * 获取当前用户
	 * @return 取不到返回 new User()
	 */
	public static SysUser getUser(){
		Principal principal = getPrincipal();
		if (principal!=null){
			SysUser user = get(String.valueOf(principal.getId()));
			if (user != null){
				return user;
			}
			return new SysUser();
		}
		// 如果没有登录，则返回实例化空的User对象。
		return new SysUser();
	}
	
	
	/**
	 * 验证码是否合法
	 * @param validateCode
	 * @return
	 */
	public static boolean validateCodeIsValid(String validateCode) {
		String code = (String) getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		return (StringUtil.isBlank(validateCode) || !validateCode.toUpperCase().equals(code))? false : true;
	}

	/**
	 * 获取当前用户角色列表
	 * @return
	 */
	public static List<SysRole> getRoleList(){
		@SuppressWarnings("unchecked")
		List<SysRole> roleList = (List<SysRole>)getCache(CACHE_ROLE_LIST);
		if (roleList == null){
			roleList = roleDao.findAllList(new SysRole());
			putCache(CACHE_ROLE_LIST, roleList);
		}
		return roleList;
	}
	
	
	
	/**
	 * 获取当前用户授权菜单
	 * @return
	 */
	@Deprecated
	public static List<SysFunctionMenu> getOld2MenuList(){
		@SuppressWarnings("unchecked")
		List<SysFunctionMenu> menuList = (List<SysFunctionMenu>)getCache(CACHE_MENU_LIST);
		
		if (menuList == null){
			SysUser user = getUser();
			menuList = bulidMenuWithButton(menuDao.getSysFunctionMenuByUserId(user.getId()));
			
			putCache(CACHE_MENU_LIST, menuList);
		}
		
		return menuList;
	}
	
	
	/**
	 * 获取当前用户授权菜单
	 * @return
	 */
	public static List<SysMenu> getMenuList(){
		@SuppressWarnings("unchecked")
		List<SysMenu> menuList = (List<SysMenu>)getCache(CACHE_MENU_LIST);
		if (menuList == null){
			SysUser user = getUser();
			if (user.isAdmin()){
				menuList = sysMenuDao.findAllList(new SysMenu());
			}else{
				menuList = sysMenuDao.findSysMenuByUserId(user.getId());
			}
			putCache(CACHE_MENU_LIST, menuList);
		}
		return menuList;
		
		
		
	}
	
	
	
	/**
	 * 按照菜单各级递归排列
	 * @return
	 */
	public static List<SysMenu> getMenuTreeList(){
		return  new TreeUtil(getMenuList()).buildTree();
	}
	
	
	/**
	 * 构建菜单关联所对应的权限按钮
	 * @return
	 */
	private static List<SysFunctionMenu> bulidMenuWithButton(List<SysFunctionMenu> menuList){
		for(SysFunctionButton button : menuDao.getSysFunctionButtonByUserId(getUser().getId())){
			for(SysFunctionMenu menu : menuList) {
				if(button.getMenuId().equals(menu.getId())) {
					menu.setButton(button);
				}
			}
		}
		
		
		
		return menuList;
	}
	
	
	
	
	
	/**
	 * 获取当前用户有权限访问的部门
	 * @return
	 */
	public static List<SysOffice> getOfficeList(){
		@SuppressWarnings("unchecked")
		List<SysOffice> officeList = (List<SysOffice>)getCache(CACHE_OFFICE_LIST);
		if (officeList == null){
			SysUser user = getUser();
			if (user.isAdmin()){
				officeList = officeDao.findAllList(new SysOffice());
			}else{
				SysOffice office = new SysOffice();
				office.getSqlMap().put("dsf", BaseService.dataScopeFilter(user, "a", ""));
				officeList = officeDao.findList(office);
			}
			putCache(CACHE_OFFICE_LIST, officeList);
		}
		return officeList;
	}

	
	
	/**
	 * 获取当前用户授权的区域
	 * @return
	 */
	public static List<SysArea> getAreaList(){
		@SuppressWarnings("unchecked")
		List<SysArea> areaList = (List<SysArea>)getCache(CACHE_AREA_LIST);
		if (areaList == null){
			areaList = areaDao.findAllList(new SysArea());
			putCache(CACHE_AREA_LIST, areaList);
		}
		return areaList;
	}
	
	/**
	 * 获取当前用户有权限访问的部门
	 * @return
	 */
	public static List<SysOffice> getOfficeAllList(){
		@SuppressWarnings("unchecked")
		List<SysOffice> officeList = (List<SysOffice>)getCache(CACHE_OFFICE_ALL_LIST);
		if (officeList == null){
			officeList = officeDao.findAllList(new SysOffice());
		}
		return officeList;
	}
	
	/**
	 * 获取授权主要对象
	 */
	public static Subject getSubject(){
		return SecurityUtils.getSubject();
	}
	
	/**
	 * 获取当前登录者对象
	 */
	public static Principal getPrincipal(){
		try{
			Subject subject = SecurityUtils.getSubject();
			Principal principal = (Principal)subject.getPrincipal();
			if (principal != null){
				return principal;
			}
//			subject.logout();
		}catch (UnavailableSecurityManagerException e) {
			
		}catch (InvalidSessionException e){
			
		}
		return null;
	}
	
	public static Session getSession(){
		try{
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession(false);
			if (session == null){
				session = subject.getSession();
			}
			if (session != null){
				return session;
			}
//			subject.logout();
		}catch (InvalidSessionException e){
			
		}
		return null;
	}
	
	// ============== User Cache ==============
	
	public static Object getCache(String key) {
		return getCache(key, null);
	}
	
	public static Object getCache(String key, Object defaultValue) {
//		Object obj = getCacheMap().get(key);
		Object obj = getSession().getAttribute(key);
		return obj==null?defaultValue:obj;
	}

	public static void putCache(String key, Object value) {
//		getCacheMap().put(key, value);
		getSession().setAttribute(key, value);
	}

	public static void removeCache(String key) {
//		getCacheMap().remove(key);
		getSession().removeAttribute(key);
	}
	
	
	
	/**
	 * 是否是验证码登录
	 * @param useruame 用户名
	 * @param isFail 计数加1
	 * @param clean 计数清零
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isValidateCodeLogin(String useruame, boolean isFail, boolean clean){
		Map<String, Integer> loginFailMap = (Map<String, Integer>)CacheUtils.get("loginFailMap");
		if (loginFailMap==null){
			loginFailMap = Maps.newHashMap();
			CacheUtils.put("loginFailMap", loginFailMap);
		}
		Integer loginFailNum = loginFailMap.get(useruame);
		if (loginFailNum==null){
			loginFailNum = 0;
		}
		if (isFail){
			loginFailNum++;
			loginFailMap.put(useruame, loginFailNum);
		}
		if (clean){
			loginFailMap.remove(useruame);
		}
		return loginFailNum >= 3;
	}
}
