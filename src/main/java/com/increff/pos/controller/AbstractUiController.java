package com.increff.pos.controller;


import com.increff.pos.util.SecurityUtil;
import com.increff.pos.util.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.increff.pos.model.InfoData;

@Controller
public abstract class AbstractUiController {

	@Autowired
	private InfoData info;

	@Value("${app.baseUrl}")
	private String baseUrl;

	protected ModelAndView mav(String page) {


		UserPrincipal principal = SecurityUtil.getPrincipal();

		info.setEmail(principal == null ? "" : principal.getEmail());
		info.setRole(principal == null ? "" : principal.getRole());

		// Set info
		ModelAndView mav = new ModelAndView(page);
		mav.addObject("info", info);
		mav.addObject("baseUrl", baseUrl);
		return mav;
	}

	protected ModelAndView mav(String page,String orderCode) {

		UserPrincipal principal = SecurityUtil.getPrincipal();



		info.setEmail(principal == null ? "" : principal.getEmail());
		info.setRole(principal == null ? "" : principal.getRole());
		// Set info
		ModelAndView mav = new ModelAndView(page);
		mav.addObject("info", info);
		mav.addObject("baseUrl", baseUrl);
		mav.addObject("orderCode", orderCode);
		return mav;
	}

	protected ModelAndView mav(String page,String role,boolean dummy) {

		UserPrincipal principal = SecurityUtil.getPrincipal();


		info.setEmail(principal == null ? "" : principal.getEmail());
		info.setRole(principal == null ? "" : principal.getRole());
		// Set info
		ModelAndView mav = new ModelAndView(page);
		mav.addObject("info", info);
		mav.addObject("baseUrl", baseUrl);
		mav.addObject("role",role);
		return mav;
	}

}
