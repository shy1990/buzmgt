package com.wangge.buzmgt;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wangge.buzmgt.sys.entity.Role;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.sys.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BuzmgtApplication.class)
@WebAppConfiguration
public class BuzmgtApplicationTests {
	@Resource
	private UserService userService;

	@Test
	public void contextLoads() {
	}

}
