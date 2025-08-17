package com.backend.hostel_finder.auth;

import com.backend.hostel_finder.auth.dtos.LoginDto;
import com.backend.hostel_finder.core.Response;
import com.backend.hostel_finder.security.JwtUtil;
import com.backend.hostel_finder.users.admin.AdminRepository;
import com.backend.hostel_finder.users.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("hf/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AdminRepository adminRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<Response<?>> login(
            @RequestBody LoginDto loginDto
    ) {
        // Try Student
        var student = studentRepository.findByEmail(loginDto.email());
        if (student.isPresent() && passwordEncoder.matches(loginDto.password(), student.get().getPassword())) {
            String token = jwtUtil.generateToken(loginDto.email(),"STUDENT");
            return Response.builder()
                    .data(token)
                    .build().responseEntity(HttpStatus.OK, "Login successful!");
        }

        // Try Admin
        var admin = adminRepository.findByEmail(loginDto.email());
        if (admin.isPresent() && passwordEncoder.matches(loginDto.password(), admin.get().getPassword())) {
            String token = jwtUtil.generateToken(loginDto.email(), String.valueOf(admin.get().getRole()));
            return Response.builder()
                    .data(token)
                    .build().responseEntity(HttpStatus.OK, "Login successful!");
        }

        return Response.builder().build().responseEntity(HttpStatus.UNAUTHORIZED, "Invalid credentials");
    }
}
