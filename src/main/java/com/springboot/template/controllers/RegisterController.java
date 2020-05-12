package com.springboot.template.controllers;


import com.springboot.template.models.RegisterForm;
import com.springboot.template.services.UserRegistrationService;
import com.springboot.template.common.CommonUtilities;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;


@Controller
public class RegisterController {

    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserRegistrationService userRegistrationService;

  /*  @GetMapping(value = "/login")
    public String getLoginPage(Model model) {
        model.addAttribute("LOGIN", new LoginForm());
        return "login";
    }*/

    @GetMapping(value = "/register")
    public String getRegisterPage(Model model) {
        logger.info("RegisterController.getRegisterPage :: Returning registration page.");
        model.addAttribute("register", new RegisterForm());
        return "register";
    }

    @PostMapping(value = "/register")
    public String register(@ModelAttribute(name = "register") @Valid RegisterForm register, BindingResult result, Model model, HttpServletRequest request)
    {
        logger.info("RegisterController.register :: Registration request received.");
        if (result.hasErrors())
        {
            logger.error("RegisterController.register :: One or more validation errors occurred while processing registration request.");
            return "register";
        }
        else
        {
            try {
                userRegistrationService.register(register);
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                logger.error("RegisterController.register :: Account already exists with the provided email. Putting error message on UI for the new registration request having email: " + register.getEmail());
                CommonUtilities.putMessageOnPage(model, "account.already.exists.with.provided.email", "register", register, messageSource, false);
                return "register";
            }
        }

        logger.info("RegisterController.register :: Initial validation passed for registration request having email: " + register.getEmail());
        return "login";
    }


}