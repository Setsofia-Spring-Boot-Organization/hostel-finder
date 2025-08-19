package com.backend.hostel_finder.features.booking;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends MongoRepository<BookingDocument, String> {

    List<BookingDocument> findByStudentId(String studentId);

    List<BookingDocument> findByBookingStatus(BookingDocument.BookingStatus status);

    List<BookingDocument> findByPaymentStatus(BookingDocument.PaymentStatus status);

    @Query("{ 'student_id': ?0, 'booking_status': { $in: ['PENDING', 'CONFIRMED'] } }")
    List<BookingDocument> findActiveBookingsByStudentId(String studentId);

    @Query("{ 'room_id': ?0, 'booking_status': { $in: ['CONFIRMED'] } }")
    List<BookingDocument> findConfirmedBookingsByRoomId(String roomId);

    Optional<BookingDocument> findByPaymentReference(String paymentReference);
}