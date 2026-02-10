package com.rathon.manatee.auth.service;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.StringUtils;

import javax.crypto.Mac;
import java.util.List;

@Slf4j
@Service
public class MfaService {
    private final RestTemplate restTemplate;
    private final String base;
    private final String verify;
    private final String enroll;
    private final String delete;
    private final String notify;
    private final String bioDelete;
    private final String mfaKey;

    public MfaService(
            RestTemplate restTemplate,
            @Value("${mfa.base}") String base,
            @Value("${mfa.otp.api.verify}") String verify,
            @Value("${mfa.otp.api.enroll}") String enroll,
            @Value("${mfa.otp.api.delete}") String delete,
            @Value("${mfa.otp.api.notify}") String notify,
            @Value("${mfa.bio.api.delete}") String bioDelete,
            @Value("${mfa.key:}") String mfaKey
    ) {
        this.restTemplate = restTemplate;
        this.base = base;
        this.verify = verify;
        this.enroll = enroll;
        this.delete = delete;
        this.notify = notify;
        this.bioDelete = bioDelete;
        this.mfaKey = mfaKey;
    }
    // -----------------------------------------------

    public String enrollV2(String userId, String displayName) {
        ApiResponse<String> response = registerAccountV2(ApiRequest.<SimpleId>builder()
                .systemName("demo-system")
                .description("test")
                .os("Windows")
                .app("Chrome")
                .location(new GeoLocation(37.476764, 126.887140, 0.0))
                .data(new SimpleId(userId, displayName))
                .build());

        return response.getData();
    }

    public String verifyV2(String userId) {
        ApiResponse<String> response = verifyAccountV2(ApiRequest.<String>builder()
                .systemName("demo-system")
                .description("test")
                .os("Windows")
                .app("Chrome")
                .location(new GeoLocation(37.476764, 126.887140, 0.0))
                .data(userId)
                .build());

        return response.getData();
    }

    public void deleteV2(String userId) {
        deleteAccountV2(ApiRequest.<String>builder()
                .systemName("demo-system")
                .description("test")
                .os("Windows")
                .app("Chrome")
                .location(new GeoLocation(37.476764, 126.887140, 0.0))
                .data(userId)
                .build());
    }

    public OtpRegistrationData enrollOtpV2(String companyCd, String username) {
        ApiResponse<OtpRegistrationData> response = otpRegisterV2(ApiRequest.<String>builder()
                .systemName("demo-system")
                .description("test")
                .os("Windows")
                .app("Chrome")
                .location(new GeoLocation(37.476764, 126.887140, 0.0))
                .data(username)
                .build());

        return response.getData();
    }

    public void alertOtpV2(String userId, String displayName) {
        otpAlertV2(ApiRequest.<SimpleId>builder()
                .systemName("demo-system")
                .description("test")
                .os("Windows")
                .app("Chrome")
                .location(new GeoLocation(37.476764, 126.887140, 0.0))
                .data(new SimpleId(userId, displayName))
                .build());
    }

    public boolean verifyOtpV2(String companyCd, String userId) {
        ApiResponse<Void> response = otpVerifyExistenceV2(ApiRequest.<String>builder()
                .systemName("demo-system")
                .description("test")
                .os("Windows")
                .app("Chrome")
                .location(new GeoLocation(37.476764, 126.887140, 0.0))
                .data(userId)
                .build());

        return response.isSuccess();
    }

    public boolean authOtpV2(String username, Long companyId, String otp) {
        ApiResponse<OtpAuthenticationData> response = otpAuthenticateV2(ApiRequest.<OtpAuthRequest>builder()
                .systemName("demo-system")
                .description("test")
                .os("Windows")
                .app("Chrome")
                .location(new GeoLocation(37.476764, 126.887140, 0.0))
                .data(new OtpAuthRequest(username, otp))
                .build());

        return response.isSuccess();
    }

    public OtpPolicy getOtpPolicyV2(String username) {
        try {
            ResponseEntity<OtpPolicy> resp = restTemplate.exchange(
                    base + "/api/v2/otp/policy?system=demo-system&username=" + username,
                    HttpMethod.GET,
                    new HttpEntity<>(jsonHeaders()),
                    new ParameterizedTypeReference<OtpPolicy>() {});
            return resp.getBody();
        } catch (Exception e) {
            return new OtpPolicy();
        }
    }

    public void disableOtpV2(String companyCd, String userId) {
        otpDeleteV2(ApiRequest.<String>builder()
                .systemName("demo-system")
                .description("test")
                .os("Windows")
                .app("Chrome")
                .location(new GeoLocation(37.476764, 126.887140, 0.0))
                .data(userId)
                .build());
    }

    public void enrollFidoV2(String userId, String displayName) {
        fido2RegisterV2(ApiRequest.<SimpleId>builder()
                .systemName("demo-system")
                .description("test")
                .os("Windows")
                .app("Chrome")
                .location(new GeoLocation(37.476764, 126.887140, 0.0))
                .data(new SimpleId(userId, displayName))
                .build());
    }

    public boolean verifyFido2V2(String companyCd, String userId) {
        ApiResponse<Void> response = fido2VerifyExistenceV2(ApiRequest.<String>builder()
                .systemName("demo-system")
                .description("test")
                .os("Windows")
                .app("Chrome")
                .location(new GeoLocation(37.476764, 126.887140, 0.0))
                .data(userId)
                .build());

        return response.isSuccess();
    }

    public Long authFidoV2(String userId, String displayName) {
        return Long.parseLong(fido2AuthenticateV2(ApiRequest.<SimpleId>builder()
                .systemName("demo-system")
                .description("test")
                .os("Windows")
                .app("Chrome")
                .location(new GeoLocation(37.476764, 126.887140, 0.0))
                .data(new SimpleId(userId, displayName))
                .build()).data);
    }

    public void disableFido2V2(String companyCd, String userId) {
        fido2DeleteV2(ApiRequest.<String>builder()
                .systemName("demo-system")
                .description("test")
                .os("Windows")
                .app("Chrome")
                .location(new GeoLocation(37.476764, 126.887140, 0.0))
                .data(userId)
                .build());
    }

    public String enrollPasskey(String username) {
        ApiResponse<String> response = passkeyEnroll(ApiRequest.<String>builder()
                .systemName("demo-system")
                .description("test")
                .os("Windows")
                .app("Chrome")
                .location(new GeoLocation(37.476764, 126.887140, 0.0))
                .data(username)
                .build());

        return response.getData();
    }

    public String createAuthSession(String username, String systemName, String appOrigin) {
        ApiResponse<AuthSessionToken> response = authSessionBootstrap(ApiRequest.<AuthSessionContext>builder()
                .systemName(systemName)
                .description("auth-session")
                .os("Windows")
                .app("Chrome")
                .location(new GeoLocation(37.476764, 126.887140, 0.0))
                .data(new AuthSessionContext(username, systemName, null, appOrigin))
                .build());

        return response.getData() != null ? response.getData().token() : null;
    }

    public boolean checkPasskey(String username, String tx) {
        ApiResponse<Void> response = passkeyCheck(ApiRequest.<UsernameTx>builder()
                .systemName("demo-system")
                .description("test")
                .os("Windows")
                .app("Chrome")
                .location(new GeoLocation(37.476764, 126.887140, 0.0))
                .data(new UsernameTx(username, tx))
                .build());

        return response.isSuccess();
    }

    public boolean disablePasskey(String username) {
        ApiResponse<Void> response = passkeyDelete(ApiRequest.<UsernameTx>builder()
                .systemName("demo-system")
                .description("test")
                .os("Windows")
                .app("Chrome")
                .location(new GeoLocation(37.476764, 126.887140, 0.0))
                .data(new UsernameTx(username, ""))
                .build());

        return response.isSuccess();
    }

    // ------------------------------------------------------------

    public record UsernameTx(String username, String tx) {}
    public record AuthSessionContext(String username, String systemName, Long txId, String appOrigin) {}
    public record AuthSessionToken(String token) {}

    @Getter
    private static class MfaServerResult<T> {
        private boolean success;
        private String code;
        private String msg;
        private int status;
        private T data;
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
    // --------------------------------------------------------------------
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ApiRequest<T> {
        String systemName = "demo-system";
        String description = "test";
        String os = "Windows";
        String app = "Chrome";
        GeoLocation location = new GeoLocation(37.476764, 126.887140, 0.0);
        MfaSecurityOptions securityOptions;
        T data;

        public ApiRequest(ApiRequest<?> original, T data) {
            this.systemName = original.systemName;
            this.description = original.description;
            this.location = original.location;
            this.securityOptions = original.securityOptions;
            this.data = data;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class ApiResponse<T> {
        @Builder.Default
        private boolean success = true;
        @Builder.Default
        private MfaResult code = MfaResult.OK;
        @Builder.Default
        private String message = "";
        private T data;

        public ApiResponse<T> success() {
            success = true;
            return this;
        }

        public ApiResponse<T> failure() {
            success = false;
            return this;
        }
    }

    public enum MfaResult {
        SUCCESS,
        FAILURE,
        TIMEOUT,
        ERROR,
        OK,
        TEST,
        IN_PROGRESS,
        CANCELLED,
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GeoLocation {
        private Double latitude;
        private Double longitude;
        private Double altitude;
    }

    public static class MfaSecurityOptions {
    }

    public record SimpleId(String username, String displayName) {
    }

    @Getter
    @Setter
    public static class OtpRegistrationData{
        private String secret;
        private OtpPolicy policy;
    }

    @Getter
    public static class OtpPolicy {
        private MfaType mfaType;
        /**
         * <p>{@link Mac#getInstance(String)}에 사용될 OTP 생성 알고리즘</p>
         * <p>{@code HmacMD5}, {@code HmacSHA1}, {@code HmacSHA256}를 지원함</p>
         * <p>기본값 {@code HmacSHA1}</p>
         */
        @Value("${mfa-config.otp.policy.algorithm:HmacSHA1}")
        private Algorithm algorithm;

        /**
         * <p>OTP 종류</p>
         * <p>기본값 {@code OtpType.TOTP}</p>
         * @see <a href="https://datatracker.ietf.org/doc/html/rfc4226">HOTP: An HMAC-Based One-Time Password Algorithm</a>
         * @see <a href="https://datatracker.ietf.org/doc/html/rfc6238">TOTP: Time-Based One-Time Password Algorithm</a>
         */
        @Value("${mfa-config.otp.policy.type:TOTP}")
        private OtpType type;

        /**
         * <p>OTP 코드 길이</p>
         * <p>RFC6238에서는 6자리를 권장함</p>
         * <p>기본값 {@code 6}</p>
         */
        @Value("${mfa-config.otp.policy.digits:6}")
        private Integer digits;

        /**
         * <p>OTP 오차 허용범위</p>
         * <p>높은 값을 가질수록 허용하는 시간/카운터 오차가 늘어난다</p>
         * <p>기본값 {@code 1}</p>
         */
        @Value("${mfa-config.otp.policy.window-size:1}")
        private Integer windowSize;

        /**
         * <p>암호화된 OTP 생성 비밀키 길이</p>
         * <p> We also RECOMMEND storing the keys securely in the validation system,
         *    and, more specifically, encrypting them using tamper-resistant
         *    hardware encryption and exposing them only when required: for
         *    example, the key is decrypted when needed to verify an OTP value, and
         *    re-encrypted immediately to limit exposure in the RAM to a short
         *    period of time.</p>
         * <p>비밀키는 최소 128비트여야 하며 160비트를 권장함</p>
         */
        @Value("${mfa-config.otp.policy.key-length:160}")
        private Integer keyLength;

        /**
         * <p>HOTP 사용시 필요한 카운터</p>
         * <p>TOTP 사용시에는 {@code -1}를 저장한다</p>
         * <p>기본값 {@code -1}</p>
         */
        @Value("${mfa-config.otp.policy.counter:-1}")
        private Integer counter;

        /**
         * <p>TOTP 사용시 필요한 생성주기 (단위: 초)</p>
         * <p>RFC6238에서는 30초를 권장함</p>
         * <p>HOTP 사용시 {@code -1}를 저장한다</p>
         * <p>기본값 {@code 30}</p>
         */
        @Value("${mfa-config.otp.policy.period}")
        private Integer period;

        public OtpPolicy() {
            this.mfaType = MfaType.OTP;
            this.algorithm = Algorithm.HmacSHA1;
            this.type = OtpType.TOTP;
            this.digits = 6;
            this.windowSize = 2;
            this.keyLength = 160;
            this.counter = -1;
            this.period = 30;
        }

        public OtpPolicy addCounter() {
            this.counter++;
            return this;
        }
    }

    public enum MfaType {
        // Possession Factors
        OTP,
        EMAIL,
        SMS,
        HARDWARE_TOKEN,
        // Inherence Factors
        FIDO,
    }

    public enum Algorithm {
        HmacMD5,
        HmacSHA1,
        HmacSHA256,
    }

    public enum OtpType {
        HOTP,
        TOTP
    }

    public record OtpAuthRequest(String username, String otp) {
    }

    public static class OtpAuthenticationData {
        private String systemName;
        private String username;
    }

    private HttpHeaders jsonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        if (StringUtils.hasText(mfaKey)) {
            headers.set("X-RATHON-MFA", mfaKey);
        }
        return headers;
    }

    private <T> HttpEntity<ApiRequest<T>> json(ApiRequest<T> body) {
        return new HttpEntity<>(body, jsonHeaders());
    }

    private <B, R> ApiResponse<R> requestV2(String path,
                                         HttpMethod httpMethod,
                                         ApiRequest<B> body,
                                         ParameterizedTypeReference<ApiResponse<R>> typeRef) {
        try {
            log.info("Path: {}, Request: {}", base + path, json(body));
            ResponseEntity<ApiResponse<R>> resp = restTemplate.exchange(
                    base + path, httpMethod, json(body), typeRef);
            return resp.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            ApiResponse<R> fail = new ApiResponse<R>().failure();
            fail.setMessage(e.getMessage());
            return fail;
        }
    }

    // ---------- v2 Admin ----------
    /** POST /api/v2/admin/user  (ApiRequest<SimpleId> -> ApiResponse<?> or concrete type if you have it) */
    private ApiResponse<String > registerAccountV2(ApiRequest<SimpleId> request) {
        return requestV2("/api/v2/admin/user", HttpMethod.POST, request,
                new ParameterizedTypeReference<ApiResponse<String>>() {});
    }

    private ApiResponse<String> verifyAccountV2(ApiRequest<String> request) {
        return requestV2("/api/v2/user/verify", HttpMethod.POST, request,
                new ParameterizedTypeReference<ApiResponse<String>>() {});
    }

    private ApiResponse<Void> deleteAccountV2(ApiRequest<String> request) {
        return requestV2("/api/v2/user/delete", HttpMethod.POST, request,
                new ParameterizedTypeReference<ApiResponse<Void>>() {});
    }

// ---------- v2 OTP ----------
    /** POST /api/v2/otp/register  (ApiRequest<String> -> ApiResponse<OtpRegistrationData>) */
    private ApiResponse<OtpRegistrationData> otpRegisterV2(ApiRequest<String> request) {
        return requestV2("/api/v2/otp/register", HttpMethod.POST, request,
                new ParameterizedTypeReference<ApiResponse<OtpRegistrationData>>() {});
    }

    /** POST /api/v2/otp/register  (ApiRequest<String> -> ApiResponse<OtpRegistrationData>) */
    private ApiResponse<Void> otpAlertV2(ApiRequest<SimpleId> request) {
        return requestV2("/api/v2/otp/alert", HttpMethod.POST, request,
                new ParameterizedTypeReference<ApiResponse<Void>>() {});
    }

    /** POST /api/v2/otp/verify  (ApiRequest<String> -> ApiResponse<Void>) */
    private ApiResponse<Void> otpVerifyExistenceV2(ApiRequest<String> request) {
        return requestV2("/api/v2/otp/verify", HttpMethod.POST, request,
                new ParameterizedTypeReference<ApiResponse<Void>>() {});
    }

    /** POST /api/v2/otp/authenticate  (ApiRequest<OtpAuthRequest> -> ApiResponse<OtpAuthenticationData>) */
    private ApiResponse<OtpAuthenticationData> otpAuthenticateV2(ApiRequest<OtpAuthRequest> request) {
        return requestV2("/api/v2/otp/authenticate", HttpMethod.POST, request,
                new ParameterizedTypeReference<ApiResponse<OtpAuthenticationData>>() {});
    }

    /** POST /api/v2/otp/delete  (ApiRequest<String> -> ApiResponse<Void> */
    private ApiResponse<Void> otpDeleteV2(ApiRequest<String> request) {
        return requestV2("/api/v2/otp/delete", HttpMethod.POST, request,
                new ParameterizedTypeReference<ApiResponse<Void>>() {});
    }

// ---------- v2 FIDO2 ----------
    /** POST /api/v2/fido2/register  (ApiRequest<SimpleId> -> ApiResponse<?>) */
    private ApiResponse<Void> fido2RegisterV2(ApiRequest<SimpleId> request) {
        return requestV2("/api/v2/fido2/register", HttpMethod.POST, request,
                new ParameterizedTypeReference<ApiResponse<Void>>() {});
    }

    /** POST /api/v2/fido2/verify  (ApiRequest<String> -> ApiResponse<Void>) */
    private ApiResponse<Void> fido2VerifyExistenceV2(ApiRequest<String> request) {
        return requestV2("/api/v2/fido2/verify", HttpMethod.POST, request,
                new ParameterizedTypeReference<ApiResponse<Void>>() {});
    }

    /** POST /api/v2/fido2/authenticate  (ApiRequest<SimpleId> -> ApiResponse<?>) */
    private ApiResponse<String> fido2AuthenticateV2(ApiRequest<SimpleId> request) {
        return requestV2("/api/v2/fido2/authenticate", HttpMethod.POST, request,
                new ParameterizedTypeReference<ApiResponse<String>>() {});
    }

    /** POST /api/v2/fido2/delete  (ApiRequest<String> -> ApiResponse<Void> */
    private ApiResponse<Void> fido2DeleteV2(ApiRequest<String> request) {
        return requestV2("/api/v2/fido2/delete", HttpMethod.POST, request,
                new ParameterizedTypeReference<ApiResponse<Void>>() {});
    }

    private ApiResponse<String> passkeyEnroll(ApiRequest<String> request) {
        return requestV2("/api/v2/fido2/register/browser", HttpMethod.POST, request,
                new ParameterizedTypeReference<ApiResponse<String>>() {});
    }

    private ApiResponse<Void> passkeyCheck(ApiRequest<UsernameTx> request) {
        return requestV2("/api/v2/fido2/verify/browser", HttpMethod.POST, request,
                new ParameterizedTypeReference<ApiResponse<Void>>() {});
    }

    private ApiResponse<Void> passkeyDelete(ApiRequest<UsernameTx> request) {
        return requestV2("/api/v2/fido2/delete/browser", HttpMethod.POST, request,
                new ParameterizedTypeReference<ApiResponse<Void>>() {});
    }

    private ApiResponse<AuthSessionToken> authSessionBootstrap(ApiRequest<AuthSessionContext> request) {
        return requestV2("/api/v2/session/bootstrap", HttpMethod.POST, request,
                new ParameterizedTypeReference<ApiResponse<AuthSessionToken>>() {});
    }

    /*
    # /api/v2/admin/user
    public ResponseEntity<ApiResponse<MfaAccount>> registerAccount(@RequestBody ApiRequest<SimpleId> request)

    # /api/v2/otp/register
    public ResponseEntity<ApiResponse<OtpRegistrationData>> register(@RequestBody ApiRequest<String> request)

    # /api/v2/otp/verify
    public ResponseEntity<ApiResponse<Void>> verifyExistence(@RequestBody ApiRequest<String> request)

    # /api/v2/otp/authenticate
    public ResponseEntity<ApiResponse<OtpAuthenticationData>> authenticate(@RequestBody ApiRequest<OtpAuthRequest> request)

    # /api/v2/fido2/register
    public ResponseEntity<ApiResponse<?>> notifyDeviceOnRegister(@RequestBody ApiRequest<SimpleId> request)

    # /api/v2/fido2/verify
    public ResponseEntity<ApiResponse<Void>> verifyExistence(@RequestBody ApiRequest<String> request)

    # /api/v2/fido2/authenticate
    public ResponseEntity<ApiResponse<?>> notifyDeviceOnAuthenticate(@RequestBody ApiRequest<SimpleId> request)

     */
}
