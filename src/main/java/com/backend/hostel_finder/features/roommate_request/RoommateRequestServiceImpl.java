package com.backend.hostel_finder.features.roommate_request;

import com.backend.hostel_finder.features.booking.BookingDocument;
import com.backend.hostel_finder.features.booking.BookingRepository;
import com.backend.hostel_finder.features.rooms.RoomDocument;
import com.backend.hostel_finder.features.rooms.RoomRepository;
import com.backend.hostel_finder.features.users.student.StudentDocument;
import com.backend.hostel_finder.features.users.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoommateRequestServiceImpl implements RoommateRequestService {

    private final RoommateRequestRepository roommateRequestRepository;
    private final RoomRepository roomRepository;
    private final StudentRepository studentRepository;
    private final BookingRepository bookingRepository;

    @Override
    public RoommateRequestDocument createRequest(String studentId, String roomId, String preferences) {
        RoomDocument room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        if (room.getCapacity() <= 1) {
            throw new RuntimeException("Room does not require roommates");
        }

        RoommateRequestDocument request = new RoommateRequestDocument();
        request.setRequesterId(studentId);
        request.setRoomId(roomId);
        request.setPreferences(preferences);
        request.setOpen(true);
        request.setJoinedStudentIds(new ArrayList<>());
        request.setCreatedAt(LocalDateTime.now());
        request.setUpdatedAt(LocalDateTime.now());

        return roommateRequestRepository.save(request);
    }

    @Override
    public List<RoommateRequestDocument> listOpenRequests() {
        return roommateRequestRepository.findByOpenTrue();
    }

    @Override
    public List<RoommateRequestDocument> getRequestsByRoomId(String roomId) {
        return roommateRequestRepository.findByRoomIdAndOpenTrue(roomId);
    }

    @Override
    @Transactional
    public RoommateRequestDocument joinRequest(String requestId, String studentId, String checkInDate, String checkOutDate, Double amountPaid) {
        RoommateRequestDocument request = roommateRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (!request.isOpen()) {
            throw new RuntimeException("Request is closed");
        }

        if (request.getJoinedStudentIds() != null && request.getJoinedStudentIds().contains(studentId)) {
            throw new RuntimeException("You have already joined this request");
        }

        RoomDocument room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        int totalOccupants = 1; // requester
        if (request.getJoinedStudentIds() != null) {
            totalOccupants += request.getJoinedStudentIds().size();
        }

        if (totalOccupants >= room.getCapacity()) {
            throw new RuntimeException("Room already full");
        }

        assert request.getJoinedStudentIds() != null;
        request.getJoinedStudentIds().add(studentId);
        request.setUpdatedAt(LocalDateTime.now());

        // ✅ If room full after this join → create bookings for all involved
        if (totalOccupants + 1 >= room.getCapacity()) {
            request.setOpen(false);

            List<String> allStudentIds = new ArrayList<>();
            allStudentIds.add(request.getRequesterId());
            allStudentIds.addAll(request.getJoinedStudentIds());

            for (String sId : allStudentIds) {
                StudentDocument student = studentRepository.findById(sId)
                        .orElseThrow(() -> new RuntimeException("Student not found"));

                BookingDocument booking = new BookingDocument();
                booking.setStudentId(student.getId());
                booking.setRoomId(room.getId());
                booking.setRoomType(room.getType());
                booking.setHostelName(room.getHostelName());
                booking.setCheckInDate(LocalDateTime.parse(checkInDate));
                booking.setCheckOutDate(LocalDateTime.parse(checkOutDate));
                booking.setBookingStatus(BookingDocument.BookingStatus.PENDING);
                booking.setPaymentStatus(BookingDocument.PaymentStatus.UNPAID);
                booking.setTotalAmount(amountPaid);
                booking.setCreatedAt(LocalDateTime.now());
                booking.setUpdatedAt(LocalDateTime.now());

                BookingDocument savedBooking = bookingRepository.save(booking);

                // also update student’s bookings list
                List<BookingDocument> studentBookings = student.getBookings();
                if (studentBookings != null) {
                    studentBookings.add(savedBooking);
                } else {
                    student.setBookings(List.of(savedBooking));
                }
                student.setUpdatedAt(LocalDateTime.now());
                studentRepository.save(student);
            }
        }

        return roommateRequestRepository.save(request);
    }


    @Override
    @Transactional
    public void cancelRequest(String requestId, String studentId) {
        RoommateRequestDocument request = roommateRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (!request.getRequesterId().equals(studentId)) {
            throw new RuntimeException("Only the requester can cancel this request");
        }

        if (!request.isOpen()) {
            throw new RuntimeException("Cannot cancel a closed request");
        }

        roommateRequestRepository.delete(request);
    }


    @Override
    public List<RoommateRequestDocument> getRequestsCreatedByStudent(String studentId) {
        return roommateRequestRepository.findByRequesterId(studentId);
    }

    @Override
    public List<RoommateRequestDocument> getRequestsJoinedByStudent(String studentId) {
        return roommateRequestRepository.findByJoinedStudentIdsContaining(studentId);
    }

}
