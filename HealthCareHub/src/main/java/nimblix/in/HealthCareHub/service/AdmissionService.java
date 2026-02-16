package nimblix.in.HealthCareHub.service;

import nimblix.in.HealthCareHub.dto.AdmitPatientRequestDTO;
import nimblix.in.HealthCareHub.dto.AdmitPatientResponseDTO;

public interface AdmissionService {

    AdmitPatientResponseDTO admitPatient(AdmitPatientRequestDTO request);
}