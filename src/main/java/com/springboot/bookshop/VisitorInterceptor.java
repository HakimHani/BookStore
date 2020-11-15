package com.springboot.bookshop;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.springboot.bookshop.Visitor;

@Component
public class VisitorInterceptor extends HandlerInterceptorAdapter{



	@Override
	public boolean preHandle
	(HttpServletRequest request, HttpServletResponse response, Object handler) 
			throws Exception {
		
		//System.out.println(request.getSession().getAttributeNames());
		/*
		Enumeration<String> attributes = request.getSession().getAttributeNames();
		while (attributes.hasMoreElements()) {
		    String attribute = (String) attributes.nextElement();
		    System.out.println(attribute+" : "+request.getSession().getAttribute(attribute));
		}*/
		
		//if(request.getSession().getAttribute("scopedTarget.visitor") == null) {
		//System.out.println(request.getSession().getAttribute("visitor"));
		if(request.getSession().getAttribute("visitor") == null) {
			System.out.println("[Interceptor] creating  visitor");
			request.getSession().setAttribute("visitor", new Visitor());
			//request.getSession().setAttribute("scopedTarget.visitor", new Visitor());
		};
		//System.out.println("Pre Handle method is Calling");
		return true;
	}
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler, ModelAndView modelAndView) throws Exception {

		//System.out.println("Post Handle method is Calling");
	}
	@Override
	public void afterCompletion
	(HttpServletRequest request, HttpServletResponse response, Object 
			handler, Exception exception) throws Exception {

		//System.out.println("Request and Response is completed");
	}



}
