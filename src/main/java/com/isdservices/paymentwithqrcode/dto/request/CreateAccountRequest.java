package com.isdservices.paymentwithqrcode.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAccountRequest {

    @JsonProperty(required = true)
    private String email;
    @JsonProperty(required = true)
    private boolean is_permanent;
    @JsonProperty(required = true)
    private String bvn;
    @JsonProperty(required = true)
    private String tx_ref;
    @JsonProperty(required = true)
    private String phonenumber;
    @JsonProperty(required = true)
    private String firstname;
    @JsonProperty(required = true)
    private String lastname;
    @JsonProperty(required = true)
    private String narration;
}
