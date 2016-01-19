package com.wangge.buzmgt.sys.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.sys.entity.Resource;
import com.wangge.buzmgt.sys.entity.Role;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.sys.repository.ResourceRepository;
import com.wangge.buzmgt.sys.repository.RoleRepository;
import com.wangge.buzmgt.sys.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private ResourceRepository resourceRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
			ResourceRepository resourceRepository) {
		super();
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.resourceRepository = resourceRepository;
	}

	@Override
	public Optional<User> getByUsername(String username) {
		return Optional.of(userRepository.findByUsername(username));
	}
	

  /*@Override
  public Boolean getByUsername(String username) {
    User u = userRepository.findByUsername(username);
    if(u != null){
      return true;
    }
    return false;
  }
*/
	@Override
	public Collection<String> getRolesByUsername(String username) {
		List<Role> roles = roleRepository.findByUsersUsername(username);
		return roles.stream().map(Role::getName).collect(Collectors.toSet());
	}

	@Override
	public Collection<String> getPermissionsByUsername(String username) {
		Collection<Resource> permissions = resourceRepository.findByRolesUsersUsername(username);
		return permissions.stream().map(Resource::getPermission).collect(Collectors.toSet());
	}
	///////////////			权限
	@Override
	public Page<Role> getAllRoles(Pageable pageRequest) {
		return roleRepository.findAll(pageRequest);
	}

	@Override
	public List<User> getUserByRoles(Long id) {
		
		return userRepository.findByRoles(roleRepository.findOne(id));
	}

	@Override
	public void saveRole(Role role) {
		roleRepository.save(role);
	}

	@Override
	public void delRole(Long id) {
		roleRepository.delete(id);
	}

	@Override
	public Role getRoleById(Long id) {
		// TODO Auto-generated method stub
		return roleRepository.findOne(id);
	}

	@Override
	@Transactional
	public User getById(String id) {
		
		return userRepository.findUserById(id);
	}
	
	public List<Role> findAll() {
		return roleRepository.findAll();
	}
   /**
    * 
    */
  @Override
  @Transactional
  public User addUser(User u) {
    return userRepository.save(u);
  }

}
