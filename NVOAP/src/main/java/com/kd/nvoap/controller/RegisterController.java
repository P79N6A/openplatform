package com.kd.nvoap.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class RegisterController {
    @RequestMapping("/register")
	public ModelAndView basic() {
		ModelAndView model = new ModelAndView("register");
		return model;
	}
}  