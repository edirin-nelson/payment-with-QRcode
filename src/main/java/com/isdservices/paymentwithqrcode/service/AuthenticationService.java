package com.isdservices.paymentwithqrcode.service;


import com.isdservices.paymentwithqrcode.dto.request.LoginRequest;
import com.isdservices.paymentwithqrcode.dto.response.LoginResponse;
import com.isdservices.paymentwithqrcode.exception.UserAccountDisabledException;
import com.isdservices.paymentwithqrcode.exception.UserNotFoundException;

public interface AuthenticationService  {

    LoginResponse authenticateUser(LoginRequest request) throws UserNotFoundException, UserAccountDisabledException;
}
