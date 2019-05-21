package com.kd.nvoap.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ForgetPwdController {
    @RequestMapping("/forgetpwd")
	public ModelAndView basic() {
		ModelAndView model = new ModelAndView("forgetpwd");
		return model;
	}
}  