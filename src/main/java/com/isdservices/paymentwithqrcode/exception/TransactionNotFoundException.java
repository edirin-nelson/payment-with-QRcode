package com.isdservices.paymentwithqrcode.exception;

import lombok.Data;

@Data
public class TransactionNotFoundException extends RuntimeException{
    public TransactionNotFoundException(String message){
        super(message);
    }
}
