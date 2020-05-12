package com.springboot.template.controllers;


import com.springboot.template.domains.repository.UserRepository;
import com.springboot.template.models.LoginForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    public MessageSource messageSource;

    @Autowired
    public UserRepository userRepository;

    @GetMapping(value = "/login")
    public String getLoginPage(Model model) {
        logger.info("LoginController.getLoginPage :: Returning login page.");
        model.addAttribute("LOGIN", new LoginForm());
        return "login";
    }

    @GetMapping(value = "/logout")
    public String getLogoutPage(Model model) {
        logger.info("LoginController.getLoginPage :: Returning login page.");
        model.addAttribute("LOGIN", new LoginForm());
        return "login";
    }

//    @PostMapping(value = PATH_LOGIN_PAGE)
//    public String login(@ModelAttribute(name = LOGIN) @Valid Login login, BindingResult result, Model model) {
//        String email = login.getEmail();
//        String password = login.getPassword();
//        System.out.println("LoginController.login :: Login request received for user having email: " + email);
//        if(result.hasErrors()) {
//            return LOGIN_PAGE_NAME;
//        } else {
//            if ("admin@admin.com".equals(email) && "admin".equals(password)) {
//                return "home";
//            } else {
//                Locale locale = LocaleContextHolder.getLocale();
//                String message = messageSource.getMessage(MESSAGE_INVALID_EMAIL_OR_PASSWORD, null, locale);
//                model.addAttribute(MESSAGE_KEY_FOR_MODEL_VIEW, message);
//                model.addAttribute(LOGIN, login);
//                return LOGIN_PAGE_NAME;
//            }
//        }
//    }
}