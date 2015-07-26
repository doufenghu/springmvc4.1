package com.nis.web.dao;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.nis.domain.SysUser;

@Repository
public interface UserDao {
	
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
	 * 保存用户
	 * @param user
	 */
	void insertUser(SysUser user);
	
	
	SysUser getUserByIdWithDepartment(long userId);
	
	
}
