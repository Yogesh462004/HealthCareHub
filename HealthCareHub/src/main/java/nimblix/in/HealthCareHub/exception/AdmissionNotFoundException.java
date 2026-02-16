package nimblix.in.HealthCareHub.exception;

public class AdmissionNotFoundException extends RuntimeException {

    public AdmissionNotFoundException(String s) {
        super("Admission not found");
    }
}