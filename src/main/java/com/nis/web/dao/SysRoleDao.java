package com.nis.web.dao;

import com.nis.domain.SysRole;
@MyBatisDao
public interface SysRoleDao extends CrudDao<SysRole>{

	SysRole getByName(SysRole r);

	void insertRoleMenu(SysRole role);

	void deleteRoleMenu(SysRole role);
   
}