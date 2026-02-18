package nimblix.in.HealthCareHub.model;

import jakarta.persistence.*;
import lombok.*;
import nimblix.in.HealthCareHub.utility.HealthCareUtil;


@Entity
@Table(name = "lab_results")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LabResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resultId;

    // ── FOREIGN KEY 1: PATIENT
    // @ManyToOne — MANY lab results belong to ONE patient
    // Creates column: lab_results.patient_id → patients.id
    // PARENT: Patient   CHILD: LabResult (this entity holds the FK)
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    // ── FOREIGN KEY 2: DOCTOR
    // @ManyToOne — MANY lab results ordered by ONE doctor
    // Creates column: lab_results.doctor_id → doctors.id
    // PARENT: Doctor   CHILD: LabResult (this entity holds the FK)
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;


    @Column(nullable = false)
    private String testName;

    private String testCategory;

    private String result;

    private String referenceRange;

    private String unit;


    @Column(nullable = false)
    private String status;

    private String remarks;

    @Column(name = "tested_at")
    private String testedAt;

    @Column(name = "created_time")
    private String createdTime;

    @Column(name = "updated_time")
    private String updatedTime;

    @PrePersist
    protected void onCreate() {
        this.createdTime = HealthCareUtil.changeCurrentTimeToLocalDateFromGmtToISTInString();
        this.updatedTime = HealthCareUtil.changeCurrentTimeToLocalDateFromGmtToISTInString();
        if (this.testedAt == null) {
            this.testedAt = HealthCareUtil.changeCurrentTimeToLocalDateFromGmtToISTInString();
        }
        if (this.status == null) {
            this.status = "PENDING";
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedTime = HealthCareUtil.changeCurrentTimeToLocalDateFromGmtToISTInString();
    }
}