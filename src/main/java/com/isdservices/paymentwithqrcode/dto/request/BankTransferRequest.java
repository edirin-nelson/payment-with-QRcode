package com.isdservices.paymentwithqrcode.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankTransferRequest {
    private String bankName;
    private String accountNumber;
    private String accountName;
    private String amount;
    private String pin;
}
