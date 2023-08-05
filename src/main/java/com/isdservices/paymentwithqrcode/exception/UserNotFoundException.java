package com.isdservices.paymentwithqrcode.exception;

import lombok.Data;

@Data
public class UserNotFoundException extends Exception{
    public UserNotFoundException(String message) {
        super(message);
    }
}
