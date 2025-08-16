package com.backend.hostel_finder.features.rooms;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomRepository extends MongoRepository<RoomDocument, String> { }
