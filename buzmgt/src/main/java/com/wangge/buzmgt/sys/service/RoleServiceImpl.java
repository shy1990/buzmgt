package com.wangge.buzmgt.sys.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.wangge.buzmgt.sys.entity.Role;
import com.wangge.buzmgt.sys.repository.RoleRepository;
import com.wangge.buzmgt.sys.vo.RoleVo;
/**
 * 
* @ClassName: RoleServiceImpl
* @Description: TODO(这里用一句话描述这个类的作用)
* @author SongBaoZhen
* @date 2015年12月31日 上午9:34:33
*
 */
@Service
public class RoleServiceImpl implements RoleService {
	@Resource
	private RoleRepository roleRepository;

	@Transactional
	public List<RoleVo> findAll() {
		List<RoleVo> voList = new ArrayList<RoleVo>();
		List<Role> list = roleRepository.findAll();
		for(Role r : list){
			RoleVo vo = new RoleVo();
			vo.setId(r.getId());
			vo.setName(r.getName());
			voList.add(vo);
		}
		
		return voList;

	}

	@Override
	public Role getRoleById(String roleId) {
		
		
		return roleRepository.findOne(Long.parseLong(roleId));
	}
	
}
