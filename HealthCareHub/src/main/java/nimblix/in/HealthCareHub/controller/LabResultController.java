package nimblix.in.HealthCareHub.controller;

import nimblix.in.HealthCareHub.dto.LabResultResponseDTO;
import nimblix.in.HealthCareHub.service.LabResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lab-results")
public class LabResultController {

    @Autowired
    private LabResultService labResultService;

    // GET /api/lab-results/{resultId}
    // Task 186 - Get lab result by ID
    @GetMapping("/{resultId}")
    public ResponseEntity<Map<String, Object>> getLabResultById(
            @PathVariable Long resultId) {

        LabResultResponseDTO data = labResultService.getLabResultById(resultId);

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Lab result fetched successfully");
        response.put("data", data);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // GET /api/lab-results/patient/{patientId}
    // Task 186 - Get all lab results for a patient
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<Map<String, Object>> getLabResultsByPatient(
            @PathVariable Long patientId) {

        List<LabResultResponseDTO> data = labResultService.getLabResultsByPatient(patientId);

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Lab results fetched successfully");
        response.put("count", data.size());
        response.put("data", data);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}