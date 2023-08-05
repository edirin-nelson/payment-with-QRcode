package com.isdservices.paymentwithqrcode.service;


import com.isdservices.paymentwithqrcode.dto.request.RegisterRequest;
import com.isdservices.paymentwithqrcode.exception.UserAlreadyExistsException;
import com.isdservices.paymentwithqrcode.exception.UserNotFoundException;
import com.isdservices.paymentwithqrcode.model.User;

public interface UserService {

    User registerUser(RegisterRequest request) throws UserAlreadyExistsException, UserNotFoundException;
}
