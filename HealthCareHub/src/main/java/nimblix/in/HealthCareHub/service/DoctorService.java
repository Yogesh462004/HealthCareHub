package nimblix.in.HealthCareHub.service;

import nimblix.in.HealthCareHub.request.DoctorRegistrationRequest;
import nimblix.in.HealthCareHub.response.DoctorProfileResponse;
import org.springframework.http.ResponseEntity;

public interface DoctorService {
    String registerDoctor(DoctorRegistrationRequest request);
    public DoctorProfileResponse getDoctorProfile(Long doctorId);
    ResponseEntity<?> getDoctorDetails(Long doctorId, Long hospitalId);
}
