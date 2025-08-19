package com.backend.hostel_finder.features.booking;

import com.backend.hostel_finder.features.booking.daos.BookingResponse;
import com.backend.hostel_finder.features.booking.dtos.CreateBookingRequest;
import com.backend.hostel_finder.features.rooms.RoomDocument;
import com.backend.hostel_finder.features.rooms.RoomRepository;
import com.backend.hostel_finder.features.users.student.StudentDocument;
import com.backend.hostel_finder.features.users.student.StudentRepository;
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
    private final RoomRepository roomRepository;
    private final StudentRepository studentRepository;

    @Override
    public BookingResponse createBooking(CreateBookingRequest request) {
        log.info("Creating booking for student: {}", request.getStudentId());

        // Validate dates
        LocalDateTime checkInDate = LocalDateTime.parse(request.getCheckInDate());
        LocalDateTime checkOutDate = LocalDateTime.parse(request.getCheckOutDate());

        if (checkOutDate.isBefore(checkInDate)) {
            throw new IllegalArgumentException("Check-out date cannot be before check-in date");
        }

        // Check room availability
        if (!isRoomAvailable(request.getRoomId(), checkInDate, checkOutDate)) {
            throw new IllegalArgumentException("Room is not available for the selected dates");
        }

        // Fetch room
        RoomDocument roomDocument = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("Room not found with ID: " + request.getRoomId()));

        // Fetch student
        StudentDocument student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + request.getStudentId()));

        // Create booking document
        BookingDocument booking = new BookingDocument();
        booking.setStudentId(student.getId()); // Internal MongoDB ID
        booking.setRoomId(roomDocument.getId());
        booking.setRoomType(roomDocument.getType());
        booking.setHostelName(roomDocument.getHostelName());
        booking.setCheckInDate(checkInDate);
        booking.setCheckOutDate(checkOutDate);
        booking.setBookingStatus(BookingDocument.BookingStatus.PENDING);
        booking.setPaymentStatus(BookingDocument.PaymentStatus.UNPAID);
        booking.setTotalAmount(request.getAmountPaid());
        booking.setCreatedAt(LocalDateTime.now());
        booking.setUpdatedAt(LocalDateTime.now());

        BookingDocument savedBooking = bookingRepository.save(booking);

        // Optionally: update student’s bookings list
        List<BookingDocument> studentBookings = student.getBookings();
        if (studentBookings != null) {
            studentBookings.add(savedBooking);
        } else {
            student.setBookings(List.of(savedBooking));
        }
        student.setUpdatedAt(LocalDateTime.now());
        studentRepository.save(student);

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
        response.setRoomId(booking.getRoomId());
        response.setHostelName(booking.getHostelName());
        response.setRoomType(booking.getRoomType());

        // Load Room Details
        RoomDocument room = roomRepository.findById(booking.getRoomId()).orElse(null);
        if (room != null) {
            response.setRoomNumber(room.getNumber());
            response.setAmenities(room.getAmenities());
            response.setGender(room.getGender());
            response.setCapacity(room.getCapacity());
        }

        // Load Student Details
        StudentDocument student = studentRepository.findById(booking.getStudentId()).orElse(null);
        if (student != null) {
            response.setCustomerName(student.getFullName());
            response.setCustomerEmail(student.getEmail());
        }

        // Booking details
        response.setCheckInDate(booking.getCheckInDate());
        response.setCheckOutDate(booking.getCheckOutDate());
        response.setBookingStatus(booking.getBookingStatus());
        response.setPaymentStatus(booking.getPaymentStatus());
        response.setTotalAmount(booking.getTotalAmount());
        response.setSpecialRequests(booking.getSpecialRequests());

        // Metadata
        response.setBookingDate(booking.getCreatedAt()); // same as createdAt
        response.setCreatedAt(booking.getCreatedAt());
        response.setUpdatedAt(booking.getUpdatedAt());

        return response;
    }

}