package nimblix.in.HealthCareHub.exception;

public class DoctorNotFoundException extends RuntimeException {

    public DoctorNotFoundException(String s) {
        super("Doctor not found");
    }
}