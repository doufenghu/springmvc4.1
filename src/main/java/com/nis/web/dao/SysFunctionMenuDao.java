package com.nis.web.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.nis.domain.SysFunctionButton;
import com.nis.domain.SysFunctionMenu;
@Repository
public interface SysFunctionMenuDao {
    
	/***
	 * 获取用户具有的权限菜单
	 * @param userId
	 * @return
	 */
	List<SysFunctionMenu> getSysFunctionMenuByUserId(Long userId);
	/***
	 * 获取用户具有的权限按钮
	 * @param userId
	 * @return
	 */
	List<SysFunctionButton> getSysFunctionButtonByUserId(Long userId);
	
	int deleteByPrimaryKey(Long id);

    int insert(SysFunctionMenu record);

    int insertSelective(SysFunctionMenu record);

    SysFunctionMenu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysFunctionMenu record);

    int updateByPrimaryKey(SysFunctionMenu record);
    
    
}