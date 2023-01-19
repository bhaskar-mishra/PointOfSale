package com.increff.employee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SiteUiController extends AbstractUiController {

	// WEBSITE PAGES
	@RequestMapping(value = "")
	public ModelAndView index() {
		return mav("index.html");
	}

	@RequestMapping(value = "/site/login")
	public ModelAndView login() {
		return mav("login.html");
	}

	@RequestMapping(value = "/site/logout")
	public ModelAndView logout() {
		return mav("logout.html");
	}

	@RequestMapping(value = "/site/pricing")
	public ModelAndView pricing() {
		return mav("pricing.html");
	}

	@RequestMapping(value = "/site/features")
	public ModelAndView features() {
		return mav("features.html");
	}

	@RequestMapping(value = "/site/brands")
	public ModelAndView brands(){return mav("brands.html");}
	@RequestMapping(value = "/site/products")
	public ModelAndView products(){return mav("products.html");}
	@RequestMapping(value = "/site/inventory")
	public ModelAndView inventory(){return mav("inventory.html");}
	@RequestMapping(value = "/site/orders")
	public ModelAndView order(){return mav("orders.html");}
	@RequestMapping(value = "/site/orderItem/{randomKey}")
	public ModelAndView orderItem(@PathVariable String randomKey){return mav("orderItem.html",randomKey);}

	@RequestMapping(value = "/site/reports")
	public ModelAndView reports(){return mav("reports.html");}

	@RequestMapping(value = "/site/salesReport")
	public ModelAndView salesReport(){return mav("salesReport.html");}

	@RequestMapping(value = "/site/brandsReport")
	public ModelAndView brandsReport(){return mav("brandsReport.html");}
	@RequestMapping(value = "/site/inventoryReport")
	public ModelAndView inventoryReport(){return mav("inventoryReport.html");}
}
