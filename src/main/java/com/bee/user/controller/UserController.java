package com.bee.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bee.user.mapper.MallUserMapper;
import com.bee.user.pojo.MallUser;
import com.bee.user.service.IUserService;
import com.bee.user.util.RandomValidateCodeUtil;

@Controller
@RequestMapping(value="/user")
@RestController
public class UserController {

	@Autowired
	private IUserService userService;
	@Autowired
	private MallUserMapper userMapper;
	
	private final static Logger logger = LoggerFactory.getLogger(UserController.class);

	
	/**
	 * 检查用户名是否存在
	 * @param username
	 * @return 用户名已存在，用户名可用，用户名不能为空
	 */
	@GetMapping(value="/checkUsername")
	public String checkUsername(String username){
		logger.info("checkUsername被访问");
		return userService.checkUsername(username);		
	}
	
	/**
	 * 发送手机验证码
	 * @param phone
	 * @return 成功返回值："success",失败返回值："error","该手机号码已被注册"
	 */
	@GetMapping(value="/sendCode")
	public String sendcode(String phone){
		logger.info("sendCode被访问");
		//获取session		
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
	    HttpServletRequest request = servletRequestAttributes.getRequest();
	    HttpSession session = request.getSession();	 
		return userService.sendCode(phone,session);	
	}
	/**
	 * 注册
	 * @param user
	 * @return 成功返回"success" 失败返回:"该用户名已存在","注册失败"
	 */
	@PostMapping(value="/register")
	public String register(MallUser user){	
		 logger.info("register被访问");
		 return  userService.register(user);					
	}
	/**
	 * 登录功能
	 * @param username
	 * @param password
	 * @return 成功返回值:success  失败返回值："用户不存在","密码错误" 
	 */
	@GetMapping(value="/login")
	public String login(String username ,String password){
		logger.info("login被访问");
		//获取session		
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
	    HttpServletRequest request = servletRequestAttributes.getRequest();
	    HttpSession session = request.getSession();	
	    System.out.println("登录时的session:"+session.getId());
	    Integer role = 1;//普通用户登录
		return userService.login(username, password, role,session);
	}
	/**
	 * 退出登录功能
	 * @return
	 */
	@GetMapping(value="/logout")
	public String logout(){
		logger.info("logout被访问");
		//获取session		
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
	    HttpServletRequest request = servletRequestAttributes.getRequest();
	    HttpSession session = request.getSession();	
	    session.removeAttribute("user");
		return "success";
	} 
	
	/**
	 * 校验短信验证码
	 * @param code
	 * @return 成功返回"success",失败返回"error"
	 */
	@GetMapping(value="/checkPhoneCode")
	public String checkPhoneCode(String code){
		logger.info("checkPhoneCode被访问");
		//获取session		
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
	    HttpServletRequest request = servletRequestAttributes.getRequest();
	    HttpSession session = request.getSession();		
	    return userService.checkPhoneCode(code, session);	    		
	}
	/**
	 * 根据手机号查找该用户的密保问题
	 * @param phone
	 * @return 成功返回密保问题,失败返回"error"
	 */
	@GetMapping(value="/selectQuestionByPhone")
	public String selectQuestionByPhone(String phone) {
		logger.info("selectQuestionByPhone被访问");
		// 获取session
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		HttpServletRequest request = servletRequestAttributes.getRequest();
		HttpSession session = request.getSession();
		return userService.selectQuestionByPhone(phone, session);
	}
	/**
	 * 检查密保问题答案是否正确
	 * @param answer
	 * @return 成功返回"success",失败返回 "error"
	 */
	@GetMapping(value="/checkAnswer")
	public String checkAnswer(String answer){
		logger.info("checkAnswer被访问");
		// 获取session
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		HttpServletRequest request = servletRequestAttributes.getRequest();
		HttpSession session = request.getSession();
		return userService.checkAnswer(answer, session);
	}
	/**
	 * 更新用户密码
	 * @param password
	 * @param newPassword
	 * @return 成功返回"success" 失败返回 "error"
	 */
	
	@GetMapping(value="/updatePassword")
	public String updatePassword(String password,String newPassword){
		logger.info("updatePassword被访问");
		// 获取session
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		HttpServletRequest request = servletRequestAttributes.getRequest();
		HttpSession session = request.getSession();
		return userService.updatePassword(password, newPassword, session);
	}
	
	/**
	 * 登录状态下更新用户信息
	 * @param mallUser
	 * @return 成功返回 success 失败返回 "error"
	 */
	@PutMapping(value="/updateInfo")
	public String updateUserInfo(MallUser mallUser){
		logger.info("updateInfo被访问");
		// 获取session
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		HttpServletRequest request = servletRequestAttributes.getRequest();
		HttpSession session = request.getSession();
		//获取当前登录的用户
		MallUser currentUser = (MallUser)session.getAttribute("user");
		if(null==currentUser){
			return "error用户未登录";
		}
		mallUser.setId(currentUser.getId());
		mallUser.setUsername(currentUser.getUsername());
		//更新信息
		return userService.updateInfo(mallUser,session);		
	}
	
	/**
	 * 获取登录状态下用户个人信息
	 * @return MallUser  null
	 */
	@GetMapping(value="/getInfo")
	public MallUser getUserInfo(){
		logger.info("getInfo被访问");
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
	
	
	/**
     * 生成验证码
     */
    @GetMapping(value = "/getVerify")
    public void getVerify(HttpServletRequest request, HttpServletResponse response) {
    	logger.info("getVerify被访问");
    	try {
            response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
            response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
            randomValidateCode.getRandcode(request, response);//输出验证码图片方法
        } catch (Exception e) {
        	 logger.error("获取验证码失败>>>>   "+e);
        }
    }
	
    /**
     * 校验验证码
     */
    @GetMapping(value = "/checkVerify" ,headers = "Accept=application/json")
    public String checkVerify(@RequestParam String verifyInput, HttpSession session) {
    	logger.info("checkVerify被访问");
    	try{
            //从session中获取随机数
            String inputStr = verifyInput;
            String random = (String) session.getAttribute("RANDOMVALIDATECODEKEY");
            if (random == null) {
                return "success";
            }
            if (random.equals(inputStr)) {
            	logger.info("校验成功!");
                return "success";
            } else {
                return "error";
            }
        }catch (Exception e){
        	 logger.error("验证码校验失败", e);
            return "error";
        }
    }
    
    @GetMapping(value="/selectUserById")
    public MallUser selectUserById(Integer userId){
    	return userMapper.selectByPrimaryKey(userId);
    }
	
	
	
	
	
}
