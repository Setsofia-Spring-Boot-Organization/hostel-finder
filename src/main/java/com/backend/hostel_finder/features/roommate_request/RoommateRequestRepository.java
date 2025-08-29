package com.backend.hostel_finder.features.roommate_request;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RoommateRequestRepository extends MongoRepository<RoommateRequestDocument, String> {

    List<RoommateRequestDocument> findByOpenTrue(); // fetch all open requests

    List<RoommateRequestDocument> findByRoomIdAndOpenTrue(String roomId); // open requests for a room
}
