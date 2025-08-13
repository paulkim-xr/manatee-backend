package com.rathon.manatee.auth.service;

import com.rathon.manatee.auth.dto.OtpVerifyResponseDto;
import com.rathon.manatee.database.dto.EmployeeDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class MfaService {
    private final RestTemplate restTemplate;
    private final String base;
    private final String verify;
    private final String enroll;
    private final String delete;
    private final String notify;
    private final String bioDelete;

    public MfaService(
            RestTemplate restTemplate,
            @Value("${mfa.base}") String base,
            @Value("${mfa.otp.api.verify}") String verify,
            @Value("${mfa.otp.api.enroll}") String enroll,
            @Value("${mfa.otp.api.delete}") String delete,
            @Value("${mfa.otp.api.notify}") String notify,
            @Value("${mfa.bio.api.delete}") String bioDelete
    ) {
        this.restTemplate = restTemplate;
        this.base = base;
        this.verify = verify;
        this.enroll = enroll;
        this.delete = delete;
        this.notify = notify;
        this.bioDelete = bioDelete;
    }

    public boolean verifyOtp(String username, Long companyId, String otp) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);

//            String urlWithParams = base + verify + "?cmpCd=" + companyId + "&uid=" + username + "&otpNum=" + otp;
//            ResponseEntity<OtpVerifyResponseDto> response = restTemplate.postForEntity(urlWithParams, request, OtpVerifyResponseDto.class);
            ResponseEntity<OtpVerifyResponseDto> response = restTemplate.postForEntity(base + verify, request, OtpVerifyResponseDto.class, companyId, username, otp);

            assert response.getBody() != null;
            return response.getBody().getSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String enrollOtp(String companyCd, String userId) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<OtpRegRequest> request = new HttpEntity<>(new OtpRegRequest(companyCd, userId), headers);

            ResponseEntity<OtpResponse> response = restTemplate.postForEntity(base + enroll, request, OtpResponse.class);

            assert response.getBody() != null;
            return response.getBody().getMsg();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public void disableOtp(String companyCd, String userId) {
        try {
            restTemplate.delete(base + delete, companyCd, userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void notifyDevice(String companyCd, String userId) {
        notifyDevice(companyCd, userId, "Manatee", "New login attempt", null);
    }

    public void notifyDevice(String companyCd, String userId, String title, String body, String intent) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            FCMNotificationRequestDTO dto = new FCMNotificationRequestDTO(companyCd, userId, title, body, intent);
            HttpEntity<FCMNotificationRequestDTO> request = new HttpEntity<>(dto, headers);

            restTemplate.postForEntity(base + notify + (intent == null ? "" : "/intent"), request, OtpVerifyResponseDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disableBio(String companyCd, String userId) {
        restTemplate.delete(base + bioDelete, companyCd, userId);
    }

    public boolean getBioStatus(String companyCd, String userId) {
        Object result = restTemplate.getForEntity(base + "/api/v1/fido/" + companyCd + "/" + userId, String.class);
        return false;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private static class FCMNotificationRequestDTO {
        private String companyCd;
        private String targetUserId;
        private String title;
        private String body;
        private String intent;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private static class OtpRegRequest {
        private String companyCd;
        private String userId;
    }

    @Getter
    @Setter
    private static class OtpResponse {
        private boolean success;
        private String code;
        private String msg;
        private int status;
    }
}
