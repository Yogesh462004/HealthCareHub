package nimblix.in.HealthCareHub.serviceImpl;

import nimblix.in.HealthCareHub.response.LabResultResponseDTO;
import nimblix.in.HealthCareHub.exception.LabResultNotFoundException;
import nimblix.in.HealthCareHub.exception.PatientNotFoundException;
import nimblix.in.HealthCareHub.model.Doctor;
import nimblix.in.HealthCareHub.model.LabResult;
import nimblix.in.HealthCareHub.model.Patient;
import nimblix.in.HealthCareHub.repository.LabResultRepository;
import nimblix.in.HealthCareHub.repository.PatientRepository;
import nimblix.in.HealthCareHub.service.LabResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LabResultServiceImpl implements LabResultService {

    @Autowired
    private LabResultRepository labResultRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public LabResultResponseDTO getLabResultById(Long resultId) {


        LabResult labResult = labResultRepository.findById(resultId)
                .orElseThrow(() -> new LabResultNotFoundException(
                        "Lab result not found with id: " + resultId));

        return mapToResponse(labResult);
    }

    @Override
    public List<LabResultResponseDTO> getLabResultsByPatient(Long patientId) {


        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException(
                        "Patient not found with id: " + patientId));

        List<LabResult> results = labResultRepository.findByPatient_Id(patientId);

        /* List<LabResultResponseDTO> responseList = new ArrayList<>();

        for (LabResult labResult : results) {
            LabResultResponseDTO request = mapToResponse(labResult);
            responseList.add(request);
        }

        return responseList;*/

        return results.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());



    }

    private LabResultResponseDTO mapToResponse(LabResult labResult) {
        LabResultResponseDTO response = new LabResultResponseDTO();

        // Lab result info
        response.setResultId(labResult.getResultId());
        response.setTestName(labResult.getTestName());
        response.setTestCategory(labResult.getTestCategory());
        response.setResult(labResult.getResult());
        response.setReferenceRange(labResult.getReferenceRange());
        response.setUnit(labResult.getUnit());
        response.setStatus(labResult.getStatus());
        response.setRemarks(labResult.getRemarks());
        response.setTestedAt(labResult.getTestedAt());

        // Patient info - uses Patient.id and Patient.name
        Patient patient = labResult.getPatient();
        response.setPatientId(patient.getId());
        response.setPatientName(patient.getName());
        response.setPatientPhone(patient.getPhone());

        // Doctor info - uses Doctor.id, Doctor.name, Doctor.specialization.name
        Doctor doctor = labResult.getDoctor();
        response.setDoctorId(doctor.getId());
        response.setDoctorName(doctor.getName());

        // Get specialization name if exists
        if (doctor.getSpecialization() != null) {
            response.setDoctorSpecialization(doctor.getSpecialization().getName());
        } else {
            response.setDoctorSpecialization("General");
        }

        return response;
    }
}