package com.backend.hostel_finder.features.booking;

import com.backend.hostel_finder.core.Response;
import com.backend.hostel_finder.features.booking.daos.BookingResponse;
import com.backend.hostel_finder.features.booking.dtos.CreateBookingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hf/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('STUDENT')")
    public ResponseEntity<Response<?>> createBooking(@RequestBody CreateBookingRequest request) {
        try {
            BookingResponse booking = bookingService.createBooking(request);
            return Response.<BookingResponse>builder()
                    .data(booking)
                    .build()
                    .responseEntity(HttpStatus.CREATED, "Booking created successfully");
        } catch (IllegalArgumentException e) {
            return Response.builder()
                    .build()
                    .responseEntity(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return Response.builder()
                    .build()
                    .responseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create booking");
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response<?>> getAllBookings() {
        try {
            List<BookingResponse> bookings = bookingService.getAllBookings();
            return Response.<List<BookingResponse>>builder()
                    .data(bookings)
                    .build()
                    .responseEntity(HttpStatus.OK, "Bookings retrieved successfully");
        } catch (Exception e) {
            return Response.builder()
                    .build()
                    .responseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve bookings");
        }
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasRole('USER') or hasRole('STUDENT') or hasRole('ADMIN')")
    public ResponseEntity<Response<?>> getBookingsByStudentId(@PathVariable String studentId) {
        try {
            List<BookingResponse> bookings = bookingService.getBookingsByStudentId(studentId);
            return Response.<List<BookingResponse>>builder()
                    .data(bookings)
                    .build()
                    .responseEntity(HttpStatus.OK, "Student bookings retrieved successfully");
        } catch (Exception e) {
            return Response.builder()
                    .build()
                    .responseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve student bookings");
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('STUDENT') or hasRole('ADMIN')")
    public ResponseEntity<Response<?>> getBookingById(@PathVariable String id) {
        try {
            BookingResponse booking = bookingService.getBookingById(id);
            return Response.<BookingResponse>builder()
                    .data(booking)
                    .build()
                    .responseEntity(HttpStatus.OK, "Booking retrieved successfully");
        } catch (RuntimeException e) {
            return Response.builder()
                    .build()
                    .responseEntity(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            return Response.builder()
                    .build()
                    .responseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve booking");
        }
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response<?>> updateBookingStatus(
            @PathVariable String id,
            @RequestParam BookingDocument.BookingStatus status) {
        try {
            BookingResponse booking = bookingService.updateBookingStatus(id, status);
            return Response.<BookingResponse>builder()
                    .data(booking)
                    .build()
                    .responseEntity(HttpStatus.OK, "Booking status updated successfully");
        } catch (RuntimeException e) {
            return Response.builder()
                    .build()
                    .responseEntity(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            return Response.builder()
                    .build()
                    .responseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update booking status");
        }
    }

    @PutMapping("/{id}/payment-status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response<?>> updatePaymentStatus(
            @PathVariable String id,
            @RequestParam BookingDocument.PaymentStatus status) {
        try {
            BookingResponse booking = bookingService.updatePaymentStatus(id, status);
            return Response.<BookingResponse>builder()
                    .data(booking)
                    .build()
                    .responseEntity(HttpStatus.OK, "Payment status updated successfully");
        } catch (RuntimeException e) {
            return Response.builder()
                    .build()
                    .responseEntity(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            return Response.builder()
                    .build()
                    .responseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update payment status");
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response<?>> cancelBooking(@PathVariable String id) {
        try {
            bookingService.cancelBooking(id);
            return Response.builder()
                    .build()
                    .responseEntity(HttpStatus.OK, "Booking cancelled successfully");
        } catch (RuntimeException e) {
            return Response.builder()
                    .build()
                    .responseEntity(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            return Response.builder()
                    .build()
                    .responseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to cancel booking");
        }
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response<?>> getBookingsByStatus(@PathVariable BookingDocument.BookingStatus status) {
        try {
            List<BookingResponse> bookings = bookingService.getBookingsByStatus(status);
            return Response.<List<BookingResponse>>builder()
                    .data(bookings)
                    .build()
                    .responseEntity(HttpStatus.OK, "Bookings retrieved successfully");
        } catch (Exception e) {
            return Response.builder()
                    .build()
                    .responseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve bookings by status");
        }
    }
}