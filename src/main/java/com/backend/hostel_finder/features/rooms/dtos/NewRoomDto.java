package com.backend.hostel_finder.features.rooms.dtos;

import java.util.List;

public record NewRoomDto(
        String number,
        String type,
        String hostelName,
        int capacity,
        double price,
        List<String>amenities,
        String status,
        String description,
        List<String> images,
        String gender
) { }
