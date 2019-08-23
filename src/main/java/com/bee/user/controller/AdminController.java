package com.bee.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bee.user.pojo.MallUser;
import com.bee.user.service.IUserService;

@Controller
@RestController
@RequestMapping(value="/admin")
public class AdminController {
	
	private final static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	
	@Autowired
	private IUserService userService;
	
	@RequestMapping("/login")
	public String login(String username,String password){
		logger.info("管理员login被访问");
		//获取session		
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
	    HttpServletRequest request = servletRequestAttributes.getRequest();
	    HttpSession session = request.getSession();	
	    System.out.println("登录时的session:"+session.getId());
	    Integer role = 0;//管理员登录
		return userService.login(username, password, role,session);
	}
	
	/**
	 * 获取登录状态下用户个人信息
	 * @return MallUser  null
	 */
	@GetMapping(value="/getInfo")
	public MallUser getUserInfo(){
		logger.info("管理员getInfo被访问");
		// 获取session
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		HttpServletRequest request = servletRequestAttributes.getRequest();
		HttpSession session = request.getSession();
		System.out.println("getInfo时的session:"+session.getId());
		MallUser currentUser = (MallUser)session.getAttribute("user");
		System.out.println("currentUser:"+currentUser);
		if(null==currentUser){
			return null;
		}
		return userService.getUserInfo(currentUser.getId());
	}
	

}
