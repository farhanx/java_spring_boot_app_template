package com.springboot.template.services;


import com.springboot.template.domains.User;
import com.springboot.template.domains.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserAuthenticationService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userEntity = userRepository.findByEmailIgnoreCase(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
        //for (Role roleEntity : userEntity.getRole()) {
        grantedAuthorities.add(new SimpleGrantedAuthority(userEntity.getRole().getId()));
       // }
        boolean isEnabled = true, accountNonExpired = true, credentialsNonExpired = true, accountNonLocked = true;
        logger.info(String.format("CustomUserDetailsService.loadUserByUsername :: User account having email: %s ", userEntity.getEmail()));

        return new org.springframework.security.core.userdetails.User(userEntity.getEmail(), userEntity.getPassword(), isEnabled,
                accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuthorities);
    }
}