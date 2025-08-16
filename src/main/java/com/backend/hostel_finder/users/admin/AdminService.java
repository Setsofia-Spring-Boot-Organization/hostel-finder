package com.backend.hostel_finder.users.admin;

import com.backend.hostel_finder.core.Response;
import com.backend.hostel_finder.users.admin.dtos.NewAdminDto;
import org.springframework.http.ResponseEntity;

public interface AdminService {
    ResponseEntity<Response<?>> createNewAdmin(NewAdminDto newAdminDto);

    ResponseEntity<Response<?>> findAdminByEmail(String email);
}
