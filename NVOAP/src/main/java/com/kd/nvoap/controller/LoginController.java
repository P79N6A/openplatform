package com.kd.nvoap.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class LoginController {
    @RequestMapping("/login")
	public ModelAndView basic() {
		ModelAndView model = new ModelAndView("login");
		return model;
	}
}  