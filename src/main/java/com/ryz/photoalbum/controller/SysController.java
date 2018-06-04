package com.ryz.photoalbum.controller;

import com.ryz.photoalbum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/index")
public class SysController {

    @Autowired
    UserService userService;

    @RequestMapping(value="/",method = RequestMethod.GET)
    public String index(Model model){
        return "index";
    }

    @RequestMapping(value="/main",method = RequestMethod.GET)
    public ModelAndView mian(String id){
        ModelAndView view = new ModelAndView();
        view.setViewName("main");
        view.addObject("cUser",userService.findPUserById(id));
        return view;
    }

}
