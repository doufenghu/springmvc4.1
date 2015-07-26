package com.nis.web.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.nis.domain.SysFunctionMenu;

@Repository
public interface SysFunctionMenuDao {
	
	List<SysFunctionMenu> getSysFunctionMenuByUserId(Long userId);

}

