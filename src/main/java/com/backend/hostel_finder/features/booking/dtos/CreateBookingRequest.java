package com.backend.hostel_finder.features.booking.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateBookingRequest {

    private String studentId;

    private String roomId;

    private String hostelName;

    private String roomType;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime checkInDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime checkOutDate;

    private Double amountPaid;

    private String paymentReference;
}