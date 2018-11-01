package com.entity;

import java.util.List;

public class UserDTO {
	private Long[] ids;
	
	private List<User> users;

	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
