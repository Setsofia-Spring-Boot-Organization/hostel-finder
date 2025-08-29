package com.backend.hostel_finder.features.roommate_request;

import com.backend.hostel_finder.core.Response;
import com.backend.hostel_finder.features.roommate_request.dtos.CreateRoommateRequestDto;
import com.backend.hostel_finder.features.roommate_request.dtos.JoinRoommateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hf/api/v1/roommate-requests")
@RequiredArgsConstructor
public class RoommateRequestController {

    private final RoommateRequestService roommateRequestService;

    /**
     * Create a new roommate request
     */
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('STUDENT')")
    public ResponseEntity<Response<?>> createRequest(@RequestBody CreateRoommateRequestDto dto) {
        try {
            RoommateRequestDocument request = roommateRequestService.createRequest(dto.getStudentId(), dto.getRoomId(), dto.getPreferences());
            return Response.<RoommateRequestDocument>builder()
                    .data(request)
                    .build()
                    .responseEntity(HttpStatus.CREATED, "Roommate request created successfully");
        } catch (Exception e) {
            return Response.builder()
                    .build()
                    .responseEntity(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * List all open roommate requests
     */
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('STUDENT')")
    public ResponseEntity<Response<?>> listOpenRequests() {
        List<RoommateRequestDocument> requests = roommateRequestService.listOpenRequests();
        return Response.<List<RoommateRequestDocument>>builder()
                .data(requests)
                .build()
                .responseEntity(HttpStatus.OK, "Open roommate requests retrieved successfully");
    }

    /**
     * Join a roommate request
     */
    @PostMapping("/{id}/join")
    @PreAuthorize("hasRole('USER') or hasRole('STUDENT')")
    public ResponseEntity<Response<?>> joinRequest(@PathVariable String id, @RequestBody JoinRoommateRequestDto dto) {
        try {
            RoommateRequestDocument request = roommateRequestService.joinRequest(
                    id, dto.getStudentId(), dto.getCheckInDate(), dto.getCheckOutDate(), dto.getAmountPaid()
            );
            return Response.<RoommateRequestDocument>builder()
                    .data(request)
                    .build()
                    .responseEntity(HttpStatus.OK, "Joined roommate request successfully");
        } catch (Exception e) {
            return Response.builder()
                    .build()
                    .responseEntity(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


    /**
     * Cancel (reverse) a roommate request
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('STUDENT')")
    public ResponseEntity<Response<?>> cancelRequest(@PathVariable String id, @RequestParam String studentId) {
        try {
            roommateRequestService.cancelRequest(id, studentId);
            return Response.builder()
                    .build()
                    .responseEntity(HttpStatus.OK, "Roommate request cancelled successfully");
        } catch (Exception e) {
            return Response.builder()
                    .build()
                    .responseEntity(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


    /**
     * Get all roommate requests created by a student
     */
    @GetMapping("/created/{studentId}")
    @PreAuthorize("hasRole('USER') or hasRole('STUDENT')")
    public ResponseEntity<Response<?>> getRequestsCreatedByStudent(@PathVariable String studentId) {
        List<RoommateRequestDocument> requests = roommateRequestService.getRequestsCreatedByStudent(studentId);
        return Response.<List<RoommateRequestDocument>>builder()
                .data(requests)
                .build()
                .responseEntity(HttpStatus.OK, "Requests created by student retrieved successfully");
    }

    /**
     * Get all roommate requests joined by a student
     */
    @GetMapping("/joined/{studentId}")
    @PreAuthorize("hasRole('USER') or hasRole('STUDENT')")
    public ResponseEntity<Response<?>> getRequestsJoinedByStudent(@PathVariable String studentId) {
        List<RoommateRequestDocument> requests = roommateRequestService.getRequestsJoinedByStudent(studentId);
        return Response.<List<RoommateRequestDocument>>builder()
                .data(requests)
                .build()
                .responseEntity(HttpStatus.OK, "Requests joined by student retrieved successfully");
    }

}
