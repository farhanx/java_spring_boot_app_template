package com.springboot.template;
//https://github.com/farhanx/java_spring_boot_app_template
import com.springboot.template.services.UserRegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TemplateApplication {

    private static final Logger logger = LoggerFactory.getLogger(UserRegistrationService.class);

    public static void main(String[] args) {
        SpringApplication.run(TemplateApplication.class, args);
        logger.info("Template Author's link - https://github.com/farhanx/java_spring_boot_app_template -");



    }

}
