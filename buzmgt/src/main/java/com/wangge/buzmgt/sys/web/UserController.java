package com.wangge.buzmgt.sys.web;

import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.sys.service.UserService;
/**
 * 
* @ClassName: userController
* @Description: TODO(这里用一句话描述这个类的作用)
* @author SongBaoZhen
* @date 2016年1月5日 下午9:17:03
*
 */
@Controller
@RequestMapping("/user")
public class UserController {
  @Resource
  private UserService userService;

  @RequestMapping(value = "/checkUsername", method = RequestMethod.POST)
  @ResponseBody
  public boolean checkUsername(String username, HttpServletRequest request){
    
       Optional<User> user = (Optional<User>) userService.getByUsername(username);
       if(user != null){
         return true;
       }
          return  false;
           
  }

}
