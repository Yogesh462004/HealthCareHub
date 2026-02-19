package nimblix.in.HealthCareHub.controller;

import nimblix.in.HealthCareHub.request.AdmitPatientRequestDTO;
import nimblix.in.HealthCareHub.response.AdmitPatientResponseDTO;
import nimblix.in.HealthCareHub.service.AdmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admissions")
public class AdmissionController {

    @Autowired
    private AdmissionService admissionService;

    // POST /api/admissions/admit
    @PostMapping("/admit")
    public ResponseEntity<Map<String, Object>> admitPatient(@RequestBody AdmitPatientRequestDTO request) {
        AdmitPatientResponseDTO data = admissionService.admitPatient(request);

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.CREATED.value());
        response.put("message", "Patient admitted successfully");
        response.put("data", data);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}