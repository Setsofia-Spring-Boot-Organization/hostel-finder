package com.backend.hostel_finder.features.booking;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "bookings")
public class BookingDocument {

    @MongoId
    private String id;

    @Field("student_id")
    private String studentId;

    @Field("room_id")
    private String roomId;

    @Field("hostel_name")
    private String hostelName;

    @Field("room_type")
    private String roomType;

    @Field("check_in_date")
    private LocalDateTime checkInDate;

    @Field("check_out_date")
    private LocalDateTime checkOutDate;

    @Field("booking_status")
    private BookingStatus bookingStatus;

    @Field("payment_status")
    private PaymentStatus paymentStatus;

    @Field("amount_paid")
    private Double amountPaid;

    @Field("payment_reference")
    private String paymentReference;

    @Field("created_at")
    private LocalDateTime createdAt;

    @Field("updated_at")
    private LocalDateTime updatedAt;

    public enum BookingStatus {
        PENDING, CONFIRMED, CANCELLED, COMPLETED
    }

    public enum PaymentStatus {
        UNPAID, PAID, PARTIALLY_PAID, REFUNDED
    }
}