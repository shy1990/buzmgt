package com.wangge.buzmgt.sys.service;

import java.util.List;
import java.util.Set;

import com.wangge.buzmgt.sys.entity.Role;
import com.wangge.buzmgt.sys.vo.RoleVo;
/**
 * 
* @ClassName: RoleService
* @Description: TODO(这里用一句话描述这个类的作用)
* @author SongBaoZhen
* @date 2015年12月31日 上午9:35:01
*
 */
public interface RoleService {
	
	public List<RoleVo> findAll();

	public Role getRoleById(String roleId);

}
