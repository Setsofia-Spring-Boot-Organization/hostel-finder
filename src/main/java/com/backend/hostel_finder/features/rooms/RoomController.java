package com.backend.hostel_finder.features.rooms;

import com.backend.hostel_finder.core.Response;
import com.backend.hostel_finder.features.rooms.dtos.NewRoomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/hf/api/v1/rooms")
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<Response<?>> createRoom(@RequestBody NewRoomDto dto) {
        return roomService.createRoom(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateRoom(@PathVariable String id, @RequestBody NewRoomDto dto) {
        return roomService.updateRoom(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteRoom(@PathVariable String id) {
        return roomService.deleteRoom(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getRoomById(@PathVariable String id) {
        return roomService.getRoomById(id);
    }

    @GetMapping
    public ResponseEntity<Response<?>> getAllRooms() {
        return roomService.getAllRooms();
    }
}
