package com.backend.hostel_finder.features.booking;


import com.backend.hostel_finder.features.booking.daos.BookingResponse;
import com.backend.hostel_finder.features.booking.dtos.CreateBookingRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {

    BookingResponse createBooking(CreateBookingRequest request);

    List<BookingResponse> getAllBookings();

    List<BookingResponse> getBookingsByStudentId(String studentId);

    BookingResponse getBookingById(String id);

    BookingResponse updateBookingStatus(String id, BookingDocument.BookingStatus status);

    BookingResponse updatePaymentStatus(String id, BookingDocument.PaymentStatus status);

    void cancelBooking(String id);

    List<BookingResponse> getBookingsByStatus(BookingDocument.BookingStatus status);

    boolean isRoomAvailable(String roomId, LocalDateTime checkIn, LocalDateTime checkOut);
}
