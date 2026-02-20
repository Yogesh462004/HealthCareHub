package nimblix.in.HealthCareHub.repository;

import nimblix.in.HealthCareHub.model.LabResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabResultRepository extends JpaRepository<LabResult, Long> {

    // Correct for simple Long FK
    List<LabResult> findByPatientId(Long patientId);

    List<LabResult> findByPatientIdAndStatus(Long patientId, String status);

    List<LabResult> findByDoctorId(Long doctorId);
}