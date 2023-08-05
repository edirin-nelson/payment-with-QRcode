package com.isdservices.paymentwithqrcode.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

// class to get the loggedIn User SecurityContextHolder details
public class SecurityUtils {

    public UserDetails getCurrentUserDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null){
            return null;
        }
        Object principal = authentication.getPrincipal();
        if(principal != null){
            return (UserDetails) principal;
        }
        return null;
    }
}
