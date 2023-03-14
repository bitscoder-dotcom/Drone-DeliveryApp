package danielIjedibia.The_Drone.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class BatteryLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drone_id")
    private DroneEntity droneModel;
    private double batteryCapacity;
    private double batteryPercentage;
    private LocalDateTime timestamp;

    public BatteryLogEntity(DroneEntity droneModel, double batteryCapacity, double batteryPercentage, LocalDateTime timestamp) {
    }
}
