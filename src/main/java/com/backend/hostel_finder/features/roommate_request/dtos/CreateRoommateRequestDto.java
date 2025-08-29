package com.backend.hostel_finder.features.roommate_request.dtos;

import lombok.Data;

@Data
public class CreateRoommateRequestDto {
    private String studentId;
    private String roomId;
    private String preferences;
}