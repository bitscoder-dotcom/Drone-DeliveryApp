package danielIjedibia.The_Drone.controller;

import danielIjedibia.The_Drone.dto.request.DroneDto;
import danielIjedibia.The_Drone.dto.request.MedicationDto;
import danielIjedibia.The_Drone.dto.response.ApiResponse;
import danielIjedibia.The_Drone.entities.DroneEntity;
import danielIjedibia.The_Drone.exception.DroneNotFoundException;
import danielIjedibia.The_Drone.exception.DroneNotLoadedException;
import danielIjedibia.The_Drone.exception.LowBatteryException;
import danielIjedibia.The_Drone.exception.MedicationWeightExceededException;
import danielIjedibia.The_Drone.service.DroneService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("droneApi/v1")
public class DroneDispatchController {
    private final DroneService droneService;

    @PostMapping("/registerDrone")
    public ResponseEntity<String> registerDrone(@Valid @RequestBody DroneDto requestDto){
        droneService.registerDrone(requestDto);
        return new ResponseEntity<>("Drone registered successfully", HttpStatus.CREATED);
    }

    @GetMapping("/drones/{id}")
    public ResponseEntity<DroneEntity> getDroneById(@PathVariable("id") Long droneId) {
        try {
            DroneEntity drone = droneService.getDroneById(droneId);
            return ResponseEntity.ok(drone);
        } catch (DroneNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getAllDrones")
    public ResponseEntity<List<DroneEntity>> getAllDrones() {
        try {
            List<DroneEntity> drones = droneService.getAllDrones();
            return new ResponseEntity<>(drones, HttpStatus.OK);
        } catch (DroneNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/loadDrone/{id}")
    public ApiResponse<DroneEntity> loadDrone(@PathVariable("id") Long droneId, @RequestBody List<MedicationDto> medicationDtos) {
        try {
            return droneService.loadDrone(droneId, medicationDtos);
        } catch (DroneNotFoundException e) {
            return new ApiResponse<>(HttpStatus.NOT_FOUND, e.getMessage(), null, LocalDateTime.now());
        } catch (DroneNotLoadedException | MedicationWeightExceededException | LowBatteryException e) {
            return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null, LocalDateTime.now());
        }
    }

    @GetMapping("/availableDrones")
    public List<DroneDto> getAvailableDronesForLoading() {
        return droneService.findAvailableDronesForLoading();
    }

    @GetMapping("/medicationsFromDrone/{id}")
    public ResponseEntity<List<MedicationDto>> getLoadedMedicationsFromDrone(@PathVariable("id") Long droneId) {
        try {
            List<MedicationDto> medicationDtos = droneService.getLoadedMedicationsFromDrone(droneId);
            return ResponseEntity.ok(medicationDtos);
        } catch (DroneNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/batteryLevel/{id}")
    public ResponseEntity<String> getDroneBatteryLevels(@PathVariable("id") Long droneId) {
        try {
            String batteryLevel = droneService.getDroneBatteryLevels(droneId);
            return ResponseEntity.ok(batteryLevel);
        } catch (DroneNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/state/delivering/{id}")
    public ResponseEntity<String> setDroneStateToDelivering(@PathVariable("id") Long droneId) throws DroneNotFoundException, DroneNotLoadedException {
        DroneEntity drone = droneService.getDroneById(droneId);
        droneService.setDroneStateToDelivering(drone, response -> {
            String message = droneId + " is delivering medication";
            System.out.println(message);
        });
        return new ResponseEntity<>("Drone state set to delivering", HttpStatus.OK);
    }

    @PutMapping("/state/delivered/{id}")
    public ResponseEntity<DroneEntity> deliveredMedications(@PathVariable("id") Long droneId) throws DroneNotFoundException {
        DroneEntity drone = droneService.deliveredMedications(droneId);
        return new ResponseEntity<>(drone, HttpStatus.OK);
    }

    @PutMapping("/setStateToDelivered/{id}")
    public ResponseEntity<String> setDroneStateToDelivered(@PathVariable("id") Long droneId) throws DroneNotFoundException {
        DroneEntity drone = droneService.getDroneById(droneId);
        String successMessage = droneService.setDroneStateToDelivered(drone);
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }
    @PutMapping("/state/returned/{id}")
    public ResponseEntity<DroneEntity> returnedToBase(@PathVariable("id") Long droneId) throws DroneNotFoundException {
        DroneEntity drone = droneService.returnedToBase(droneId);
        return new ResponseEntity<>(drone, HttpStatus.OK);
    }
    @PutMapping("/state/Idle/{id}")
    public ResponseEntity<DroneEntity> setDroneToIdle(@PathVariable("id") Long droneId) throws DroneNotFoundException {
        DroneEntity drone = droneService.setDroneToIdle(droneId);
        return new ResponseEntity<>(drone, HttpStatus.OK);
    }
}
