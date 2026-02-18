package nimblix.in.HealthCareHub.repository;

import nimblix.in.HealthCareHub.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    // Find patient by email (via User entity)
    Optional<Patient> findByUser_Email(String email);

    // Find patient by hospital
    java.util.List<Patient> findByHospital_Id(Long hospitalId);
}