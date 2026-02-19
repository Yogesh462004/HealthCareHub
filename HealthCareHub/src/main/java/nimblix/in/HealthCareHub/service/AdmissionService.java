package nimblix.in.HealthCareHub.service;

import nimblix.in.HealthCareHub.request.AdmitPatientRequestDTO;
import nimblix.in.HealthCareHub.response.AdmitPatientResponseDTO;

public interface AdmissionService {

    AdmitPatientResponseDTO admitPatient(AdmitPatientRequestDTO request);
}