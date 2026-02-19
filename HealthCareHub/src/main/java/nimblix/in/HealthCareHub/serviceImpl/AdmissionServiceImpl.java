package nimblix.in.HealthCareHub.serviceImpl;

import nimblix.in.HealthCareHub.request.AdmitPatientRequestDTO;
import nimblix.in.HealthCareHub.response.AdmitPatientResponseDTO;
import nimblix.in.HealthCareHub.exception.DoctorNotFoundException;
import nimblix.in.HealthCareHub.exception.PatientNotFoundException;
import nimblix.in.HealthCareHub.exception.RoomNotFoundException;
import nimblix.in.HealthCareHub.model.Admission;
import nimblix.in.HealthCareHub.model.Doctor;
import nimblix.in.HealthCareHub.model.Patient;
import nimblix.in.HealthCareHub.model.Room;
import nimblix.in.HealthCareHub.repository.AdmissionRepository;
import nimblix.in.HealthCareHub.repository.DoctorRepository;
import nimblix.in.HealthCareHub.repository.PatientRepository;
import nimblix.in.HealthCareHub.repository.RoomRepository;
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

    @Override
    @Transactional
    public AdmitPatientResponseDTO admitPatient(AdmitPatientRequestDTO request) {

        // Uses Patient.id (Long) instead of patientId
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new PatientNotFoundException(
                        "Patient not found with id: " + request.getPatientId()));

        // Uses Patient.id and status as String "ADMITTED"
        boolean isPatientAlreadyAdmitted =
        admissionRepository.existsByPatient_IdAndStatus(
                patient.getId(),
                Admission.AdmissionStatus.ADMITTED
        );


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

        // Uses Room.roomId and status as String "ADMITTED"
        boolean isRoomOccupied =
                admissionRepository.existsByRoom_RoomIdAndStatus(
                        room.getRoomId(),
                        Admission.AdmissionStatus.ADMITTED
                );

        if (isRoomOccupied) {
            throw new IllegalArgumentException(
                    "Room " + room.getRoomNumber() + " is already occupied. Please select another room.");
        }

        // Build Admission using Builder pattern
        Admission admission = Admission.builder()
                .patient(patient)
                .doctor(doctor)
                .room(room)
                .admissionReason(request.getAdmissionReason())
                .symptoms(request.getSymptoms())
                .initialDiagnosis(request.getInitialDiagnosis())
                .status(Admission.AdmissionStatus.ADMITTED)



                .build();

        Admission savedAdmission = admissionRepository.save(admission);

        room.setStatus(Room.RoomStatus.OCCUPIED);
        roomRepository.save(room);

        return mapToResponse(savedAdmission);
    }

    private AdmitPatientResponseDTO mapToResponse(Admission admission) {
        AdmitPatientResponseDTO response = new AdmitPatientResponseDTO();

        // Admission info
        response.setAdmissionId(admission.getAdmissionId());
        response.setAdmissionDate(String.valueOf(admission.getAdmissionDate()));
        response.setAdmissionReason(admission.getAdmissionReason());
        response.setSymptoms(admission.getSymptoms());
        response.setInitialDiagnosis(admission.getInitialDiagnosis());
        response.setStatus(String.valueOf(admission.getStatus()));

        // Patient info - uses Patient.id and Patient.name (single field)
        Patient patient = admission.getPatient();
        response.setPatientId(patient.getId());
        response.setPatientName(patient.getName());
        response.setPatientPhone(patient.getPhone());

        // Doctor info - uses Doctor.id, Doctor.name, Doctor.specialization.name
        Doctor doctor = admission.getDoctor();
        response.setDoctorId(doctor.getId());
        response.setDoctorName("Dr. " + doctor.getName());

        // Get specialization name if exists
        if (doctor.getSpecialization() != null) {
            response.setDoctorSpecialization(doctor.getSpecialization().getName());
        } else {
            response.setDoctorSpecialization("General");
        }

        // Room info - uses Room.roomId, Room.roomNumber, Room.getRoomType()
        Room room = admission.getRoom();
        response.setRoomId(room.getRoomId());
        response.setRoomNumber(room.getRoomNumber());
        response.setRoomType(room.getRoomType());  // Returns enum.name() as String

        return response;
    }
}