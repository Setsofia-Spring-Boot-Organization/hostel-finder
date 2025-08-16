package com.backend.hostel_finder.features.maintenance;

import com.backend.hostel_finder.features.maintenance.dtos.NewMaintenanceRequestDto;
import com.backend.hostel_finder.features.maintenance.dtos.UpdateMaintenanceStatusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hf/api/maintenance")
@RequiredArgsConstructor
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    @PostMapping("/{studentId}/request")
    public ResponseEntity<MaintenanceDocument> requestMaintenance(
            @PathVariable String studentId,
            @RequestBody NewMaintenanceRequestDto dto
    ) {
        return ResponseEntity.ok(maintenanceService.requestMaintenance(studentId, dto));
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<List<MaintenanceDocument>> getStudentRequests(
            @PathVariable String studentId
    ) {
        return ResponseEntity.ok(maintenanceService.getStudentMaintenanceRequests(studentId));
    }

    @GetMapping
    public ResponseEntity<List<MaintenanceDocument>> getAllRequests() {
        return ResponseEntity.ok(maintenanceService.getAllMaintenanceRequests());
    }

    @PutMapping("/{requestId}/status")
    public ResponseEntity<MaintenanceDocument> updateStatus(
            @PathVariable String requestId,
            @RequestBody UpdateMaintenanceStatusDto dto
    ) {
        return ResponseEntity.ok(maintenanceService.updateMaintenanceStatus(requestId, dto));
    }
}
