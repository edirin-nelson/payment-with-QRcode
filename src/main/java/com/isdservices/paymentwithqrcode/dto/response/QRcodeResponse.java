package com.isdservices.paymentwithqrcode.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QRcodeResponse {

    private String bankName;
    private String accountNumber;
    private String accountName;
}
