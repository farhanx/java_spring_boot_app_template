package com.springboot.template.controllers;

import com.springboot.template.domains.User;
import com.springboot.template.domains.repository.UserRepository;
import com.springboot.template.common.CommonUtilities;
import org.springframework.beans.factory.annotation.Autowired;

public class MainController {

    @Autowired
    public  UserRepository userRepository;

    protected  User getUserObject()
    {
        String email = CommonUtilities.getAuthenticatedLogin();
        User userEntity = userRepository.findByEmailIgnoreCase(email);
        return userEntity;
    }
}
