package nimblix.in.HealthCareHub.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorProfileResponse {

    private Long doctorId;
    private String name;
    private String email;
    private String phone;
    private String qualification;
    private Integer experienceYears;

    private Long specializationId;
    private String specializationName;

    // Hospital info
    private Long hospitalId;
    private String hospitalName;
    private String hospitalAddress;
    private String hospitalCity;
    private String hospitalState;
    private String hospitalPhone;
    private String hospitalEmail;
    private Integer hospitalTotalBeds;
}