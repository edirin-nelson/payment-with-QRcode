package com.isdservices.paymentwithqrcode.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MerchantDetailsDto {
    private String bankName;
    private String accountNumber;
    private String merchantName;
}