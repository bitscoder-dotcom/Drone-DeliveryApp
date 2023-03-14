package danielIjedibia.The_Drone.entities;

import lombok.*;

import javax.persistence.*;

// Entity class for medications
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class MedicationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double weight;
    private String code;
    private byte[] image; // picture of the medication case
}
