package com.springboot.template.controllers;


import com.springboot.template.services.UserRegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/dashboard")
public class DashboardController extends MainController{

    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserRegistrationService userRegistrationService;

  /*  @GetMapping(value = "/login")
    public String getLoginPage(Model model) {
        model.addAttribute("LOGIN", new LoginForm());
        return "login";
    }*/

    @GetMapping(value = {"","/"})
    public String getDashboardPage(Model model) {

        model.addAttribute("user",  getUserObject());
        logger.info("Welcome to the Dashboard");
        return "dashboard";
    }

}