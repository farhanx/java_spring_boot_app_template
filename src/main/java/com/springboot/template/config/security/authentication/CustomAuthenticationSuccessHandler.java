package com.springboot.template.config.security.authentication;

import com.springboot.template.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;


@Component("authenticationSuccessHandler")
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        User principal = (User) authentication.getPrincipal();
        logger.info("CustomAuthenticationSuccessHandler.onAuthenticationSuccess :: Authentication successful for user: " + principal.getUsername());
        boolean isAdmin = false;
        Iterator<GrantedAuthority> iterator = principal.getAuthorities().iterator();
        while (iterator.hasNext()) {
            String authority = iterator.next().getAuthority();
            if(authority.equalsIgnoreCase(Constants.USER)) {
                isAdmin = true;
            }
        }

        if(isAdmin) {
            logger.info("CustomAuthenticationSuccessHandler.onAuthenticationSuccess :: Authenticated user: " + principal.getUsername() + " has role admin. Now redirecting to the admin home page.");
            httpServletResponse.sendRedirect("/dashboard");
        } else {
            logger.info("CustomAuthenticationSuccessHandler.onAuthenticationSuccess :: Authenticated user: " + principal.getUsername() + " doesn't have admin role. Now redirecting to the user home page.");
            //httpServletResponse.sendRedirect(PATH_USER);
            httpServletResponse.sendRedirect("/dashboard2");
        }
    }
}
