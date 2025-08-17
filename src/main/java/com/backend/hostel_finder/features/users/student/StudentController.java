package com.backend.hostel_finder.features.users.student;

import com.backend.hostel_finder.core.Response;
import com.backend.hostel_finder.features.users.student.dtos.NewStudentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "hf/api/v1/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @PostMapping(path = "/new")
    public ResponseEntity<Response<?>> createNewStudent(
            @RequestBody NewStudentDto newStudentDto
    ) {
        return studentService.createNewStudent(newStudentDto);
    }


    @GetMapping(path = "/find")
    public ResponseEntity<Response<?>> findStudentByEmail(@RequestParam String email) {
        return studentService.findStudentByEmail(email);
    }

}
