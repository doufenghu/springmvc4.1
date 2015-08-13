package com.nis.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SysUser implements java.io.Serializable {
	private static final long serialVersionUID = 8672536293642073408L;
	private Long id;
	private String loginId;
	private String name;
	private String email;//电子邮箱
	private String password;
	private String oldLoginId;// 原登录名
	private Date createTime;
	private Integer status;
	private List<SysDepartment> userDeptList = new ArrayList<SysDepartment>();
	private List<SysRole> userRoleList = new ArrayList<SysRole>();
	/*public static ArrayList  LONGIN_USER=new ArrayList();//存储登录用户
	public static Map LOGIN_SESSION=new HashMap();//存储登录用户的session
	*/
	
	
	
	

	public SysUser() {
		super();
	}

	public SysUser(Long id, String loginId) {
		super();
		this.id = id;
		this.loginId = loginId;
	}
	
	

	
	public SysUser(Long id, String loginId, String name, String email,
			String password, String oldLoginId, Date createTime, Integer status) {
		super();
		this.id = id;
		this.loginId = loginId;
		this.name = name;
		this.email = email;
		this.password = password;
		this.oldLoginId = oldLoginId;
		this.createTime = createTime;
		this.status = status;
	}

	public String getOldLoginId() {
		return oldLoginId;
	}
	public void setOldLoginId(String oldLoginId) {
		this.oldLoginId = oldLoginId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Integer getStatus() {
		return status;
	}

	public List<SysDepartment> getUserDeptList() {
		return userDeptList;
	}

	public void setUserDeptList(List<SysDepartment> userDeptList) {
		this.userDeptList = userDeptList;
	}
	
	public List<SysRole> getUserRoleList() {
		return userRoleList;
	}

	public void setUserRoleList(List<SysRole> userRoleList) {
		this.userRoleList = userRoleList;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}

	
	

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}



	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getEmail() {
		return email;
	}

	
	public void setEmail(String email) {
		this.email = email;
	}

	

}