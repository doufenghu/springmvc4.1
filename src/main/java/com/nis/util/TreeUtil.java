package com.nis.util;

import java.util.ArrayList;
import java.util.List;

import com.nis.domain.SysFunctionMenu;
import com.nis.domain.SysMenu;

/**
 * 将树构建成上下层结构
 * @author Administrator
 *
 */
public final class TreeUtil {
	
	private List<SysMenu> menuList = new ArrayList<SysMenu>();
	
	public TreeUtil(List<SysMenu> menuList) {
		this.menuList = menuList;
	}
	
	public List<SysMenu> buildTree(){
		List<SysMenu> newMenuList = new ArrayList<SysMenu>();
		
		for (SysMenu menu : menuList) {
			if (menu.getParent().getId().equals(1l)) {
				build(menu);
				newMenuList.add(menu);
			}
		}
		
		return newMenuList;
	}
	
	private void build(SysMenu rootMenu) {
		
		List<SysMenu> children = getChildren(rootMenu);
		
		if ( !StringUtil.isEmpty(children) ) {
			rootMenu.setChildren(children);
			for (SysMenu child : children) {
				if (StringUtils.isBlank(child.getHref())) { //根据url是否为空判断结束
					build(child);
				}
			}
		}
		
	}
	
	private List<SysMenu> getChildren(SysMenu rootMenu){
		
		List<SysMenu> children = new ArrayList<SysMenu>();
		
		for(SysMenu child : menuList) {
			if (rootMenu.getId().equals(child.getParent().getId())) {
				children.add(child);
			}
		}
		
		return children;
		
	}
	
	

}
