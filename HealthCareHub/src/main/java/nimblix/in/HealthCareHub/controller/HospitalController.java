package nimblix.in.HealthCareHub.controller;

import lombok.RequiredArgsConstructor;
import nimblix.in.HealthCareHub.request.HospitalRegistrationRequest;
import nimblix.in.HealthCareHub.response.RoomResponse;
import nimblix.in.HealthCareHub.service.HospitalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospital")
@RequiredArgsConstructor
public class HospitalController {

    private final HospitalService hospitalService;

    @PostMapping("/register")
    public String registerHospital(
            @RequestBody HospitalRegistrationRequest request) {

        return hospitalService.registerHospital(request);
    }

    @PostMapping("/{hospitalId}/rooms")
    public String addRooms(
            @PathVariable Long hospitalId,
            @RequestBody List<HospitalRegistrationRequest.Room> rooms) {

        hospitalService.addRooms(hospitalId, rooms);
        return "Rooms added successfully";
    }

    @GetMapping("/{hospitalId}/available-rooms")
    public List<RoomResponse> getAvailableRooms(
            @PathVariable Long hospitalId) {

        return hospitalService.getAvailableRooms(hospitalId);
    }
}