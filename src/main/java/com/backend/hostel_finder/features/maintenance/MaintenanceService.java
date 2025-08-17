package com.backend.hostel_finder.features.maintenance;

import com.backend.hostel_finder.features.maintenance.dtos.NewMaintenanceRequestDto;
import com.backend.hostel_finder.features.maintenance.dtos.UpdateMaintenanceStatusDto;

import java.util.List;

public interface MaintenanceService {

    // Student actions
    MaintenanceDocument requestMaintenance(String studentId, NewMaintenanceRequestDto dto);
    List<MaintenanceDocument> getStudentMaintenanceRequests(String studentId);

    // Admin actions
    List<MaintenanceDocument> getAllMaintenanceRequests();
    MaintenanceDocument updateMaintenanceStatus(String requestId, UpdateMaintenanceStatusDto dto);
}
