package com.isdservices.paymentwithqrcode.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QRcodeRequest {

    private String bankName;
    private String accountNumber;
}
