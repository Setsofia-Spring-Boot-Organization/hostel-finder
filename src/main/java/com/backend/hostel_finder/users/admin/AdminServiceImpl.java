package com.backend.hostel_finder.users.admin;

import com.backend.hostel_finder.core.Response;
import com.backend.hostel_finder.users.admin.dtos.NewAdminDto;
import com.backend.hostel_finder.users.roles.UserRoles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminServiceImpl(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<Response<?>> createNewAdmin(NewAdminDto newAdminDto) {

        if (adminRepository.existsByEmail(newAdminDto.email())) {
            return Response.builder()
                    .build()
                    .responseEntity(HttpStatus.BAD_REQUEST, "Admin already exists!");
        }

        try {

            AdminDocument admin = new AdminDocument();
            admin.setAdminId(newAdminDto.adminId());
            admin.setFullName(newAdminDto.fullName());
            admin.setEmail(newAdminDto.email());
            admin.setPhoneNumber(newAdminDto.phoneNumber());
            admin.setRole(UserRoles .ADMIN);

            admin.setPassword(passwordEncoder.encode(newAdminDto.password()));

            admin.setCreatedAt(LocalDateTime.now());
            admin.setUpdatedAt(LocalDateTime.now());

            AdminDocument newAdmin = adminRepository.save(admin);


            return Response.builder()
                    .data(newAdmin)
                    .build()
                    .responseEntity(HttpStatus.CREATED, "Admin created successfully!");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<Response<?>> findAdminByEmail(String email) {
        return adminRepository.findByEmail(email)
                .map(admin -> Response.builder()
                        .data(admin)
                        .build()
                        .responseEntity(HttpStatus.OK, "Admin found"))
                .orElseGet(() -> Response.builder()
                        .build()
                        .responseEntity(HttpStatus.NOT_FOUND, "Admin not found"));
    }
}
