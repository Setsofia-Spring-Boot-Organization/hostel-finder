package com.backend.hostel_finder.features.maintenance;

import com.backend.hostel_finder.features.maintenance.dtos.NewMaintenanceRequestDto;
import com.backend.hostel_finder.features.maintenance.dtos.UpdateMaintenanceStatusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;

    @Override
    public MaintenanceDocument requestMaintenance(String studentId, NewMaintenanceRequestDto dto) {
        MaintenanceDocument request = new MaintenanceDocument();
        request.setStudentId(studentId);
        request.setRoomId(dto.roomId());
        request.setDescription(dto.description());
        request.setCreatedAt(LocalDateTime.now());
        request.setUpdatedAt(LocalDateTime.now());
        // Status defaults to PENDING, no need to set it manually
        return maintenanceRepository.save(request);
    }

    @Override
    public List<MaintenanceDocument> getStudentMaintenanceRequests(String studentId) {
        return maintenanceRepository.findByStudentId(studentId);
    }

    @Override
    public List<MaintenanceDocument> getAllMaintenanceRequests() {
        return maintenanceRepository.findAll();
    }

    @Override
    public MaintenanceDocument updateMaintenanceStatus(String requestId, UpdateMaintenanceStatusDto dto) {
        MaintenanceDocument request = maintenanceRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Maintenance request not found"));
        request.setStatus(dto.status());
        request.setUpdatedAt(LocalDateTime.now());
        return maintenanceRepository.save(request);
    }
}
