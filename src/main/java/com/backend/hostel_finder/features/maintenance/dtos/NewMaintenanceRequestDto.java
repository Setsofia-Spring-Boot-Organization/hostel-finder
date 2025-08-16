package com.backend.hostel_finder.features.maintenance.dtos;

public record NewMaintenanceRequestDto(
        String roomId,
        String description
) { }
