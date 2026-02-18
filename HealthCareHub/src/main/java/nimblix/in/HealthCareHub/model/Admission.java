package nimblix.in.HealthCareHub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nimblix.in.HealthCareHub.utility.HealthCareUtil;

@Entity
@Table(name = "admissions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Admission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long admissionId;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    private String admissionDate;

    private String admissionReason;

    private String symptoms;

    private String initialDiagnosis;

    @Enumerated(EnumType.STRING)
    private AdmissionStatus status;

    private String createdAt;

    private String updatedAt;

    @PrePersist
    protected void onCreate() {

        this.createdAt = HealthCareUtil.changeCurrentTimeToLocalDateFromGmtToISTInString();
        this.updatedAt = HealthCareUtil.changeCurrentTimeToLocalDateFromGmtToISTInString();

        if (this.status == null) {
            this.status = AdmissionStatus.ADMITTED;
        }

        if (this.admissionDate == null || this.admissionDate.isEmpty()) {
            this.admissionDate =
                    HealthCareUtil.changeCurrentTimeToLocalDateFromGmtToISTInString();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt =
                HealthCareUtil.changeCurrentTimeToLocalDateFromGmtToISTInString();
    }

    public enum AdmissionStatus {
        ADMITTED,
        DISCHARGED,
        TRANSFERRED
    }
}
