package com.yunbaek.barogodelivery.auth.ui;

import com.yunbaek.barogodelivery.auth.application.AuthService;
import com.yunbaek.barogodelivery.auth.domain.LoginMember;
import com.yunbaek.barogodelivery.auth.dto.TokenRequest;
import com.yunbaek.barogodelivery.auth.dto.TokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login/token")
    public ResponseEntity<TokenResponse> login(@RequestBody TokenRequest request) {
        TokenResponse token = authService.login(request);
        return ResponseEntity.ok().body(token);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test(@AuthenticationPrincipal(expression = "loginMember") LoginMember loginMember) {
        return ResponseEntity.ok().body(loginMember.loginId());
    }
}
