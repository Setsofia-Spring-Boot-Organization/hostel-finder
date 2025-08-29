package com.backend.hostel_finder.features.roommate_request.dtos;

import lombok.Data;

@Data
public class JoinRoommateRequestDto {
    private String studentId;
    private String checkInDate;
    private String checkOutDate;
    private Double amountPaid;
}