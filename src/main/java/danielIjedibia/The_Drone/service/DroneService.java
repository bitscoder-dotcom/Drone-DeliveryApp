package danielIjedibia.The_Drone.service;

import danielIjedibia.The_Drone.dto.request.DroneDto;
import danielIjedibia.The_Drone.dto.request.MedicationDto;
import danielIjedibia.The_Drone.dto.response.ApiResponse;
import danielIjedibia.The_Drone.entities.DroneEntity;
import danielIjedibia.The_Drone.exception.DroneNotFoundException;
import danielIjedibia.The_Drone.exception.DroneNotLoadedException;
import danielIjedibia.The_Drone.exception.LowBatteryException;
import danielIjedibia.The_Drone.exception.MedicationWeightExceededException;

import java.util.List;
import java.util.function.Consumer;

public interface DroneService {
    ApiResponse<String> registerDrone(DroneDto droneDto);
    DroneEntity getDroneById(Long droneId) throws DroneNotFoundException;
    List<DroneEntity> getAllDrones() throws DroneNotFoundException;
    ApiResponse<DroneEntity> loadDrone(Long droneId, List<MedicationDto> medicationDtos) throws DroneNotFoundException, MedicationWeightExceededException, LowBatteryException, DroneNotLoadedException;
    void setDroneStateToDelivering(DroneEntity drone, Consumer<ApiResponse<DroneEntity>> callback) throws DroneNotLoadedException;
    List<MedicationDto> getLoadedMedicationsFromDrone(Long droneId) throws DroneNotFoundException;
    List<DroneDto> findAvailableDronesForLoading();
    String getDroneBatteryLevels(Long droneId) throws DroneNotFoundException;
    DroneEntity deliveredMedications(Long droneId) throws DroneNotFoundException;
    String setDroneStateToDelivered(DroneEntity drone);
    DroneEntity returnedToBase(Long droneId) throws DroneNotFoundException;
    DroneEntity setDroneToIdle(Long droneId) throws DroneNotFoundException;
}
