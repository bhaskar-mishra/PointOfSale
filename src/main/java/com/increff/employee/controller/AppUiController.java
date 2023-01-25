package com.increff.employee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppUiController extends AbstractUiController {

	private String role;
	@RequestMapping(value = "/ui/home/{role}")
	public ModelAndView home(@PathVariable String role) {

		this.role = role;
		return mav("home.html",role,true);
	}

	@RequestMapping(value = "/ui/home/alternative")
	public ModelAndView home() {
		return mav("home.html",role,true);
	}

	@RequestMapping(value = "/ui/employee")
	public ModelAndView employee() {
		return mav("employee.html");
	}

	@RequestMapping(value = "/ui/admin")
	public ModelAndView admin() {
		return mav("user.html");
	}

	@RequestMapping(value = "/site/brands")
	public ModelAndView brands(){return mav("brands.html",role,true);}
	@RequestMapping(value = "/site/products")
	public ModelAndView products(){return mav("products.html",role,true);}
	@RequestMapping(value = "/site/inventory")
	public ModelAndView inventory(){return mav("inventory.html",role,true);}
	@RequestMapping(value = "/site/orders")
	public ModelAndView order(){return mav("orders.html",role,true);}
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
