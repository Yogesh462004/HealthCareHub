package nimblix.in.HealthCareHub.repository;

import nimblix.in.HealthCareHub.model.LabResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabResultRepository extends JpaRepository<LabResult, Long> {

    //  Get all lab results for a patient
    // Uses Patient.id (Long) instead of patientId
    List<LabResult> findByPatient_Id(Long patientId);

    // Get lab results by patient and status
    // Status is String: "PENDING", "COMPLETED", "NORMAL", "ABNORMAL"
    List<LabResult> findByPatient_IdAndStatus(Long patientId, String status);

    // Get lab results by doctor
    // Uses Doctor.id (Long)
    List<LabResult> findByDoctor_Id(Long doctorId);

    // Get all results for a specific test name
    List<LabResult> findByPatient_IdAndTestName(Long patientId, String testName);
}