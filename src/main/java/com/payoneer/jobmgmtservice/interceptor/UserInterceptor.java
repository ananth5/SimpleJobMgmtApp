package com.payoneer.jobmgmtservice.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Ananth Shanmugam General interceptor class 
 */
@Component
public class UserInterceptor implements HandlerInterceptor {

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		// get current user principal
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			// check if the current user principal is valid.
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}
}
