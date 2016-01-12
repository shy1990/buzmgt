package com.wangge.buzmgt.sys.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLoginForm() {
		if (SecurityUtils.getSubject().isAuthenticated() || SecurityUtils.getSubject().isRemembered()) {
			// 已经认证的用户先退出一下
			SecurityUtils.getSubject().logout();
		}
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String showLoginFormWithError(HttpServletRequest req, Model model) {

		String exceptionClassName = (String) req.getAttribute("shiroLoginFailure");
		String error = null;
		if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
			error = "账户不存在";
		} else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
			error = "用户名/密码错误";
		} else if (LockedAccountException.class.getName().equals(exceptionClassName)) {
			error = "您的账号被锁定";
		} else if (AuthenticationException.class.getName().equals(exceptionClassName)){
		  error ="认证错误!";
		}else if(exceptionClassName != null) {
		  error ="认证错误!";
			System.out.println("其他错误：" + exceptionClassName);
		}
		model.addAttribute("error", error);
		return "login";
	}

}
