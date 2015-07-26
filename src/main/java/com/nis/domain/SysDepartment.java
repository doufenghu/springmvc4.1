package com.nis.domain;

import java.io.Serializable;
import java.util.Date;

public class SysDepartment implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String deptNo;
	private String deptName;
	private String deptNameEn;
	private String deptNameShort;
	private Long parentId;
	private int isLeaf;
	private int orderNumber;
	private Date createTime;
	private int status;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDeptNo() {
		return deptNo;
	}
	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getDeptNameEn() {
		return deptNameEn;
	}
	public void setDeptNameEn(String deptNameEn) {
		this.deptNameEn = deptNameEn;
	}
	public String getDeptNameShort() {
		return deptNameShort;
	}
	public void setDeptNameShort(String deptNameShort) {
		this.deptNameShort = deptNameShort;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public int getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(int isLeaf) {
		this.isLeaf = isLeaf;
	}
	public int getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	

}
