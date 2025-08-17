package com.backend.hostel_finder.features.users.student;

import com.backend.hostel_finder.core.Response;
import com.backend.hostel_finder.features.users.roles.UserRoles;
import com.backend.hostel_finder.features.users.student.dtos.NewStudentDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class StudentServiceImpl implements StudentService {


    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentServiceImpl(StudentRepository studentRepository, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }



    @Override
    public ResponseEntity<Response<?>> createNewStudent(NewStudentDto newStudentDto) {

        if (studentRepository.existsByEmail(newStudentDto.email())) {
            return Response.builder()
                    .build()
                    .responseEntity(HttpStatus.BAD_REQUEST, "Student already exist!");
        }

        try {

            StudentDocument student = new StudentDocument();
            student.setStudentId(newStudentDto.studentId());
            student.setFullName(newStudentDto.fullName());
            student.setEmail(newStudentDto.email());
            student.setPhoneNumber(newStudentDto.phoneNumber());
            student.setDob(LocalDate.parse(newStudentDto.dob()));
            student.setGender(newStudentDto.gender());
            student.setFaculty(newStudentDto.faculty());
            student.setGuardianContact(newStudentDto.guardianContact());
            student.setAddress(newStudentDto.address());
            student.setPassword(passwordEncoder.encode(newStudentDto.password()));
            student.setRole(UserRoles.STUDENT);
            student.setCreatedAt(LocalDateTime.now());
            student.setUpdatedAt(LocalDateTime.now());

            StudentDocument newStudent = studentRepository.save(student);

            return Response.builder()
                    .data(newStudent)
                    .build()
                    .responseEntity(HttpStatus.CREATED, "Student created successfully!");

        } catch (Exception e) {

            throw new RuntimeException(e);

        }
    }


    @Override
    public ResponseEntity<Response<?>> findStudentByEmail(String email) {
        return studentRepository.findByEmail(email)
                .map(student -> Response.builder()
                        .data(student)
                        .build()
                        .responseEntity(HttpStatus.OK, "Student found"))
                .orElseGet(() -> Response.builder()
                        .build()
                        .responseEntity(HttpStatus.NOT_FOUND, "Student not found"));
    }

}
