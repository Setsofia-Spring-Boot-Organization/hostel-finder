package com.backend.hostel_finder.features.rooms;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@Document(collection = "rooms")
public class RoomDocument {
    @MongoId
    private String id;
    private String number;
    private String hostelName;
    private String type;
    private int capacity;
    private double price;
    private List<String> amenities;
    private String status;
    private String description;
    private List<String> images;
    private String gender;
}
