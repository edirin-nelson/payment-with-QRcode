package com.isdservices.paymentwithqrcode.dto.response;


import com.isdservices.paymentwithqrcode.flutterAPI.AccountCreationData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAccountResponse {

    private String status;
    private String message;
    private AccountCreationData data;
}
