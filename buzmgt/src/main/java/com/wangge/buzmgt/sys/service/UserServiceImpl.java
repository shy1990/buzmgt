package com.wangge.buzmgt.sys.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

}
