package nimblix.in.HealthCareHub.repository;

import nimblix.in.HealthCareHub.model.Admission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdmissionRepository extends JpaRepository<Admission, Long> {

    // Check if patient already has an active admission
    // Uses simple Long patientId field
    boolean existsByPatientIdAndStatus(Long patientId, String status);

    // Check if room already has an active admission
    // Uses simple Long roomId field
    boolean existsByRoomIdAndStatus(Long roomId, String status);

    // Get admission history by patient
    List<Admission> findByPatientId(Long patientId);

    // Get admission history by patient and status
    List<Admission> findByPatientIdAndStatus(Long patientId, String status);
}