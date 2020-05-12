package com.springboot.template.services;

import com.springboot.template.common.Constants;
import com.springboot.template.common.CryptoUtilities;
import com.springboot.template.domains.Role;
import com.springboot.template.domains.User;
import com.springboot.template.domains.repository.RoleRepository;
import com.springboot.template.domains.repository.UserRepository;
import com.springboot.template.models.RegisterForm;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class UserRegistrationService {

    private static final Logger logger = LoggerFactory.getLogger(UserRegistrationService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder userPasswordEncoder;

    @Transactional
    public void register(RegisterForm register) throws UnsupportedEncodingException, NoSuchAlgorithmException, MessagingException {
        User userEntity = saveUser(register);
        if (!isUserSavedSuccessfully(userEntity))
        {
            throw new RuntimeException("User not registered");
        }
    }


    private boolean isUserSavedSuccessfully(User userEntity) {
        if(null != userEntity.getId() && userEntity.getId().signum() == 1) {
            logger.info("UserRegistrationService.isUserSavedSuccessfully :: User account information saved successfully having email: " + userEntity.getEmail());
            return true;
        } else {
            logger.error("UserRegistrationService.isUserSavedSuccessfully :: Problem during saving user account having email: " + userEntity.getEmail());
            return false;
        }
    }

    private User saveUser(RegisterForm register) {
        if(accountExistsAlready(register.getEmail())) {
            throw new RuntimeException("User already registered");
        }
        User userEntity = createUserEntity(register);
        return userRepository.save(userEntity);
    }

    private boolean accountExistsAlready(String email) {
        logger.info("UserRegistrationService.accountExistsAlready :: Checking if an account already exists for email: " + email);
        User user = userRepository.findByEmailIgnoreCase(email);
        if(null != user) {
            logger.error("UserRegistrationService.accountExistsAlready :: Account already exists with the provided email: " + email);
            return true;
        } else {
            logger.info("UserRegistrationService.accountExistsAlready :: No account exists with the provided email: " + email);
            return false;
        }
    }

    private User createUserEntity(RegisterForm register) {
        logger.info("UserRegistrationService.createUserEntity :: Creating user entity having email: " + register.getEmail());
        User entity = new User();
        entity.setEmail(register.getEmail());
        entity.setPassword(userPasswordEncoder.encode(register.getPassword()));
        entity.setRole(createUserRoleEntity(Constants.USER));
        entity.setStatus(1);
        entity.setCreationDate(CryptoUtilities.getUnixTimeStamp());

        logger.info("UserRegistrationService.createUserEntity :: User entity created having email: " + register.getEmail());
        return entity;
    }

    private Role createUserRoleEntity(String roldtype) {
        Optional<Role> entity = roleRepository.findById(roldtype);
        Role role = entity.get();
        return role;
    }
}