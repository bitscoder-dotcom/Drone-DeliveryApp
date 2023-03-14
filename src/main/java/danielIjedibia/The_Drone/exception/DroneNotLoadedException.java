package danielIjedibia.The_Drone.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DroneNotLoadedException extends Throwable {
    private String message;

    public DroneNotLoadedException(String message) {
        this.message = message;
    }
}
