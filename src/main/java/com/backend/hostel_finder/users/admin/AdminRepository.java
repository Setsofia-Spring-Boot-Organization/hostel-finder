package com.backend.hostel_finder.users.admin;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AdminRepository extends MongoRepository<AdminDocument, String> {
    boolean existsByEmail(String email);
    Optional<AdminDocument> findByEmail(String email);
}
