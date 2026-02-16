package nimblix.in.HealthCareHub.serviceImpl;

import nimblix.in.HealthCareHub.dto.AdmitPatientRequestDTO;
import nimblix.in.HealthCareHub.dto.AdmitPatientResponseDTO;
import nimblix.in.HealthCareHub.model.Admission;
import nimblix.in.HealthCareHub.model.Admission.AdmissionStatus;
import nimblix.in.HealthCareHub.model.Doctor;
import nimblix.in.HealthCareHub.model.Patient;
import nimblix.in.HealthCareHub.model.Room;
import nimblix.in.HealthCareHub.exception.DoctorNotFoundException;
import nimblix.in.HealthCareHub.exception.PatientNotFoundException;
import nimblix.in.HealthCareHub.exception.RoomNotFoundException;
import nimblix.in.HealthCareHub.repository.AdmissionRepository;
import nimblix.in.HealthCareHub.repository.DoctorRepository;
import nimblix.in.HealthCareHub.repository.PatientRepository;
import nimblix.in.HealthCareHub.repository.RoomRepository;
import nimblix.in.HealthCareHub.service.AdmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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

    @Override
    @Transactional
    public AdmitPatientResponseDTO admitPatient(AdmitPatientRequestDTO request) {

        // 1. Validate Patient exists
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with id: " + request.getPatientId()));

        // 2. Check Patient is not already admitted
        boolean alreadyAdmitted = admissionRepository
                .existsByPatient_PatientIdAndStatus(request.getPatientId(), AdmissionStatus.ADMITTED);
        if (alreadyAdmitted) {
            throw new IllegalArgumentException("Patient is already admitted. Please discharge first.");
        }

        // 3. Validate doctor exists
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with id: " + request.getDoctorId()));

        // 4. Validate room exists and is not occupied
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RoomNotFoundException("Room not found with id: " + request.getRoomId()));

        boolean roomOccupied = admissionRepository
                .existsByRoom_RoomIdAndStatus(request.getRoomId(), AdmissionStatus.ADMITTED);
        if (roomOccupied) {
            throw new IllegalArgumentException("Room " + room.getRoomNumber() + " is already occupied.");
        }

        // 5. Create and save admission
        Admission admission = new Admission();
        admission.setPatient(patient);
        admission.setDoctor(doctor);
        admission.setRoom(room);
        admission.setAdmissionDate(
                request.getAdmissionDate() != null ? request.getAdmissionDate() : LocalDateTime.now()
        );
        admission.setAdmissionReason(request.getAdmissionReason());
        admission.setSymptoms(request.getSymptoms());
        admission.setInitialDiagnosis(request.getInitialDiagnosis());
        admission.setStatus(AdmissionStatus.ADMITTED);

        Admission saved = admissionRepository.save(admission);

        // 6. Update room status to occupied
        room.setStatus(Room.RoomStatus.OCCUPIED);
        roomRepository.save(room);

        // 7. Build and return response
        return mapToResponse(saved, patient, doctor, room);
    }

    private AdmitPatientResponseDTO mapToResponse(Admission admission, Patient patient, Doctor doctor, Room room) {
        AdmitPatientResponseDTO response = new AdmitPatientResponseDTO();
        response.setAdmissionId(admission.getAdmissionId());
        response.setPatientId(patient.getPatientId());
        response.setPatientName(patient.getFirstName() + " " + patient.getLastName());
        response.setDoctorId(doctor.getDoctorId());
        response.setDoctorName("Dr. " + doctor.getFirstName() + " " + doctor.getLastName());
        response.setRoomId(room.getRoomId());
        response.setRoomNumber(room.getRoomNumber());
        response.setRoomType(room.getRoomType());
        response.setAdmissionDate(admission.getAdmissionDate());
        response.setAdmissionReason(admission.getAdmissionReason());
        response.setSymptoms(admission.getSymptoms());
        response.setInitialDiagnosis(admission.getInitialDiagnosis());
        response.setStatus(admission.getStatus());
        response.setCreatedAt(admission.getCreatedAt());
        return response;
    }
}