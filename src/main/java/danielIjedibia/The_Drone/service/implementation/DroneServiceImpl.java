package danielIjedibia.The_Drone.service.implementation;

import danielIjedibia.The_Drone.dto.request.BatteryLogDto;
import danielIjedibia.The_Drone.dto.request.DroneDto;
import danielIjedibia.The_Drone.dto.request.MedicationDto;
import danielIjedibia.The_Drone.dto.response.ApiResponse;
import danielIjedibia.The_Drone.entities.BatteryLogEntity;
import danielIjedibia.The_Drone.entities.DroneEntity;
import danielIjedibia.The_Drone.entities.MedicationEntity;
import danielIjedibia.The_Drone.enums.DroneModels;
import danielIjedibia.The_Drone.enums.DroneState;
import danielIjedibia.The_Drone.exception.DroneNotFoundException;
import danielIjedibia.The_Drone.exception.DroneNotLoadedException;
import danielIjedibia.The_Drone.mapper.BatteryLogMapper;
import danielIjedibia.The_Drone.mapper.DroneMapper;
import danielIjedibia.The_Drone.mapper.MedicationMapper;
import danielIjedibia.The_Drone.repository.BatteryLogRepository;
import danielIjedibia.The_Drone.repository.DroneRepository;
import danielIjedibia.The_Drone.service.DroneService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;


@Log4j2
@Service
@AllArgsConstructor
public class DroneServiceImpl implements DroneService {
    private DroneBatteryCheckServiceImpl droneBatteryCheckService;
    private BatteryLogRepository batteryLogRepository;
    private final DroneRepository droneRepository;

    @Override
    public ApiResponse registerDrone(DroneDto droneDto) {
        log.info("setting:: about setting");
        log.info("about registering");
        DroneEntity droneModel = DroneMapper.toEntity(droneDto);

        log.info("drone registered");
        droneRepository.save(droneModel);
        return  new ApiResponse<>(HttpStatus.CREATED, "Success", droneDto.getSerialNumber(), LocalDateTime.now());
    }

    @Override
    public DroneEntity getDroneById(Long droneId) throws DroneNotFoundException {
        return droneRepository.findById(droneId)
                .orElseThrow(() -> new DroneNotFoundException("Drone not found"));
    }

    @Override
    public List<DroneEntity> getAllDrones() throws DroneNotFoundException {
        List<DroneEntity> drones = droneRepository.findAll();
        if (drones.isEmpty()) {
            throw new DroneNotFoundException("No drones found");
        }
        return drones;
    }

    @Override
    public ApiResponse<DroneEntity> loadDrone(Long droneId, List<MedicationDto> medicationDtos) throws DroneNotFoundException, DroneNotLoadedException {
        log.debug("Loading drone with id {}", droneId);

        DroneEntity drone = droneRepository.findById(droneId)
                .orElseThrow(() -> new DroneNotFoundException("Drone not found with id " + droneId));

        double totalMedicationWeight = medicationDtos.stream()
                .mapToDouble(MedicationDto::getWeight)
                .sum();
        log.debug("Total medication weight: {}", totalMedicationWeight);

        if (totalMedicationWeight > 500) {
            log.error("Medication weight exceeds maximum capacity of 500 grams");
            return new ApiResponse<>(HttpStatus.BAD_REQUEST, "Medication weight exceeds maximum capacity of 500 grams", null, LocalDateTime.now());
        }

        double batteryPercentage = droneBatteryCheckService.calculateBatteryPercentage(drone.getBatteryCapacity(), drone.getModel());
        log.info("Battery percentage: {}", batteryPercentage);

        if (batteryPercentage < 25) {
            log.warn("Battery level is below 25%, call a different drone");
            return new ApiResponse<>(HttpStatus.BAD_REQUEST, "Battery level is below 25%, call a different drone", null, LocalDateTime.now());
        }

        List<MedicationEntity> medications = medicationDtos.stream()
                .map(MedicationMapper::toEntity)
                .collect(Collectors.toList());
        drone.setMedications(medications);

        drone.setState(DroneState.LOADED);

        if (drone.getState() != DroneState.LOADED) {
            log.error("Drone is not loaded with medications");
            return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, "Drone is not loaded with medications", null, LocalDateTime.now());
        }

        drone = droneRepository.save(drone);
        log.info("Drone loaded with medications: {}", drone);

        droneBatteryCheckService.startBatteryCheckScheduler();
        log.info("Battery check scheduler started");

        setDroneStateToDelivering(drone, droneModelApiResponse -> log.info("Drone state set to DELIVERING: {}", droneModelApiResponse.getData()));

        return new ApiResponse<>(HttpStatus.OK, "Drone loaded successfully", drone, LocalDateTime.now());
    }

    @Override
    public List<MedicationDto> getLoadedMedicationsFromDrone(Long droneId) throws DroneNotFoundException {
        log.debug("Getting loaded medications from drone with id {}", droneId);
        DroneEntity drone = droneRepository.findById(droneId)
                .orElseThrow(() -> new DroneNotFoundException("Drone not found for id " + droneId));
        List<MedicationDto> medicationDtos = drone.getMedications().stream()
                .map(MedicationMapper::toDto)
                .collect(Collectors.toList());
        String successMessage = "Loaded medications for drone with id " + droneId + " retrieved successfully";
        log.info(successMessage);
        return medicationDtos;
    }

    @Override
    public List<DroneDto> findAvailableDronesForLoading() {
        log.info("Getting available drones for loading...");

        int minBatteryPercentage = 25;
        List<DroneModels> droneModels = DroneModels.getAllModels();

        Double fullBatteryCapacity = droneModels.stream()
                .mapToDouble(DroneModels::getDefaultBatteryCapacity)
                .max()
                .orElseThrow(() -> new IllegalStateException("No drone models found"));

        List<DroneEntity> availableDrones = droneRepository
                .findAllByStateAndModelIn(DroneState.IDLE, droneModels)
                .stream()
                .filter(droneEntity -> droneEntity.getBatteryCapacity() / fullBatteryCapacity * 100 > minBatteryPercentage)
                .collect(Collectors.toList());

        List<DroneDto> availableDronesDto = availableDrones
                .stream()
                .map(DroneMapper::toDto)
                .collect(Collectors.toList());

        log.info("Found {} available drones for loading.", availableDrones.size());

        return availableDronesDto;
    }

    @Override
    public String getDroneBatteryLevels(Long droneId) throws DroneNotFoundException {
        log.debug("Getting battery level for id {}", droneId);
        DroneEntity drone = droneRepository.findById(droneId)
                .orElseThrow(() -> new DroneNotFoundException("Drone not found with id " + droneId));
        double batteryPercentage = droneBatteryCheckService.calculateBatteryPercentage(drone.getBatteryCapacity(), drone.getModel());
        String batteryLevel = "The battery capacity for " + drone.getSerialNumber() + ": " + drone.getBatteryCapacity() + "mAH, battery percentage:" + String.format("%.2f", batteryPercentage) + "%";

        log.info(batteryLevel);

        BatteryLogDto batteryLogDto = new BatteryLogDto(null, drone, drone.getBatteryCapacity(), batteryPercentage, LocalDateTime.now());
        BatteryLogEntity batteryLogModel = BatteryLogMapper.toEntity(batteryLogDto);
        batteryLogRepository.save(batteryLogModel);

        log.info("Saved battery log for drone with id {}: battery percentage = {}%", drone.getId(), batteryPercentage);
        return batteryLevel;
    }

    @Override
    public void setDroneStateToDelivering(DroneEntity drone, Consumer<ApiResponse<DroneEntity>> callback) throws DroneNotLoadedException {
        // Validate that the drone is loaded before setting the state to DELIVERING
        if (drone.getState() != DroneState.LOADED){
            String errorMessage = "Drone is not loaded with medications";
            log.error(errorMessage);
            throw new DroneNotLoadedException(errorMessage);
        }
        // Set drone state to DELIVERING  and save changes to the database
        drone.setState(DroneState.DELIVERING);
        droneRepository.save(drone);

        String successMessage = "Drone state is set to DELIVERING: {}" + drone;
        log.info(successMessage);
        ApiResponse<DroneEntity> response = new ApiResponse<>(
                HttpStatus.OK,
                successMessage,
                drone,
                LocalDateTime.now()
        );
        callback.accept(response);
    }

    @Override
    public String setDroneStateToDelivered(DroneEntity drone) {
        // Set drone state to DELIVERED and save changes to the database
        if (drone.getState() == DroneState.DELIVERING) {
            drone.setState(DroneState.DELIVERED);
            droneRepository.save(drone);
        }
        String successMessage = "Drone state is set to DELIVERED: {}" + drone;
        log.info(successMessage);
        return successMessage;
    }
    @Override
    public DroneEntity deliveredMedications(Long droneId) throws DroneNotFoundException {
        log.debug("Delivering medications for drone with id {}", droneId);

        DroneEntity drone = droneRepository.findById(droneId)
                .orElseThrow(() -> new DroneNotFoundException("Drone not found with id " + droneId));

        // Set drone state to IDLE or RETURNED depending on the current state
        if (drone.getState() == DroneState.DELIVERED) {
            drone.setState(DroneState.RETURNING);
        }

        // Remove medications from drone
        drone.setMedications(Collections.emptyList());

        // Save changes to drone
        drone = droneRepository.save(drone);
        log.info("Medications delivered for drone with id {}: {}", droneId, drone);

        droneBatteryCheckService.stopBatteryCheckScheduler();

        return drone;
    }

    @Override
    public DroneEntity returnedToBase(Long droneId) throws DroneNotFoundException {
        log.debug("Returning to base for drone with id {}", droneId);

        DroneEntity drone = droneRepository.findById(droneId)
                .orElseThrow(() -> new DroneNotFoundException("Drone not found with id " + droneId));

        // Set drone state to RETURNED if it is currently RETURNING
        if (drone.getState() == DroneState.RETURNING) {
            drone.setState(DroneState.RETURNED);
        }

        // Save changes to drone
        drone = droneRepository.save(drone);
        log.info("Drone with id {} returned to base: {}", droneId, drone);

        // Stop battery check scheduler
        droneBatteryCheckService.stopBatteryCheckScheduler();

        return drone;
    }

    @Override
    public DroneEntity setDroneToIdle(Long droneId) throws DroneNotFoundException {
        log.debug("Setting drone with id {} to IDLE", droneId);

        DroneEntity drone = droneRepository.findById(droneId)
                .orElseThrow(() -> new DroneNotFoundException("Drone not found with id " + droneId));

        // Set drone state to IDLE
        if (drone.getState() == DroneState.RETURNED) {
            drone.setState(DroneState.IDLE);
        }

        // Save changes to drone
        drone = droneRepository.save(drone);
        log.info("Drone with id {} set to IDLE: {}", droneId, drone);

        return drone;
    }
}