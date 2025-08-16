package com.backend.hostel_finder.features.auth.dtos;

public record LoginDto(
        String email,
        String password
) { }
