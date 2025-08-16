package com.backend.hostel_finder.users.admin.dtos;

public record NewAdminDto(

        String adminId,
        String fullName,
        String email,
        String phoneNumber,
        String password

) { }
