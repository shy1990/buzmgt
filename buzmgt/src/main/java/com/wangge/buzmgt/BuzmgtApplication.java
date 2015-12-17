package com.wangge.buzmgt;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.wangge.buzmgt.sys.entity.Organization;
import com.wangge.buzmgt.sys.entity.Resource;
import com.wangge.buzmgt.sys.entity.Resource.ResourceType;
import com.wangge.buzmgt.sys.entity.Role;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.sys.repository.ResourceRepository;
import com.wangge.buzmgt.sys.repository.OrganizationRepository;
import com.wangge.buzmgt.sys.repository.RoleRepository;
import com.wangge.buzmgt.sys.repository.UserRepository;

@SpringBootApplication
public class BuzmgtApplication extends SpringBootServletInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BuzmgtApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(BuzmgtApplication.class, args);
	}
	//@Bean
	CommandLineRunner init(UserRepository userRepository, RoleRepository roleRepository,OrganizationRepository organizationRepository,ResourceRepository moduleRepository) {
		return (evt) -> {
			
			// test role
			Arrays.asList("区域总监,大区经理".split(",")).forEach(r -> {
				Role role=new Role(r);
				
				role.setDescription(r);
				roleRepository.save(role);
			});
			//test module
			Resource m = new Resource("业务管理",ResourceType.MENU,null,0);
			m.addRole(roleRepository.findOne(1L));
			moduleRepository.save(m);
			
			//test org
			Organization org1=organizationRepository.save(new Organization("三际网格"));
			Organization org2=new Organization("市场部");
			org2.setParent(org1);
			organizationRepository.save(org2);
			
			
			//test user
			Arrays.asList("root,caozhaoyang,zhangsan".split(",")).forEach(a -> {
				User user = new User(a, "password");
				System.out.println("save user " + user.getId() + " " + user.getUsername());
				if (a.equals("caozhaoyang")) {
					
					user.addRole(roleRepository.findOne(1l));
				}else if(!a.equals("root")){
					user.addRole(roleRepository.findOne(2l));
				}
				user.setOrganization(organizationRepository.findOne(2L));
				userRepository.save(user);
			});
		
	};
		};
}
