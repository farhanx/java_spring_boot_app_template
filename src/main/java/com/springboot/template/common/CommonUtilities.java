package com.springboot.template.common;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CommonUtilities {

    private static final Logger logger = LoggerFactory.getLogger(CommonUtilities.class);


    public static Timestamp CURRENT_TIMESTAMP() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static Timestamp ADD_TIME_TO_CURRENT_TIMESTAMP(TimeUnit unitToAdd, long valueToAdd) {
        Timestamp currentTimestamp = CURRENT_TIMESTAMP();
        long newAdjustedTime = getAddedTimeInMillis(unitToAdd, valueToAdd);
        currentTimestamp.setTime(currentTimestamp.getTime() +  newAdjustedTime);
        return currentTimestamp;
    }

    public static void validateIncomingRequestIsNotEmpty(Object request) {
        if(isNull(request)) {
            logger.error("CommonOperations.validateIncomingRequestIsNotEmpty :: Incoming request cannot be empty.");
            throw new IllegalArgumentException("Empty request received.");
        }
    }


    public static String getAuthenticatedLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User authenticationUser = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
        return authenticationUser.getUsername();
    }


    private static long getAddedTimeInMillis(TimeUnit timeunit, long valueToAdd) {
        TimeUnit newTimeUnit;
        switch (timeunit) {
            default:
            case MINUTES:
                newTimeUnit = TimeUnit.MINUTES;
                break;
            case HOURS:
                newTimeUnit = TimeUnit.HOURS;
                break;
            case DAYS:
                newTimeUnit = TimeUnit.DAYS;
                break;
            case SECONDS:
                newTimeUnit = TimeUnit.SECONDS;
                break;
            case NANOSECONDS:
                newTimeUnit = TimeUnit.NANOSECONDS;
                break;
            case MICROSECONDS:
                newTimeUnit = TimeUnit.MICROSECONDS;
                break;
            case MILLISECONDS:
                newTimeUnit = TimeUnit.MILLISECONDS;
                break;
        }
        return newTimeUnit.toMillis(valueToAdd);
    }

    public static String getClientIp(HttpServletRequest request) {
        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getHeader("X-FORWARDED-FOR".toLowerCase());
                if (remoteAddr == null || "".equals(remoteAddr)) {
                    remoteAddr = request.getRemoteAddr();
                }
            }
        }
        logger.info("CommonOperations.getClientIp :: Client IP is: " + remoteAddr);
        return remoteAddr;
    }

    public static String getAppURL(HttpServletRequest request) {
        String appUrl =
                "http://" + request.getServerName() +
                        ":" + request.getServerPort() +
                        request.getContextPath();
        logger.info("***************************************************************************************************************************");
        logger.info("***************************************************************************************************************************");
        logger.info("RegisterController.register :: AppURL: " + appUrl);
        logger.info("***************************************************************************************************************************");
        logger.info("***************************************************************************************************************************");
        return appUrl;
    }

    public static String addLanguageCodeToURL(HttpServletRequest request) {
        Locale locale = request.getLocale();
        return "?lang=" + locale;
    }

    public static boolean isNull(Object valueToCheck) {
        return null == valueToCheck;
    }

    public static boolean isNullOrEmpty(String valueToCheck) {
        return (null == valueToCheck || valueToCheck.trim().length() == 0);
    }

    public static boolean isNullOrEmpty(List<? extends Object> valueToCheck) {
        return (null == valueToCheck || valueToCheck.size() == 0);
    }

    public static void putMessageOnPage(Model model, String message, String objectNameKey, Object actualObject, MessageSource messageSource, Boolean isSuccess) {
        Locale locale = LocaleContextHolder.getLocale();
        String localizedMessage = messageSource.getMessage(message, null, locale);
        model.addAttribute("msg", localizedMessage);
        if(!isNull(isSuccess)) {
            model.addAttribute("isSuccess", isSuccess);
        }
        if(null != objectNameKey) {
            model.addAttribute(objectNameKey, actualObject);
        }
    }

    public static String goToGeneralErrorPage(String message, Model model, MessageSource messageSource) {
        putMessageOnPage(model, message, null, null, messageSource, false);
        return "error/general-error-page";
    }

}