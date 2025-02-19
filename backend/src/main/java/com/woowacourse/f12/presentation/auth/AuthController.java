package com.woowacourse.f12.presentation.auth;

import com.woowacourse.f12.application.auth.AuthService;
import com.woowacourse.f12.dto.response.auth.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthService authService;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestParam final String code) {
        return ResponseEntity.ok(authService.login(code));
    }
}
