package danielIjedibia.The_Drone.entities;

import danielIjedibia.The_Drone.enums.DroneModels;
import danielIjedibia.The_Drone.enums.DroneState;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

// Entity class for drone
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DroneEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID serialNumber; // 100 characters max
    @Enumerated(EnumType.STRING)
    private DroneModels model; // Lightweight, Middleweight, Cruiserweight, Heavyweight
    private double weightLimit; // 500gr max
    private double batteryCapacity; // remaining capacity
    private double batteryPercentage;  // percentage
    @Enumerated(EnumType.STRING)
    private DroneState state; // IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "drone_medication",
        joinColumns = @JoinColumn(name = "drone_id"),
        inverseJoinColumns = @JoinColumn(name = "medication_id"))
    private List<MedicationEntity> medications;
}

