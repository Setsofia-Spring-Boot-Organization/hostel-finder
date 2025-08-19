package com.backend.hostel_finder.features.booking.daos;

import com.backend.hostel_finder.features.booking.BookingDocument;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingResponse {
    private String id;
    private String studentId;
    private String roomId;
    private String hostelName;
    private String roomType;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private BookingDocument.BookingStatus bookingStatus;
    private BookingDocument.PaymentStatus paymentStatus;
    private Double amountPaid;
    private String paymentReference;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}