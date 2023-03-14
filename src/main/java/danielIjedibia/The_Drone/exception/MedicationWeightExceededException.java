package danielIjedibia.The_Drone.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicationWeightExceededException extends Throwable{
    private String message;

    public MedicationWeightExceededException(String message) {
        this.message = message;
    }
}
