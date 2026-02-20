package nimblix.in.HealthCareHub.serviceImpl;

import nimblix.in.HealthCareHub.model.*;
import nimblix.in.HealthCareHub.repository.*;
import nimblix.in.HealthCareHub.request.AdmitPatientRequestDTO;
import nimblix.in.HealthCareHub.response.AdmitPatientResponseDTO;
import nimblix.in.HealthCareHub.exception.DoctorNotFoundException;
import nimblix.in.HealthCareHub.exception.PatientNotFoundException;
import nimblix.in.HealthCareHub.exception.RoomNotFoundException;
import nimblix.in.HealthCareHub.service.AdmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdmissionServiceImpl implements AdmissionService {

    @Autowired
    private AdmissionRepository admissionRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private SpecializationRepository specializationRepository;

    @Override
    @Transactional
    public AdmitPatientResponseDTO admitPatient(AdmitPatientRequestDTO request) {

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new PatientNotFoundException(
                        "Patient not found with id: " + request.getPatientId()));

        boolean isPatientAlreadyAdmitted = admissionRepository
                .existsByPatientIdAndStatus(request.getPatientId(), "ADMITTED");
        if (isPatientAlreadyAdmitted) {
            throw new IllegalArgumentException(
                    "Patient is already admitted. Cannot admit the same patient twice.");
        }

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new DoctorNotFoundException(
                        "Doctor not found with id: " + request.getDoctorId()));

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RoomNotFoundException(
                        "Room not found with id: " + request.getRoomId()));

        boolean isRoomOccupied = admissionRepository
                .existsByRoomIdAndStatus(request.getRoomId(), "ADMITTED");
        if (isRoomOccupied) {
            throw new IllegalArgumentException(
                    "Room " + room.getRoomNumber() + " is already occupied. " +
                            "Please select another room.");
        }

        Admission admission = Admission.builder()
                .patientId(request.getPatientId())
                .doctorId(request.getDoctorId())
                .roomId(request.getRoomId())
                .admissionReason(request.getAdmissionReason())
                .symptoms(request.getSymptoms())
                .initialDiagnosis(request.getInitialDiagnosis())
                .status("ADMITTED")
                .build();

        Admission savedAdmission = admissionRepository.save(admission);

        room.setStatus(Room.RoomStatus.OCCUPIED);
        roomRepository.save(room);

        return mapToResponse(savedAdmission, patient, doctor, room);
    }

    private AdmitPatientResponseDTO mapToResponse(Admission admission,
                                                  Patient patient,
                                                  Doctor doctor,
                                                  Room room) {
        AdmitPatientResponseDTO response = new AdmitPatientResponseDTO();

        // Admission info
        response.setAdmissionId(admission.getAdmissionId());
        response.setAdmissionDate(admission.getAdmissionDate());
        response.setAdmissionReason(admission.getAdmissionReason());
        response.setSymptoms(admission.getSymptoms());
        response.setInitialDiagnosis(admission.getInitialDiagnosis());
        response.setStatus(admission.getStatus());

        // Patient info
        response.setPatientId(patient.getId());
        response.setPatientName(patient.getName());
        response.setPatientPhone(patient.getPhone());

        // Doctor info
        response.setDoctorId(doctor.getId());
        response.setDoctorName("Dr. " + doctor.getName());


        Specialization specialization = specializationRepository
                .findById(doctor.getSpecializationId())
                .orElse(null);
        if (specialization != null) {
            response.setDoctorSpecialization(specialization.getName());
        } else {
            response.setDoctorSpecialization("General");
        }

        // Room info
        response.setRoomId(room.getRoomId());
        response.setRoomNumber(room.getRoomNumber());
        response.setRoomType(room.getRoomType());

        return response;
    }
}