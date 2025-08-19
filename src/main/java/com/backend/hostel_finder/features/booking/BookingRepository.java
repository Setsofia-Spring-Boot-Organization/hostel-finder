package com.backend.hostel_finder.features.booking;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends MongoRepository<BookingDocument, String> {

    List<BookingDocument> findByStudentId(String studentId);

    List<BookingDocument> findByBookingStatus(BookingDocument.BookingStatus status);

    List<BookingDocument> findByPaymentStatus(BookingDocument.PaymentStatus status);

    @Query("{ 'student_id': ?0, 'room_id': ?1, 'booking_status': { $in: ['PENDING', 'CONFIRMED'] } }")
    List<BookingDocument> findActiveBookingForStudentAndRoom(String studentId, String roomId);

    @Query("{ 'room_id': ?0, 'booking_status': { $in: ['CONFIRMED'] } }")
    List<BookingDocument> findConfirmedBookingsByRoomId(String roomId);
}