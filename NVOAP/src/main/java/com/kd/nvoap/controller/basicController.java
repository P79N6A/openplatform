package com.kd.nvoap.controller;

import com.kd.nvoap.model.User;
import com.kd.nvoap.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Controller
public class basicController {
    @RequestMapping("/basic")
	public ModelAndView basic() {
		ModelAndView model = new ModelAndView("basic");
		return model;
	}
}  