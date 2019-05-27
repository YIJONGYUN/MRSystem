package com.gsitm.mrs.interceptor;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import com.gsitm.mrs.user.service.UserService;

/**
 * 로그인 상태에서 url 조작으로 "/" 접근 방지 역할을 해주는 인터셉터 
 * preHandle 메소드, postHandle 메소드 활용
 * 
 * @Package : com.gsitm.mrs.interceptor
 * @date : 2019. 5. 23.
 * @author : 이종윤
 */
public class HomeInterceptor extends HandlerInterceptorAdapter {
	
	private static final String LOGIN = "login";
	private static final Logger logger = LoggerFactory.getLogger(HomeInterceptor.class);
	
	@Inject
	private UserService service;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();

		Object user = session.getAttribute(LOGIN);
		
		Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");

		
		if (loginCookie != null) {
			
			if (user != null) {
				// 로그인 한 유저가 주소창을 통해 직접 "/"로 들어왔을 경우를 대비하여 이전 URL 불러오기
				Object url = session.getAttribute("prevURL");	
				logger.info("이전 URL : " + url);
				
				response.sendRedirect(url.toString());
				
				return false;
			}
			
			user = service.getInfo(loginCookie.getValue());
			
			logger.info("자동 로그인! >> " + user.toString());
			
			session.setAttribute(LOGIN, user);
			response.sendRedirect("/reservation/statusCalendar");
			
		}
		
		return true;
	}

}
