package com.isdservices.paymentwithqrcode.flutterAPI;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountCreationData {
    private String response_code;
    private String response_message;
    private String flw_ref;
    private String order_ref;
    private String account_number;
    private String frequency;
    private String bank_name;
    private String created_at;
    private String expiry_date;
    private String note;
    private String amount;
}
