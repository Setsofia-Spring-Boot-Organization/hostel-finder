package com.backend.hostel_finder.features.users.student;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface StudentRepository extends MongoRepository<StudentDocument, String> {
    boolean existsByEmail(String email);
    Optional<StudentDocument> findByEmail(String email);
}
