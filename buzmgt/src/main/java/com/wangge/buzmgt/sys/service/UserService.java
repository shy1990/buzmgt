package com.wangge.buzmgt.sys.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wangge.buzmgt.sys.entity.Role;
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
	/**
	 * 
	 * @Description: 获取权限列表(分页)
	 * @param @return   
	 * @return List<Role>  
	 * @throws
	 * @author changjun
	 * @date 2015年12月19日
	 */
	public Page<Role> getAllRoles(Pageable pageRequest);
	/**
	 * 
	 * @Description: 总查询
	 * @param @return   
	 * @return List<Role>  
	 * @throws
	 * @author changjun
	 * @date 2016年1月2日
	 */
	List<Role> findAll();
	/**
	 * 
	 * @Description: 获取该权限下的所有用户
	 * @param @param id
	 * @param @return   
	 * @return List<User>  
	 * @throws
	 * @author changjun
	 * @date 2015年12月22日
	 */
	public List<User> getUserByRoles(Long id);
	/**
	 * 
	 * @Description: 添加
	 * @param @param role
	 * @param @return   
	 * @return boolean  
	 * @throws
	 * @author changjun
	 * @date 2015年12月22日
	 */
	public void  saveRole(Role role);
	/**
	 * 
	 * @Description: 删除
	 * @param @param id
	 * @param @return   
	 * @return boolean  
	 * @throws
	 * @author changjun
	 * @date 2015年12月22日
	 */
	public void delRole(Long id);
	/**
	 * 
	 * @Description: 通过id查询
	 * @param @param id
	 * @param @return   
	 * @return Role  
	 * @throws
	 * @author changjun
	 * @date 2015年12月24日
	 */
	public Role getRoleById(Long id);
	/**
	 * 
	* @Title:
	* @Description: TODO( 通过id查询用户) 
	* @param @param id
	* @param @return    设定文件 
	* @return User    返回类型 
	* @throws
	* @author SongBaozhen
	* @date 
	 */
	public User getById(String string);
	
}
