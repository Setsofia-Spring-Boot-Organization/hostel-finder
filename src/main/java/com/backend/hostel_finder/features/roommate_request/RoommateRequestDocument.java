package com.backend.hostel_finder.features.roommate_request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "roommate_requests")
public class RoommateRequestDocument {

    @MongoId
    private String id;

    @Field("requester_id")
    private String requesterId;  // student who created the request

    @Field("room_id")
    private String roomId;

    @Field("preferences")
    private String preferences; // e.g. "quiet roommate", "same faculty"

    @Field("open")
    private boolean open = true; // request open or closed

    @Field("joined_student_ids")
    private List<String> joinedStudentIds; // students who accepted

    @Field("created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Field("updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
}
