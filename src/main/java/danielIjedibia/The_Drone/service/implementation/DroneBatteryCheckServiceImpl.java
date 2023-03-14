package danielIjedibia.The_Drone.service.implementation;

import danielIjedibia.The_Drone.dto.request.BatteryLogDto;
import danielIjedibia.The_Drone.entities.BatteryLogEntity;
import danielIjedibia.The_Drone.entities.DroneEntity;
import danielIjedibia.The_Drone.enums.DroneModels;
import danielIjedibia.The_Drone.enums.DroneState;
import danielIjedibia.The_Drone.mapper.BatteryLogMapper;
import danielIjedibia.The_Drone.repository.BatteryLogRepository;
import danielIjedibia.The_Drone.repository.DroneRepository;
import danielIjedibia.The_Drone.service.DroneBatteryCheckService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Log4j2
@Service
public class DroneBatteryCheckServiceImpl implements DroneBatteryCheckService {
    private final DroneRepository droneRepository;
    private final BatteryLogRepository batteryLogRepository;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final Map<DroneModels, Integer> droneModelBatteryCapacities = Map.of(
            DroneModels.LIGHTWEIGHT, 3000,
            DroneModels.MIDDLEWEIGHT, 5000,
            DroneModels.CRUISERWEIGHT, 7000,
            DroneModels.HEAVYWEIGHT, 10000
    );

    public DroneBatteryCheckServiceImpl(DroneRepository droneRepository, BatteryLogRepository batteryLogRepository) {
        this.droneRepository = droneRepository;
        this.batteryLogRepository = batteryLogRepository;
    }

    public void startBatteryCheckScheduler() {
        scheduler.scheduleAtFixedRate(this::checkBatteryLevels, 0 , 3, TimeUnit.MINUTES);
        log.info("Battery check scheduler started, it checks the battery level after every 3 minutes.");
    }

    public void stopBatteryCheckScheduler() {
        scheduler.shutdown();
        log.info("Battery check scheduler stopped");
    }

    @Override
    public void checkBatteryLevels() {
        log.info("Starting battery level check for all drones...");

        List<DroneEntity> droneModels = droneRepository.findAll();
        LocalDateTime now = LocalDateTime.now();

        for (DroneEntity droneModel : droneModels) {
            if ((droneModel.getState() == DroneState.DELIVERING || droneModel.getState() == DroneState.RETURNING) &&
                droneModel.getState() != DroneState.RETURNED && droneModel.getState() != DroneState.IDLE) {

                double batteryCapacity = droneModel.getBatteryCapacity();
                double updatedBatteryCapacity = Math.max(0, batteryCapacity - 5.0);

                double batteryPercentage = calculateBatteryPercentage(updatedBatteryCapacity, droneModel.getModel());
                droneModel.setBatteryCapacity(updatedBatteryCapacity);

                BatteryLogDto batteryLogDto = new BatteryLogDto(null, droneModel, batteryCapacity, batteryPercentage, now);
                // Convert the model to dto using the mapper class
                log.info("Converting the model to dto using the mapper class");
                BatteryLogEntity batteryLogModel = BatteryLogMapper.toEntity(batteryLogDto);

                batteryLogRepository.save(batteryLogModel);
                log.info("Saved battery log for drone with id{}: battery percentage = {}%", droneModel.getId(), batteryPercentage);
            }
        }
        log.info("Finished battery level check for all drones.");
    }

    @Override
    public double calculateBatteryPercentage(double batteryCapacity, DroneModels droneModels) {
        // Get the battery capacity for the specified drone model
        int fullBatteryCapacity = droneModelBatteryCapacities.get(droneModels);

        // Calculate the battery based on the battery capacity and full battery capacity
        double batteryPercentage = Math.min(100, batteryCapacity / fullBatteryCapacity * 100);
        log.info("Calculated battery percentage: {}", batteryPercentage);

        return batteryPercentage;
    }
}
