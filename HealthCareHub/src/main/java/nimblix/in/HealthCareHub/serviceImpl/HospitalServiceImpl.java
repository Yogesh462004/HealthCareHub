package nimblix.in.HealthCareHub.serviceImpl;

import lombok.RequiredArgsConstructor;
import nimblix.in.HealthCareHub.model.Hospital;
import nimblix.in.HealthCareHub.repository.HospitalRepository;
import nimblix.in.HealthCareHub.request.HospitalRegistrationRequest;
import nimblix.in.HealthCareHub.response.RoomResponse;
import nimblix.in.HealthCareHub.service.HospitalService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository hospitalRepository;

    @Override
    public String registerHospital(HospitalRegistrationRequest request) {


        if (hospitalRepository.findByName(request.getName()).isPresent()) {
            return "Hospital already exists";
        }

        Hospital hospital = Hospital.builder()
                .name(request.getName())
                .address(request.getAddress())
                .city(request.getCity())
                .state(request.getState())
                .phone(request.getPhone())
                .email(request.getEmail())
                .totalBeds(request.getTotalBeds())
                .build();

        hospitalRepository.save(hospital);

        return "Hospital Registered Successfully";
    }



    @Override
    public void addRooms(Long hospitalId,
                         List<HospitalRegistrationRequest.Room> rooms) {

        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new IllegalArgumentException("Hospital not found"));

        if (hospital.getRooms().size() + rooms.size() > hospital.getTotalBeds()) {
            throw new IllegalArgumentException("Exceeds total bed capacity");
        }

        for (HospitalRegistrationRequest.Room roomReq : rooms) {
            Hospital.Room room = new Hospital.Room();
            room.setRoomNumber(roomReq.getRoomNumber());
            room.setRoomType(roomReq.getRoomType());
            room.setAvailable(roomReq.isAvailable());

            hospital.getRooms().add(room);
        }

        hospitalRepository.save(hospital);
    }

    @Override
    public List<RoomResponse> getAvailableRooms(Long hospitalId) {

        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new IllegalArgumentException("Hospital not found"));

        List<RoomResponse> response = new ArrayList<>();

        for (Hospital.Room room : hospital.getRooms()) {
            if (room.isAvailable()) {
                RoomResponse roomResponse = RoomResponse.builder()
                        .roomNumber(room.getRoomNumber())
                        .roomType(room.getRoomType())
                        .available(room.isAvailable())
                        .build();

                response.add(roomResponse);
            }
        }

        return response;
    }
}