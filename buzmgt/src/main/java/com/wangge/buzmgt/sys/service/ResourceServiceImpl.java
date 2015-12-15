package com.wangge.buzmgt.sys.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.sys.entity.Resource;
import com.wangge.buzmgt.sys.entity.Resource.ResourceType;
import com.wangge.buzmgt.sys.repository.ResourceRepository;

@Service
public class ResourceServiceImpl implements ResourceService {

	private ResourceRepository resourceRepository;

	@Autowired
	public ResourceServiceImpl(ResourceRepository resourceRepository) {
		super();
		this.resourceRepository = resourceRepository;
	}

	@Override
	public List<Menu> getMenusByUsername(String username) {
		List<Resource> resources = resourceRepository.findByRolesUsersUsernameAndType(username, ResourceType.MENU);
		return resource2Menu(resources).stream().collect(Collectors.toList());
	}

	private Set<Menu> resource2Menu(Collection<Resource> resources) {
		Set<Menu> menus=new HashSet<Menu>();
		resources.forEach(r->{
			Menu menu=new Menu(r.getName(), r.getUrl());
			if (!r.getChildren().isEmpty()) {
				menu.setChildren(resource2Menu(r.getChildren()));
			} 
				menus.add(menu);
		});
		return menus;
	}

}
