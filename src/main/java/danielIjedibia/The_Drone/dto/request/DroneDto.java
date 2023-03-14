package danielIjedibia.The_Drone.dto.request;

import danielIjedibia.The_Drone.entities.MedicationEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DroneDto {
    private Long id;
    @Size(min = 3, max = 5)
    private UUID serialNumber;
    private String model;
    @Range(min = 1, max = 500)
    private double weightLimit;
    private double batteryCapacity;
    private double batteryPercentage;
    private String state;
    private List<MedicationEntity> medications;
}
