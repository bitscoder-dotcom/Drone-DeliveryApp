package danielIjedibia.The_Drone.service;

import danielIjedibia.The_Drone.entities.DroneEntity;
import danielIjedibia.The_Drone.enums.DroneModels;

public interface DroneBatteryCheckService {
    void checkBatteryLevels();
    double calculateBatteryPercentage(double batteryCapacity, DroneModels droneModels);
}
