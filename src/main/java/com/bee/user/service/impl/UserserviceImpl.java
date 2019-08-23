package com.bee.user.service.impl;

import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.bee.user.mapper.MallUserMapper;
import com.bee.user.pojo.MallUser;
import com.bee.user.service.IUserService;
import com.bee.user.util.MD5Util;
import com.bee.user.util.SendSmsUtil;

@Service
public class UserserviceImpl implements IUserService {

	@Autowired
	private MallUserMapper userMapper;

	@Override
	public String checkUsername(String username) {
		if (!StringUtils.isEmpty(username)) {
			MallUser user = userMapper.selectUserByUsername(username);
			if (null != user) {
				return "error";
			}
			return "success";
		} else {
			return "error";
		}

	}

	/***
	 * 注册功能
	 */
	@Override
	public String register(MallUser mallUser) {
		System.out.println(mallUser.toString());
		// 校验用户名
		MallUser user = userMapper.selectUserByUsername(mallUser.getUsername());
		if (null != user) {
			return "该用户已存在";
		}
		// md5加密密码
		mallUser.setPassword(MD5Util.MD5(mallUser.getPassword()));
		// 设置角色(普通用户)
		mallUser.setRole(1);
		// 设置日期
		mallUser.setCreateTime(new Date());
		mallUser.setUpdateTime(new Date());
		Integer result = userMapper.insert(mallUser);
		if (result == 0) {
			return "error";
		} else {
			return "success";
		}
	}

	@Override
	public String sendCode(String phone, HttpSession session) {
		if (!StringUtils.isEmpty(phone)) {
			// 判断该手机号是否已经注册过
			MallUser user = userMapper.selectUserByPhone(phone);
			if (null != user) {
				return "该手机号码已被注册!";
			}
			// 发送验证码
			// 随机生成6位验证码
			StringBuilder codebuilder = new StringBuilder();// 定义变长字符串
			Random random = new Random();
			for (int i = 0; i < 6; i++) {
				codebuilder.append(random.nextInt(10));
			}
			String code = codebuilder.toString();
			System.out.println("发送验证码时的sesssion:" + session.getId());
			session.setAttribute("code", code);
			try {
				SendSmsUtil.send(phone, code);
			} catch (Exception e) {
				e.printStackTrace();
				return "error";
			}
			return "success";
		} else {
			return "error";
		}

	}

	@Override
	public String login(String username, String password, Integer role, HttpSession session) {
		// 检查用户名是否正确
		if(StringUtils.isEmpty(username)){
			return "用户名不能为空";
		}
		MallUser usertemp = userMapper.selectUserByUsername(username);
		if (null == usertemp) {
			// 该用户不存在
			return "用户不存在";
		}
		String md5Password = MD5Util.MD5(password);
		MallUser user = userMapper.selectLogin(username, md5Password,role);
		if (null == user) {
			// 说明密码不正确
			return "密码错误";
		}
		System.out.println("登录时的sesssion:" + session.getId());
		session.setAttribute("user", user);
		return "success";
	}

	@Override
	public String checkPhoneCode(String code, HttpSession session) {
		String sessionCode = (String) session.getAttribute("code");
		if (!StringUtils.isEmpty(code)) {
			if (code.equals(sessionCode)) {
				return "success";
			}
		}
		return "error";
	}
    /**
     * 根据手机号查找用户密保问题
     */
	@Override
	public String selectQuestionByPhone(String phone, HttpSession session) {
		MallUser user = userMapper.selectQuestionByPhone(phone);
		if(null!=user){
			// 将用户放到session中方便后续比较密保问题
			session.setAttribute("unLoginUser", user);
			System.out.println("放之前："+session.getId());
			return user.getQuestion();
		}else{
			return "error";
		}
		
	}

	@Override
	public String checkAnswer(String answer, HttpSession session) {
		MallUser user = (MallUser) session.getAttribute("unLoginUser");
		if (null != user) {
			System.out.println("放之后：" + session.getId());
			if (user.getAnswer().equals(answer)) {
				return "success";
			}
		}
		return "error";
	}

	@Override
	public String updatePassword(String password, String newPassword, HttpSession session) {
		// 非登录状态时		
		System.out.println("更新密码时的sessionId:"+session.getId());
		
		MallUser unLoginUser = (MallUser) session.getAttribute("unLoginUser");
		if (null != unLoginUser) {
			String oldpassword  = MD5Util.MD5(password);
			if (unLoginUser.getPassword().equals(oldpassword)) {
				// 修改密码
				String newpassword = MD5Util.MD5(newPassword);
				Integer result = userMapper.updatePassword(newpassword, unLoginUser.getId());
				return (result > 0 ? "success" : "error");
			}
		} else {
			// 登录状态时
			MallUser user = (MallUser) session.getAttribute("user");
			String oldpassword  = MD5Util.MD5(password);
			if (user.getPassword().equals(oldpassword)) {
				// 修改密码
				String newpassword = MD5Util.MD5(newPassword);
				Integer result = userMapper.updatePassword(newpassword, user.getId());
				return (result > 0 ? "success" : "error");
			}
		}
		return "error";

	}

	@Override
	public String updateInfo(MallUser mallUser,HttpSession session) {
		//检查email和数据库中其他邮箱是否重复
		int count = userMapper.checkEmailByUserId(mallUser.getEmail(), mallUser.getId());
		if(count>0){
			//说明有重复的邮箱
			return "error邮箱已存在，换个邮箱";
		}
		//更新个人信息
		int updateResult = userMapper.updateByPrimaryKeySelective(mallUser);
		if(updateResult>0){
			//更新session存的user对象
			MallUser user = userMapper.selectByPrimaryKey(mallUser.getId());
			session.setAttribute("user", user);
			return "success";			
		}	
		//更新失败
		return "error";
	}
	
    //获取用户的登录信息
	@Override
	public MallUser getUserInfo(Integer id) {
		MallUser user = userMapper.selectByPrimaryKey(id);	
		if(user==null){
		    //说明找不到这个用户
			return null;
		}
		//把密码置空
		user.setPassword(null);
		return user;
	}

}
