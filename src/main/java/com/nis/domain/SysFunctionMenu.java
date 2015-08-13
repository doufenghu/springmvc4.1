package com.nis.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SysFunctionMenu implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

    private String menuName;

    private SysFunctionMenu parent;

    private String menuUrl;

    private String menuClass;

    private String menuIcon;

    private Integer isLeaf;

    private Integer menuOrder;

    private Integer menuLevel;

    private String permission;

    private Integer isVisible;

    private Integer quickAction;
    
    private List<SysFunctionMenu> children = new ArrayList<SysFunctionMenu>();
    private List<SysFunctionButton> sysFunctionButtonList = new ArrayList<SysFunctionButton>();
    
    public void addChildren(SysFunctionMenu menu) {
    	children.add(menu);
    }
    
    public List<SysFunctionMenu> getChildren() {
		return children;
	}

	public void setChildren(List<SysFunctionMenu> children) {
		this.children = children;
	}

	

    public List<SysFunctionButton> getSysFunctionButtonList() {
		return sysFunctionButtonList;
	}

	public void setSysFunctionButtonList(
			List<SysFunctionButton> sysFunctionButtonList) {
		this.sysFunctionButtonList = sysFunctionButtonList;
	}
	
	
	public void setButton(SysFunctionButton button) {
		this.sysFunctionButtonList.add(button);
	}

	

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

  
    public SysFunctionMenu getParent() {
		return parent;
	}

	public void setParent(SysFunctionMenu parent) {
		this.parent = parent;
	}

	public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl == null ? null : menuUrl.trim();
    }

    public String getMenuClass() {
        return menuClass;
    }

    public void setMenuClass(String menuClass) {
        this.menuClass = menuClass == null ? null : menuClass.trim();
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon == null ? null : menuIcon.trim();
    }

    public Integer getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Integer isLeaf) {
        this.isLeaf = isLeaf;
    }

    public Integer getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(Integer menuOrder) {
        this.menuOrder = menuOrder;
    }

    public Integer getMenuLevel() {
        return menuLevel;
    }

    public void setMenuLevel(Integer menuLevel) {
        this.menuLevel = menuLevel;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission == null ? null : permission.trim();
    }

    public Integer getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Integer isVisible) {
        this.isVisible = isVisible;
    }

    public Integer getQuickAction() {
        return quickAction;
    }

    public void setQuickAction(Integer quickAction) {
        this.quickAction = quickAction;
    }
}