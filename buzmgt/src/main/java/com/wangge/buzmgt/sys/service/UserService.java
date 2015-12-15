package com.wangge.buzmgt.sys.service;

import java.util.Collection;
import java.util.Optional;

import com.wangge.buzmgt.sys.entity.User;

public interface UserService {
	/**
	 * 根据用户名获取用户
	 * @param username
	 * @return
	 */
	public Optional<User>  getByUsername(String username);
	/**
	 * 获取用户角色
	 * @param username
	 * @return
	 */
	public Collection<String> getRolesByUsername(String username);
	/**
	 * 获取用户权限
	 * @param username
	 * @return
	 */
	public Collection<String> getPermissionsByUsername(String username);
}
