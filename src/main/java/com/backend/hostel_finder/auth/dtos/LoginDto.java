package com.backend.hostel_finder.auth.dtos;

public record LoginDto(
        String email,
        String password
) { }
