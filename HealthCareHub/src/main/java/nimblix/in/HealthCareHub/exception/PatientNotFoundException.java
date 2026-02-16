package nimblix.in.HealthCareHub.exception;

public class PatientNotFoundException extends RuntimeException {

    public PatientNotFoundException(String s) {
        super("Patient not found");
    }
}