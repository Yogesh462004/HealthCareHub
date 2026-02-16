package nimblix.in.HealthCareHub.repository;

import nimblix.in.HealthCareHub.model.Admission;
import nimblix.in.HealthCareHub.model.Admission.AdmissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdmissionRepository extends JpaRepository<Admission, Long> {

    // Check if Patient already has an active admission
    boolean existsByPatient_PatientIdAndStatus(Long patientId, AdmissionStatus status);

    // Check if room is already occupied
    boolean existsByRoom_RoomIdAndStatus(Long roomId, AdmissionStatus status);

    // Get all admissions for a Patient
    List<Admission> findByPatient_PatientId(Long patientId);
}