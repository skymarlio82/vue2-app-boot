
package com.reforgedsrc.app.vue2demo.boot.rest.model;

import java.util.List;

public class UserProfile {

	private String userId = null;
	private String userName = null;
	private String roleName = null;
	private List<String> menuList = null;

	public UserProfile() {
		
	}

	public UserProfile(String userId, String userName, String roleName, List<String> menuList) {
		this.userId = userId;
		this.userName = userName;
		this.roleName = roleName;
		this.menuList = menuList;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<String> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<String> menuList) {
		this.menuList = menuList;
	}
}