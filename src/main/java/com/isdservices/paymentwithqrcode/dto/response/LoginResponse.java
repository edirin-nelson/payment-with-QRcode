package com.isdservices.paymentwithqrcode.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    private String userID;
    private String token;
    private String firstName;
    private String userType;
    private Date expiredAt;

}
