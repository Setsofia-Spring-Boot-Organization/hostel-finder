package com.backend.hostel_finder.features.maintenance;

import com.backend.hostel_finder.features.maintenance.enums.MaintenanceStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Document(collection = "maintenance_requests")
public class MaintenanceDocument {

    @Id
    private String id;

    @Field("student_id")
    private String studentId;

    @Field("room_id")
    private String roomId;

    @Field("description")
    private String description;

    @Field("status")
    private MaintenanceStatus status = MaintenanceStatus.PENDING;

    @Field("created_at")
    private LocalDateTime createdAt;

    @Field("updated_at")
    private LocalDateTime updatedAt;
}
