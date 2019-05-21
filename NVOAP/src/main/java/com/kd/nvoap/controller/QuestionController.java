package com.kd.nvoap.controller;

import org.springframework.stereotype.Controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class QuestionController {
    @RequestMapping("/question")
	public ModelAndView basic() {
		ModelAndView model = new ModelAndView("question");
		return model;
	}
    
}  