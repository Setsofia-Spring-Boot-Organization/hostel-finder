package com.backend.hostel_finder.features.rooms;

import com.backend.hostel_finder.core.Response;
import com.backend.hostel_finder.features.rooms.dtos.NewRoomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public ResponseEntity<Response<?>> createRoom(NewRoomDto dto) {
        RoomDocument room = new RoomDocument();
        room.setNumber(dto.number());
        room.setType(dto.type());
        room.setHostelName(dto.hostelName());
        room.setCapacity(dto.capacity());
        room.setPrice(dto.price());
        room.setAmenities(dto.amenities());
        room.setStatus(dto.status());
        room.setDescription(dto.description());
        room.setImages(dto.images());
        room.setGender(dto.gender());

        RoomDocument created = roomRepository.save(room);

        return Response.<RoomDocument>builder()
                .data(created)
                .build()
                .responseEntity(HttpStatus.CREATED, "Room created successfully");
    }

    @Override
    public ResponseEntity<Response<?>> updateRoom(String id, NewRoomDto dto) {
        RoomDocument updated = roomRepository.findById(id)
                .map(existing -> {
                    existing.setNumber(dto.number());
                    existing.setType(dto.type());
                    existing.setCapacity(dto.capacity());
                    existing.setPrice(dto.price());
                    existing.setAmenities(dto.amenities());
                    existing.setStatus(dto.status());
                    existing.setDescription(dto.description());
                    existing.setImages(dto.images());
                    existing.setGender(dto.gender());
                    return roomRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + id));

        return Response.<RoomDocument>builder()
                .data(updated)
                .build()
                .responseEntity(HttpStatus.OK, "Room updated successfully");
    }

    @Override
    public ResponseEntity<Response<?>> deleteRoom(String id) {
        roomRepository.deleteById(id);
        return Response.builder()
                .build()
                .responseEntity(HttpStatus.NO_CONTENT, "Room deleted successfully");
    }

    @Override
    public ResponseEntity<Response<?>> getRoomById(String id) {
        RoomDocument room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + id));

        return Response.<RoomDocument>builder()
                .data(room)
                .build()
                .responseEntity(HttpStatus.OK, "Room fetched successfully");
    }

    @Override
    public ResponseEntity<Response<?>> getAllRooms() {
        List<RoomDocument> rooms = roomRepository.findAll();

        return Response.<List<RoomDocument>>builder()
                .data(rooms)
                .build()
                .responseEntity(HttpStatus.OK, "Rooms fetched successfully");
    }
}
