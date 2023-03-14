package danielIjedibia.The_Drone.repository;

import danielIjedibia.The_Drone.entities.MedicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepository extends JpaRepository<MedicationEntity, Long> {
}
