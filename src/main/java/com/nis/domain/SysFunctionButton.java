package com.nis.domain;

import java.io.Serializable;

public class SysFunctionButton implements Serializable {
	/**
	 * Aug 19, 2013
	 * @author darnell
	 *
	 * 
	 */
	private static final long serialVersionUID = -796517763282731747L;
	private Long id;
	private String btnName;
	private String btnClass;
	private String btnIcon;
	private String btnScript;
	private int status;
	private Long menuId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBtnName() {
		return btnName;
	}
	public void setBtnName(String btnName) {
		this.btnName = btnName;
	}
	public String getBtnClass() {
		return btnClass;
	}
	public void setBtnClass(String btnClass) {
		this.btnClass = btnClass;
	}
	public String getBtnIcon() {
		return btnIcon;
	}
	public void setBtnIcon(String btnIcon) {
		this.btnIcon = btnIcon;
	}
	public String getBtnScript() {
		return btnScript;
	}
	public void setBtnScript(String btnScript) {
		this.btnScript = btnScript;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	
	
	

}
