package com.backend.hostel_finder.features.booking;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "bookings")
public class BookingDocument {

    @MongoId
    private String id;

    @Field("room_id")
    private String roomId;

    @Field("student_id")
    private String studentId;

    @Field("room_number")
    private String roomNumber;

    @Field("hostel_name")
    private String hostelName;

    @Field("room_type")
    private String roomType;

    @Field("customer_name")
    private String customerName;

    @Field("customer_email")
    private String customerEmail;

    @Field("check_in_date")
    private LocalDateTime checkInDate;

    @Field("check_out_date")
    private LocalDateTime checkOutDate;

    @Field("total_amount")
    private Double totalAmount;

    @Field("payment_status")
    private PaymentStatus paymentStatus;

    @Field("booking_status")
    private BookingStatus bookingStatus;

    @Field("created_at")
    private LocalDateTime createdAt;

    @Field("updated_at")
    private LocalDateTime updatedAt;

    @Field("amenities")
    private List<String> amenities;

    @Field("gender")
    private String gender;

    @Field("capacity")
    private Integer capacity;

    @Field("special_requests")
    private String specialRequests;

    public enum BookingStatus {
        PENDING, CONFIRMED, CANCELLED, COMPLETED
    }

    public enum PaymentStatus {
        UNPAID, PAID, PARTIALLY_PAID, REFUNDED
    }
}
