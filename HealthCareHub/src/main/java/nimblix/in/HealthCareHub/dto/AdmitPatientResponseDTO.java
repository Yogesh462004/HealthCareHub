package nimblix.in.HealthCareHub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nimblix.in.HealthCareHub.model.Admission.AdmissionStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdmitPatientResponseDTO {

    private Long admissionId;

    private Long patientId;
    private String patientName;

    private Long doctorId;
    private String doctorName;

    private Long roomId;
    private String roomNumber;
    private String roomType;

    private LocalDateTime admissionDate;
    private String admissionReason;
    private String symptoms;
    private String initialDiagnosis;
    private AdmissionStatus status;

    private LocalDateTime createdAt;
}