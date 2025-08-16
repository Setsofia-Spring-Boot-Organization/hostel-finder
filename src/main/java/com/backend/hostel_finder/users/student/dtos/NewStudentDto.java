package com.backend.hostel_finder.users.student.dtos;

public record NewStudentDto(

        String studentId,
        String fullName,
        String email,
        String phoneNumber,
        String dob,
        String gender,
        String faculty,
        String guardianContact,
        String address,
        String password

) { }
