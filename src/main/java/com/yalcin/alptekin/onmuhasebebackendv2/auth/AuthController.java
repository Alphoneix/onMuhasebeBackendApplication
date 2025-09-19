package com.yalcin.alptekin.onmuhasebebackendv2.auth;

import com.yalcin.alptekin.onmuhasebebackendv2.user.User;
import com.yalcin.alptekin.onmuhasebebackendv2.user.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        Optional<User> userOpt = userService.findByEmail(request.getEmail());
        if (userOpt.isPresent() && userOpt.get().isActive()
                && userService.checkPassword(request.getPassword(), userOpt.get().getPassword())) {

            // Kullanıcıyı tarayıcıda saklamak için cookie oluşturuluyor
            ResponseCookie cookie = ResponseCookie.from("userId", userOpt.get().getId().toString())
                    .httpOnly(true)
                    .path("/")
                    .maxAge(60 * 60 * 24 * 365 * 10) // 10 yıl, pratikte süresiz
                    .build();
            response.addHeader("Set-Cookie", cookie.toString());
            return ResponseEntity.ok(userOpt.get());
        }
        return ResponseEntity.status(401).body("Giriş başarısız");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("userId", "")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .build();
        response.addHeader("Set-Cookie", cookie.toString());
        return ResponseEntity.ok("Çıkış yapıldı");
    }

    @Data
    public static class LoginRequest {
        private String email;
        private String password;
    }
}