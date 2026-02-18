package nimblix.in.HealthCareHub.repository;

import nimblix.in.HealthCareHub.model.Admission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdmissionRepository extends JpaRepository<Admission, Long> {

    // Check if patient already has an active admission
    // Uses Patient.id (not patientId) and status as String
    boolean existsByPatient_IdAndStatus(Long patientId, Admission.AdmissionStatus status);

    // Check if room already has an active admission
    boolean existsByRoom_RoomIdAndStatus(Long roomId, Admission.AdmissionStatus status);


    // Uses Patient.id
    List<Admission> findByPatient_Id(Long patientId);

    //Get admission history by patient and status
    List<Admission> findByPatient_IdAndStatus(Long patientId, Admission.AdmissionStatus status);
}