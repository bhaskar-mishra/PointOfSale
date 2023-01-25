package com.increff.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.increff.employee.model.InfoData;
import com.increff.employee.util.SecurityUtil;
import com.increff.employee.util.UserPrincipal;

@Controller
public abstract class AbstractUiController {

	@Autowired
	private InfoData info;

	@Value("${app.baseUrl}")
	private String baseUrl;

	protected ModelAndView mav(String page) {
		// Get current user
//		UserPrincipal principal = SecurityUtil.getPrincipal();
//
//		info.setEmail(principal == null ? "" : principal.getEmail());

		// Set info
		ModelAndView mav = new ModelAndView(page);
		mav.addObject("info", info);
		mav.addObject("baseUrl", baseUrl);
		return mav;
	}

	protected ModelAndView mav(String page,String randomKey) {
		// Get current user
//		UserPrincipal principal = SecurityUtil.getPrincipal();
//
//		info.setEmail(principal == null ? "" : principal.getEmail());

		// Set info
		ModelAndView mav = new ModelAndView(page);
		mav.addObject("info", info);
		mav.addObject("baseUrl", baseUrl);
		mav.addObject("randomKey",randomKey);
		System.out.println(mav.getViewName());
		System.out.println("mav is being called and the id is "+randomKey);
		return mav;
	}

	protected ModelAndView mav(String page,String role,boolean dummy) {
		// Set info
		ModelAndView mav = new ModelAndView(page);
		mav.addObject("info", info);
		mav.addObject("baseUrl", baseUrl);
		mav.addObject("role",role);
		return mav;
	}

}
