package com.bee.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bee.user.pojo.MallUser;
import com.bee.user.service.IUserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicantTest {
	@Autowired
	private IUserService userService;
	@Test
	public void testRegister(){
	   MallUser user = new MallUser();
	   user.setUsername("zhangsan");
	   user.setPassword("123456");
	   user.setEmail("11111111#qq.com");
	   user.setPhone("18738700810");
	   user.setAnswer("答案");
	   user.setQuestion("问题");
	   user.setRole(0);
	   userService.register(user);
	   
	}

}
