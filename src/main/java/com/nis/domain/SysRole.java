package com.nis.domain;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

public class SysRole extends BaseEntity<SysRole>{
	private static final long serialVersionUID = -5388120268433030734L;
	private String name;
	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	private String oldName; 	// 原角色名称
	private String roleType;
	private Integer dataScope;
    private String remark;
    private List<SysMenu> menuList = Lists.newArrayList(); // 拥有菜单列表
    

    public List<SysMenu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<SysMenu> menuList) {
		this.menuList = menuList;
	}

	public Integer getDataScope() {
		return dataScope;
	}

	public void setDataScope(Integer dataScope) {
		this.dataScope = dataScope;
	}

	private Integer status;

    private Date createTime;
    
 // 数据范围（1：所有数据；2：所在公司及以下数据；3：所在公司数据；4：所在部门及以下数据；5：所在部门数据；8：仅本人数据；9：按明细设置）
 	public static final Integer DATA_SCOPE_ALL = 1;
 	public static final Integer DATA_SCOPE_COMPANY_AND_CHILD = 2;
 	public static final Integer DATA_SCOPE_COMPANY = 3;
 	public static final Integer DATA_SCOPE_OFFICE_AND_CHILD = 4;
 	public static final Integer DATA_SCOPE_OFFICE = 5;
 	public static final Integer DATA_SCOPE_SELF = 8;
 	public static final Integer DATA_SCOPE_CUSTOM = 9;



	
	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	
	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

   

    public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public List<Long> getMenuIdList() {
		List<Long> menuIdList = Lists.newArrayList();
		for (SysMenu menu : menuList) {
			menuIdList.add(menu.getId());
		}
		return menuIdList;
	}

	public void setMenuIdList(List<String> menuIdList) {
		menuList = Lists.newArrayList();
		for (String menuId : menuIdList) {
			SysMenu menu = new SysMenu();
			menu.setId(Long.parseLong(menuId));
			menuList.add(menu);
		}
	}

	public String getMenuIds() {
		return StringUtils.join(getMenuIdList(), ",");
	}
	
	public void setMenuIds(String menuIds) {
		menuList = Lists.newArrayList();
		if (menuIds != null){
			String[] ids = StringUtils.split(menuIds, ",");
			setMenuIdList(Lists.newArrayList(ids));
		}
	}
	
}