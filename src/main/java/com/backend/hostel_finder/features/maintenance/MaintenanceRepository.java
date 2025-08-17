package com.backend.hostel_finder.features.maintenance;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MaintenanceRepository extends MongoRepository<MaintenanceDocument, String> {
    List<MaintenanceDocument> findByStudentId(String studentId);
}
