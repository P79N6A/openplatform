package com.kd.nvoap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class resolveController {
    @RequestMapping("/resolve")
	public ModelAndView basic() {
		ModelAndView model = new ModelAndView("resolve");
		return model;
	}
}  