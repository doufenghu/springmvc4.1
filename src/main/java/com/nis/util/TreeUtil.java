package com.nis.util;

import java.util.ArrayList;
import java.util.List;

import com.nis.domain.SysFunctionMenu;

public final class TreeUtil {
	
	private List<SysFunctionMenu> menuList = new ArrayList<SysFunctionMenu>();
	
	public TreeUtil(List<SysFunctionMenu> menuList) {
		this.menuList = menuList;
	}
	
	public List<SysFunctionMenu> buildTree(){
		List<SysFunctionMenu> newMenuList = new ArrayList<SysFunctionMenu>();
		
		for (SysFunctionMenu menu : menuList) {
			if (menu.getParent().getId().equals(1l)) {
				build(menu);
				newMenuList.add(menu);
			}
		}
		
		return newMenuList;
	}
	
	private void build(SysFunctionMenu rootMenu) {
		
		List<SysFunctionMenu> children = getChildren(rootMenu);
		
		if ( !StringUtil.isEmpty(children) ) {
			rootMenu.setChildren(children);
			for (SysFunctionMenu child : children) {
				if (child.getIsLeaf() !=1) {
					build(child);
				}
			}
		}
		
	}
	
	private List<SysFunctionMenu> getChildren(SysFunctionMenu rootMenu){
		
		List<SysFunctionMenu> children = new ArrayList<SysFunctionMenu>();
		
		for(SysFunctionMenu child : menuList) {
			if (rootMenu.getId().equals(child.getParent().getId())) {
				children.add(child);
			}
		}
		
		return children;
		
	}
	
	

}
