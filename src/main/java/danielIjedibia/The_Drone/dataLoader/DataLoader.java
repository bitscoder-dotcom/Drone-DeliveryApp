package danielIjedibia.The_Drone.dataLoader;

import danielIjedibia.The_Drone.entities.DroneEntity;
import danielIjedibia.The_Drone.entities.MedicationEntity;
import danielIjedibia.The_Drone.enums.DroneModels;
import danielIjedibia.The_Drone.enums.DroneState;
import danielIjedibia.The_Drone.repository.DroneRepository;
import danielIjedibia.The_Drone.repository.MedicationRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Component
public class DataLoader {
    private DroneRepository droneRepository;
    private MedicationRepository medicationRepository;

    public DataLoader(DroneRepository droneRepository, MedicationRepository medicationRepository) {
        this.droneRepository = droneRepository;
        this.medicationRepository = medicationRepository;
    }

    @PostConstruct
    public void loadDroneData() {
        // Create a medication
        MedicationEntity medication = new MedicationEntity();
        medication.setName("Aspirin");
        medication.setWeight(200);
        medication.setCode("ASP-01");
        // Save the medication to the repository
        medicationRepository.save(medication);

        MedicationEntity medication2 = new MedicationEntity();
        medication2.setName("Paracetamol");
        medication2.setWeight(90.06);
        medication2.setCode("PAR-01");
        medicationRepository.save(medication2);

        MedicationEntity medication1 = new MedicationEntity();
        medication1.setName("Ibuprofen");
        medication1.setWeight(75.35);
        medication1.setCode("IBU-01");
        medicationRepository.save(medication1);

        // Create a drone
        DroneEntity drone = new DroneEntity();
        drone.setSerialNumber(UUID.randomUUID());
        drone.setModel(DroneModels.LIGHTWEIGHT);
        drone.setWeightLimit(300);
        drone.setBatteryCapacity(1000);
        drone.setBatteryPercentage(100);
        drone.setState(DroneState.IDLE);
        // Add the medication to the drone
        drone.setMedications(null);
        // Save the drone to the repository
        droneRepository.save(drone);

        DroneEntity drone3 = new DroneEntity();
        drone3.setSerialNumber(UUID.randomUUID());
        drone3.setModel(DroneModels.HEAVYWEIGHT);
        drone3.setWeightLimit(500);
        drone3.setBatteryCapacity(4000);
        drone3.setBatteryPercentage(100);
        drone3.setState(DroneState.IDLE);
        droneRepository.save(drone3);

        DroneEntity drone2 = new DroneEntity();
        drone2.setSerialNumber(UUID.randomUUID());
        drone2.setModel(DroneModels.CRUISERWEIGHT);
        drone2.setWeightLimit(300);
        drone2.setBatteryCapacity(6600);
        drone2.setBatteryPercentage(100);
        drone2.setState(DroneState.IDLE);
        droneRepository.save(drone2);

        DroneEntity drone1 = new DroneEntity();
        drone1.setSerialNumber(UUID.randomUUID());
        drone1.setModel(DroneModels.MIDDLEWEIGHT);
        drone1.setWeightLimit(500);
        drone1.setBatteryCapacity(400);
        drone1.setBatteryPercentage(10);
        drone1.setState(DroneState.IDLE);
        droneRepository.save(drone1);
    }
}
