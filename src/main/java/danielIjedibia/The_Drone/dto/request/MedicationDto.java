package danielIjedibia.The_Drone.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicationDto {
    private String name;
    private double weight;
    private String code;
    private byte[] image;

    public void setCode(String code) {
        if (!code.matches("^[A-Z0-9_]*$")) {
            throw new IllegalArgumentException("Code must contain only upper case letters, numbers, and underscore");
        }
        this.code = code;
    }

    public void setName(String name) {
        if (!name.matches("^[A-Za-z0-9_-]*$")) {
            throw new IllegalArgumentException("Name must contain only letters, numbers, hyphen, and underscore");
        }
        this.name = name;
    }
}
