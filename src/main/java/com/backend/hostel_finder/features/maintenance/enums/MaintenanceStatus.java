package com.backend.hostel_finder.features.maintenance.enums;

public enum MaintenanceStatus {
    PENDING,     // Student just created request, waiting for admin
    IN_PROGRESS, // Admin has acknowledged and working on it
    COMPLETED,   // Work has been completed
    REJECTED     // Request denied (e.g., invalid reason)
}
