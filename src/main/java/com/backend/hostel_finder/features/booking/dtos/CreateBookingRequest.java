package com.backend.hostel_finder.features.booking.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;


@Data
public class CreateBookingRequest {

    private String studentId;

    private String roomId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private String checkInDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private String checkOutDate;

    private Double amountPaid;

    private String paymentReference;
}