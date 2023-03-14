package danielIjedibia.The_Drone.dto.request;

import danielIjedibia.The_Drone.entities.DroneEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BatteryLogDto {
    private Long id;
    private DroneEntity droneModel;
    private double batteryCapacity;
    private double batteryPercentage;
    private LocalDateTime timestamp;
}
