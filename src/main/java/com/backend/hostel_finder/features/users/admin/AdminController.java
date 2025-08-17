package com.backend.hostel_finder.features.users.admin;

import com.backend.hostel_finder.core.Response;
import com.backend.hostel_finder.features.users.admin.dtos.NewAdminDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "hf/api/v1/admins")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @PostMapping(path = "/new")
    public ResponseEntity<Response<?>> createNewAdmin(
            @RequestBody NewAdminDto newAdminDto
    ) {
        return adminService.createNewAdmin(newAdminDto);
    }


    @GetMapping(path = "/find")
    public ResponseEntity<Response<?>> findAdminByEmail(@RequestParam String email) {
        return adminService.findAdminByEmail(email);
    }

}
