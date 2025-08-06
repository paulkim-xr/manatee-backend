package com.rathon.manatee.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtpVerifyResponseDto {
    private Boolean success;
    private Integer status;
    private String code;
    private String msg;
}
