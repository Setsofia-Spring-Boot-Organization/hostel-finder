package com.backend.hostel_finder.core.services;

import com.backend.hostel_finder.features.users.admin.AdminRepository;
import com.backend.hostel_finder.features.users.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // 1. Try Student
        return studentRepository.findByEmail(email)
                .map(student -> User.builder()
                        .username(student.getEmail())
                        .password(student.getPassword())
                        .roles(String.valueOf(student.getRole()))
                        .build())
                // 2. Try Admin
                .or(() -> adminRepository.findByEmail(email)
                        .map(admin -> User.builder()
                                .username(admin.getEmail())
                                .password(admin.getPassword())
                                .roles(String.valueOf(admin.getRole()))
                                .build()))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }
}
