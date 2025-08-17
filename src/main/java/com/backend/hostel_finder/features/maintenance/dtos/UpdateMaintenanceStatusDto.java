package com.backend.hostel_finder.features.maintenance.dtos;


import com.backend.hostel_finder.features.maintenance.enums.MaintenanceStatus;

public record UpdateMaintenanceStatusDto(
        MaintenanceStatus status
) { }
