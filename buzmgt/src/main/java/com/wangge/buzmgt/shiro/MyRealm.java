package com.wangge.buzmgt.shiro;

import java.util.Collection;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.sys.entity.User.UserStatus;
import com.wangge.buzmgt.sys.service.UserService;

public class MyRealm extends AuthorizingRealm {
	private static final Logger LOG = LoggerFactory.getLogger(MyRealm.class);
	private UserService userService;

	public MyRealm(UserService userService) {
		this.userService = userService;
	}

	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof UsernamePasswordToken;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();
		User user = userService.getByUsername(username)
				.orElseThrow(() -> new UnknownAccountException("account token [" + username + "] is not found."));

		if (user.getStatus().equals(UserStatus.LOCKED)) {
			LOG.warn("被锁定的用户[{}]尝试登录。", user.getUsername());
			throw new LockedAccountException("用户被锁定。");
		}

		return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		User user = (User) principals.fromRealm(getName()).iterator().next();
		LOG.info("USER===="+user.getRoles());
		if (user.getUsername().equals("root")) {
			// 超级管理员
			authorizationInfo.addRole("admin");
			authorizationInfo.addStringPermission("*");
		} else {
			Collection<String> roles = userService.getRolesByUsername(user.getUsername());
			LOG.debug("{}'s roles are {}",user.getUsername(),roles);
			authorizationInfo.addRoles(roles);
			Collection<String> permissions = userService.getPermissionsByUsername(user.getUsername());
			LOG.debug("{}'s permissions are {}",user.getUsername(),permissions);
			authorizationInfo.addStringPermissions(permissions);
		}

		return authorizationInfo;
	}

}