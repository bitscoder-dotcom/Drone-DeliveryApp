package danielIjedibia.The_Drone.exception;

import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class DroneNotFoundException extends Throwable {
    private String message;

    public DroneNotFoundException(String message) {
        this.message = message;
    }
}
