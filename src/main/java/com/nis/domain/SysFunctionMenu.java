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
	private int isLeaf;
	private int menuOrder;
	private int menuLevel;
	private int isVisible;
	private int quickAction;
	private List<SysFunctionMenu> children = new ArrayList<SysFunctionMenu>();
	private List<SysFunctionButton> buttonList = new ArrayList<SysFunctionButton>();
	
	public int getQuickAction() {
		return quickAction;
	}
	public void setQuickAction(int quickAction) {
		this.quickAction = quickAction;
	}
	/**
	 * @return the buttonList
	 */
	public List<SysFunctionButton> getButtonList() {
		return buttonList;
	}
	/**
	 * @param buttonList the buttonList to set
	 */
	public void setButtonList(List<SysFunctionButton> buttonList) {
		this.buttonList = buttonList;
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
		this.menuName = menuName;
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
		this.menuUrl = menuUrl;
	}
	public String getMenuClass() {
		return menuClass;
	}
	public void setMenuClass(String menuClass) {
		this.menuClass = menuClass;
	}
	public String getMenuIcon() {
		return menuIcon;
	}
	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}
	public int getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(int isLeaf) {
		this.isLeaf = isLeaf;
	}
	public int getMenuOrder() {
		return menuOrder;
	}
	public void setMenuOrder(int menuOrder) {
		this.menuOrder = menuOrder;
	}
	public int getMenuLevel() {
		return menuLevel;
	}
	public void setMenuLevel(int menuLevel) {
		this.menuLevel = menuLevel;
	}
	public int getIsVisible() {
		return isVisible;
	}
	public void setIsVisible(int isVisible) {
		this.isVisible = isVisible;
	}
	
	
	public List<SysFunctionMenu> getChildren() {
		return children;
	}
	public void setChildren(List<SysFunctionMenu> children) {
		this.children = children;
	}
	public void addChildren(SysFunctionMenu childrenFunctionMenu){
		children.add(childrenFunctionMenu);
	}
	
	
	public void addButton(SysFunctionButton button){
		buttonList.add(button);
	}
	

	
	 
	

}
