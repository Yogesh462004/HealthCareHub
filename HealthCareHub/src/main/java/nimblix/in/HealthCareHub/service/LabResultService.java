package nimblix.in.HealthCareHub.service;

import nimblix.in.HealthCareHub.dto.LabResultResponseDTO;

import java.util.List;

public interface LabResultService {

    // Task 186 - Get lab result by ID
    LabResultResponseDTO getLabResultById(Long resultId);

    // Get all lab results for a patient
    List<LabResultResponseDTO> getLabResultsByPatient(Long patientId);
}