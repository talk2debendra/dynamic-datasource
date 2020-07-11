package com.deb.dynamicdatasource.interceptor;

import java.util.Objects;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.deb.dynamicdatasource.config.DataBaseContextHolder;
import com.deb.dynamicdatasource.enums.DatabaseContext;
import com.deb.dynamicdatasource.enums.Satelites;

import lombok.extern.log4j.Log4j2;

/**
 * LoginInterceptor that allows for customized handler execution chains.
 * 
 * <p>
 * A HandlerInterceptor gets called before the appropriate HandlerAdapter
 * triggers the execution of the handler itself. This is the trigger point which
 * checks for the session validation or not before accessing any resources of
 * the application.
 *
 *
 * @see HandlerExecutionChain#getInterceptors
 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter
 */

@Component
@Log4j2
public class RequestInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		log.info("Intercepting request : prehandle method");

		String type = request.getParameter("type");

		Satelites sateLiteType = Satelites.getSatelite(type);
		if (Objects.nonNull(type)) {
			switch (sateLiteType) {
			case SARAL:
				DataBaseContextHolder.setCurrentDb(DatabaseContext.SATELITE_SARAL);
				break;

			case ARAYABHAT:
				DataBaseContextHolder.setCurrentDb(DatabaseContext.SATELITE_ARYABHAT);
				break;

			default:
				DataBaseContextHolder.setCurrentDb(DatabaseContext.MAIN);
				break;
			}
		} else {
			DataBaseContextHolder.setCurrentDb(DatabaseContext.MAIN);
		}
		log.info("Databse context holder :{}", DataBaseContextHolder.getCurrentDb());

		return true;

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		log.info("Intercepting request : posthandle method");

		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate,max-stale=0,post-check=0,pre-check=0");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		int sessionTimeoutValue = request.getSession().getMaxInactiveInterval();
		Cookie cookie = new Cookie("TimeoutCookie", "1");
		cookie.setMaxAge(sessionTimeoutValue);
		response.addCookie(cookie);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

		log.info("Intercepting request : aftercompletion method");
	}

}
