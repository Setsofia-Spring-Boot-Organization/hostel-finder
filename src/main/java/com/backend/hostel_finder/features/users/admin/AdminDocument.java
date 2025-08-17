package com.backend.hostel_finder.features.users.admin;

import com.backend.hostel_finder.features.users.roles.UserRoles;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Document(collection = "admins")
public class AdminDocument {

    @Id
    private String id;

    @Field("admin_id")
    private String adminId;

    @Field("full_name")
    private String fullName;

    @Field("email")
    private String email;

    @Field("phone_number")
    private String phoneNumber;

    @Field("role")
    private UserRoles role;

    @Field("password")
    private String password;

    @Field("created_at")
    private LocalDateTime createdAt;

    @Field("updated_at")
    private LocalDateTime updatedAt;
}
