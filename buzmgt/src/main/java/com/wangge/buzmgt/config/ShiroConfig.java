package com.wangge.buzmgt.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.wangge.buzmgt.shiro.MyRealm;
import com.wangge.buzmgt.sys.service.UserService;

@Configuration
public class ShiroConfig {

	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(org.apache.shiro.mgt.SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
		shiroFilter.setLoginUrl("/login");
		shiroFilter.setSuccessUrl("/");
		shiroFilter.setUnauthorizedUrl("/forbidden");

		Map<String, Filter> filters = new HashMap<String, Filter>();
		filters.put("anno", new AnonymousFilter());
		filters.put("authc", new FormAuthenticationFilter());
		filters.put("logout", new LogoutFilter());
		filters.put("user", new UserFilter());
		shiroFilter.setFilters(filters);

		Map<String, String> filterChainDefinitionMapping = new LinkedHashMap<String, String>();

		filterChainDefinitionMapping.put("/**/favicon.ico", "anno");
		filterChainDefinitionMapping.put("/login", "authc");
		filterChainDefinitionMapping.put("/logout", "logout");
		filterChainDefinitionMapping.put("/static/**", "anon");
		filterChainDefinitionMapping.put("/mainIncome/calcuPayed", "anon");
		filterChainDefinitionMapping.put("/baseSalary/test", "anon");
		filterChainDefinitionMapping.put("/**", "user");
		shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMapping);
		shiroFilter.setSecurityManager(securityManager);
		return shiroFilter;
	}

	@Bean(name = "securityManager")
	public org.apache.shiro.mgt.SecurityManager securityManager(Realm realm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(realm);
		SecurityUtils.setSecurityManager(securityManager);
		return securityManager;
	}

	@Bean(name = "realm")
	@DependsOn({ "lifecycleBeanPostProcessor" })
	public Realm realm(UserService userService) {
		MyRealm realm = new MyRealm(userService);
		return realm;
	}


	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(org.apache.shiro.mgt.SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

}
