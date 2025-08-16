package com.backend.hostel_finder.features.users.student;

import com.backend.hostel_finder.core.Response;
import com.backend.hostel_finder.features.users.student.dtos.NewStudentDto;
import org.springframework.http.ResponseEntity;

public interface StudentService {
    ResponseEntity<Response<?>> createNewStudent(NewStudentDto newStudentDto);
    ResponseEntity<Response<?>> findStudentByEmail(String email);
}
