package com.rathon.manatee.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OtpVerifyRequestDto {
    private String companyId;
    private String username;
    private String otp;
}
