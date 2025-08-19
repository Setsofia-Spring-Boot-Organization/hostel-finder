package com.backend.hostel_finder.features.booking.daos;

import com.backend.hostel_finder.features.booking.BookingDocument;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookingResponse {
    private String id;
    private String roomId;
    private String roomNumber;       // New
    private String hostelName;
    private String roomType;

    // Customer details
    private String customerName;     // New
    private String customerEmail;    // New

    // Booking details
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private Double totalAmount;      // Renamed from amountPaid
    private BookingDocument.PaymentStatus paymentStatus;
    private BookingDocument.BookingStatus bookingStatus;
    private LocalDateTime bookingDate;  // New (use createdAt)

    // Room details
    private List<String> amenities;  // New
    private String gender;           // New (room/student gender)
    private int capacity;            // New
    private String specialRequests;  // New (optional)

    // Metadata
    private String paymentReference;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
