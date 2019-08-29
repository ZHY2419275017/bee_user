package com.bee.user.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.bee.user.pojo.MallUser;

public interface IUserService {
	
	//检查用户名是否已存在 
	String checkUsername(String username);
	
	//用户注册
	String register(MallUser user);
	
	//发送验证码
	String sendCode(String phone,HttpSession session);
	
	//登录
	String login(String username,String password,Integer role,HttpSession session);
	
	//校验手机验证码
	String checkPhoneCode(String code,HttpSession session);
	
	//根据手机号查找用户密保问题
	String selectQuestionByPhone(String phone,HttpSession session);
	//检查密保问题是否正确
	String checkAnswer(String answer,HttpSession session);
	//修改密码（登录和非登录都可用）
	String updatePassword(String password,String newPassword,HttpSession session);	
	//登录状态下用户更新个人信息
	String updateInfo(MallUser mallUser,HttpSession session);
	//获取用户的登录信息
	MallUser getUserInfo(Integer id);
		
	List<MallUser> selectAll();
	
	String deleteUser(Integer userId);
	//管理员修改用户信息的方法	
	String updateUser(MallUser mallUser);
	
	

}
