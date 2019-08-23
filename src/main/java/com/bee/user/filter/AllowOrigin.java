package com.bee.user.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class AllowOrigin implements Filter {
	
	private final List<String> allowedOrigins = Arrays.asList("http://192.168.0.70:8080","http://localhost:8080");// 允许跨域的地址
	

	

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse response =(HttpServletResponse)res;
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		String origin = request.getHeader("Origin");
		response.setHeader("Access-Control-Allow-Origin",allowedOrigins.contains(origin)? origin : "");
		response.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,DELETE");
		// 是否允许浏览器携带用户身份信息（cookie）
        response.setHeader("Access-Control-Allow-Credentials","true");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type,Content-Length, Authorization, Accept, X-Requested-With , yourHeaderFeild,XFILENAME,XFILECATEGORY,XFILESIZE");
		System.out.println("过滤器被使用");
		chain.doFilter(request, response);
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
