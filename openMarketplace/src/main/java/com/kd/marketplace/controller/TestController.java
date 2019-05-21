package com.kd.marketplace.controller;

import com.kd.marketplace.model.User;
import com.kd.marketplace.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
@RequestMapping("/test")
public class TestController {
    @Resource
    private UserService userService;

    @RequestMapping("/testDemo")
    public ModelAndView test1(){
        User users=null;
        ModelAndView model=new ModelAndView("/hello");
        try{
            users = userService.findUser();
            if(users!=null){
                model.addObject("users",users);
                return model;

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return model;
    }
}
