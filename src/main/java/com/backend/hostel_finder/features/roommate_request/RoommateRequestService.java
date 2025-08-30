package com.backend.hostel_finder.features.roommate_request;

import java.util.List;

public interface RoommateRequestService {

    RoommateRequestDocument createRequest(String studentId, String roomId, String preferences);

    List<RoommateRequestDocument> listOpenRequests();

    RoommateRequestDocument joinRequest(String requestId, String studentId, String checkInDate, String checkOutDate, Double amountPaid);

    void cancelRequest(String requestId, String studentId);

    List<RoommateRequestDocument> getRequestsCreatedByStudent(String studentId);

    List<RoommateRequestDocument> getRequestsJoinedByStudent(String studentId);

    List<RoommateRequestDocument> getRequestsByRoomId(String roomId);

}

