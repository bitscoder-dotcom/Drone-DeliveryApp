package danielIjedibia.The_Drone.repository;

import danielIjedibia.The_Drone.entities.DroneEntity;
import danielIjedibia.The_Drone.enums.DroneModels;
import danielIjedibia.The_Drone.enums.DroneState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;


public interface DroneRepository extends JpaRepository<DroneEntity, Long> {
    @Query("""
        SELECT droneEntity
        FROM DroneEntity droneEntity
        WHERE droneEntity.state = :state
        AND droneEntity.model IN :models
    """)
    List<DroneEntity> findAllByStateAndModelIn(
            @Param("state") DroneState state,
            @Param("models") Collection<DroneModels> models
    );
}