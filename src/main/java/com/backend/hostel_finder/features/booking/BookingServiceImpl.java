package com.backend.hostel_finder.features.booking;

import com.backend.hostel_finder.features.booking.daos.BookingResponse;
import com.backend.hostel_finder.features.booking.dtos.CreateBookingRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    @Override
    public BookingResponse createBooking(CreateBookingRequest request) {
        log.info("Creating booking for student: {}", request.getStudentId());

        // Validate dates
        if (request.getCheckOutDate().isBefore(request.getCheckInDate())) {
            throw new IllegalArgumentException("Check-out date cannot be before check-in date");
        }

        // Check room availability
        if (!isRoomAvailable(request.getRoomId(), request.getCheckInDate(), request.getCheckOutDate())) {
            throw new IllegalArgumentException("Room is not available for the selected dates");
        }

        // Create booking document
        BookingDocument booking = new BookingDocument();
        booking.setStudentId(request.getStudentId());
        booking.setRoomId(request.getRoomId());
        booking.setHostelName(request.getHostelName());
        booking.setRoomType(request.getRoomType());
        booking.setCheckInDate(request.getCheckInDate());
        booking.setCheckOutDate(request.getCheckOutDate());
        booking.setBookingStatus(BookingDocument.BookingStatus.PENDING);
        booking.setPaymentStatus(request.getAmountPaid() != null && request.getAmountPaid() > 0
                ? BookingDocument.PaymentStatus.PAID
                : BookingDocument.PaymentStatus.UNPAID);
        booking.setAmountPaid(request.getAmountPaid());
        booking.setPaymentReference(request.getPaymentReference());
        booking.setCreatedAt(LocalDateTime.now());
        booking.setUpdatedAt(LocalDateTime.now());

        BookingDocument savedBooking = bookingRepository.save(booking);
        log.info("Booking created successfully with ID: {}", savedBooking.getId());

        return convertToResponse(savedBooking);
    }

    @Override
    public List<BookingResponse> getAllBookings() {
        log.info("Fetching all bookings");
        List<BookingDocument> bookings = bookingRepository.findAll();
        return bookings.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponse> getBookingsByStudentId(String studentId) {
        log.info("Fetching bookings for student: {}", studentId);
        List<BookingDocument> bookings = bookingRepository.findByStudentId(studentId);
        return bookings.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BookingResponse getBookingById(String id) {
        log.info("Fetching booking with ID: {}", id);
        BookingDocument booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + id));
        return convertToResponse(booking);
    }

    @Override
    public BookingResponse updateBookingStatus(String id, BookingDocument.BookingStatus status) {
        log.info("Updating booking status for ID: {} to {}", id, status);
        BookingDocument booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + id));

        booking.setBookingStatus(status);
        booking.setUpdatedAt(LocalDateTime.now());

        BookingDocument updatedBooking = bookingRepository.save(booking);
        return convertToResponse(updatedBooking);
    }

    @Override
    public BookingResponse updatePaymentStatus(String id, BookingDocument.PaymentStatus status) {
        log.info("Updating payment status for ID: {} to {}", id, status);
        BookingDocument booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + id));

        booking.setPaymentStatus(status);
        booking.setUpdatedAt(LocalDateTime.now());

        BookingDocument updatedBooking = bookingRepository.save(booking);
        return convertToResponse(updatedBooking);
    }

    @Override
    public void cancelBooking(String id) {
        log.info("Cancelling booking with ID: {}", id);
        updateBookingStatus(id, BookingDocument.BookingStatus.CANCELLED);
    }

    @Override
    public List<BookingResponse> getBookingsByStatus(BookingDocument.BookingStatus status) {
        log.info("Fetching bookings with status: {}", status);
        List<BookingDocument> bookings = bookingRepository.findByBookingStatus(status);
        return bookings.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isRoomAvailable(String roomId, LocalDateTime checkIn, LocalDateTime checkOut) {
        List<BookingDocument> conflictingBookings = bookingRepository.findConfirmedBookingsByRoomId(roomId);

        return conflictingBookings.stream().noneMatch(booking ->
                checkIn.isBefore(booking.getCheckOutDate()) && checkOut.isAfter(booking.getCheckInDate())
        );
    }

    private BookingResponse convertToResponse(BookingDocument booking) {
        BookingResponse response = new BookingResponse();
        response.setId(booking.getId());
        response.setStudentId(booking.getStudentId());
        response.setRoomId(booking.getRoomId());
        response.setHostelName(booking.getHostelName());
        response.setRoomType(booking.getRoomType());
        response.setCheckInDate(booking.getCheckInDate());
        response.setCheckOutDate(booking.getCheckOutDate());
        response.setBookingStatus(booking.getBookingStatus());
        response.setPaymentStatus(booking.getPaymentStatus());
        response.setAmountPaid(booking.getAmountPaid());
        response.setPaymentReference(booking.getPaymentReference());
        response.setCreatedAt(booking.getCreatedAt());
        response.setUpdatedAt(booking.getUpdatedAt());
        return response;
    }
}