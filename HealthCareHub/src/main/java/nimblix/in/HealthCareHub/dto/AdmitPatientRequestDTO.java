package nimblix.in.HealthCareHub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdmitPatientRequestDTO {

    private Long patientId;
    private Long doctorId;
    private Long roomId;


    private String admissionReason;
    private String symptoms;
    private String initialDiagnosis;
}
