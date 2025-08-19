package com.backend.hostel_finder.features.users.student;

import com.backend.hostel_finder.features.booking.BookingDocument;
import com.backend.hostel_finder.features.users.roles.UserRoles;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "students")
public class StudentDocument {

    @MongoId
    private String id;

    @Field("student_id")
    private String studentId;

    @Field("full_name")
    private String fullName;

    @Field("email")
    private String email;

    @Field("phone_number")
    private String phoneNumber;

    @Field("dob")
    private LocalDate dob;

    @Field("gender")
    private String gender;

    @Field("faculty")
    private String faculty;

    @Field("guardian_contact")
    private String guardianContact;

    @Field("address")
    private String address;

    @Field("password")
    private String password;

    @Field("role")
    private UserRoles role;

    @Field("bookings")
    private List<BookingDocument> bookings;

    @Field("created_at")
    private LocalDateTime createdAt;

    @Field("updated_at")
    private LocalDateTime updatedAt;
}
