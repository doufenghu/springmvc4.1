package com.nis.web.dao;



import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nis.domain.SysUser;

@MyBatisDao
public interface UserDao extends CrudDao<SysUser> {
	
	/**
	 * 通过主键ID获取相关用户，不包含密码
	 * @param id
	 * @return
	 */
	SysUser getUserById(@Param("id") Long id);
	
	/**
	 * 通过登录账户获取相关用户，不包含密码
	 * @param loginName
	 * @return
	 */
	SysUser getUserByLoginName(@Param("loginName") String loginName);
	
	
	
	/**
	 * 删除用户角色关联数据
	 * @param user id
	 * @return
	 */
	public int deleteUserRole(Long id);
	
	
	/**
	 * 查询用户附带关联：部门、角色
	 * @param user
	 * @return
	 */
	SysUser getUserWithRelation(SysUser user);
	
	
	
	void insertUserRole(SysUser user);

	public int deleteUserOffice(Long id);

	void insertUserOffice(SysUser user);

	List<SysUser> findUserByRoleId(Long id);

	List<SysUser> findUserByOfficeId(Long id);

	void removeUserInRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

	void updateUserInfo(SysUser user);

	void updatePasswordById(SysUser user);
	
	
}
