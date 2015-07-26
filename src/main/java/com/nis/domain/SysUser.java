package com.nis.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SysUser implements java.io.Serializable {
	private static final long serialVersionUID = 8672536293642073408L;
	private Long id;
	private String loginId;
	private String email;//电子邮箱
	private String password;
	private Date createTime;
	private Integer status;
	private List<SysDepartment> userDeptList = new ArrayList<SysDepartment>();
	/*public static ArrayList  LONGIN_USER=new ArrayList();//存储登录用户
	public static Map LOGIN_SESSION=new HashMap();//存储登录用户的session
	private List<SysRole> userRoleList = new ArrayList();
	*/

	public Integer getStatus() {
		return status;
	}

	public List<SysDepartment> getUserDeptList() {
		return userDeptList;
	}

	public void setUserDeptList(List<SysDepartment> userDeptList) {
		this.userDeptList = userDeptList;
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