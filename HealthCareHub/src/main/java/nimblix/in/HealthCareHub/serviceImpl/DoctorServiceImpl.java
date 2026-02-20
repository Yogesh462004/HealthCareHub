package nimblix.in.HealthCareHub.serviceImpl;

import lombok.RequiredArgsConstructor;
import nimblix.in.HealthCareHub.model.Doctor;
import nimblix.in.HealthCareHub.repository.DoctorRepository;
import nimblix.in.HealthCareHub.request.DoctorRegistrationRequest;
import nimblix.in.HealthCareHub.service.DoctorService;
import org.springframework.stereotype.Service;

import nimblix.in.HealthCareHub.model.Hospital;
import nimblix.in.HealthCareHub.model.Specialization;
import nimblix.in.HealthCareHub.repository.HospitalRepository;
import nimblix.in.HealthCareHub.repository.SpecializationRepository;


@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;
    private final SpecializationRepository specializationRepository;

    @Override
    public String RegisterDoctor(DoctorRegistrationRequest request) {

        Hospital hospital = hospitalRepository.findByName(request.getHospitalName())
                .orElseThrow(() -> new RuntimeException("Hospital not found"));

        Specialization specialization = specializationRepository.findByName(request.getSpecialization())
                .orElseThrow(() -> new RuntimeException("Specialization not found"));

        // Create Doctor
        Doctor doctor = new Doctor();

        doctor.setName(request.getDoctorName());
        doctor.setPhone(request.getPhoneNo());
        doctor.setEmailId(request.getDoctorEmail());
        doctor.setPassword(request.getPassword());

        // Set IDs (Correct way based on your entity)
        doctor.setHospitalId(hospital.getId());
        doctor.setSpecializationId(specialization.getId());

        doctorRepository.save(doctor);

        return "Doctor Registration Successful";
    }
}

