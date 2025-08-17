package com.backend.hostel_finder.features.rooms;

import com.backend.hostel_finder.core.Response;
import com.backend.hostel_finder.features.rooms.dtos.NewRoomDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

public interface RoomService {
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Response<?>> createRoom(NewRoomDto dto);

    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Response<?>> updateRoom(String id, NewRoomDto dto);

    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Response<?>> deleteRoom(String id);

    ResponseEntity<Response<?>> getRoomById(String id);

    ResponseEntity<Response<?>> getAllRooms();
}
