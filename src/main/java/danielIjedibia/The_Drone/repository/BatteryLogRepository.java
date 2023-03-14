package danielIjedibia.The_Drone.repository;

import danielIjedibia.The_Drone.entities.BatteryLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatteryLogRepository extends JpaRepository<BatteryLogEntity, Long> {
}
