package danielIjedibia.The_Drone.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LowBatteryException extends Throwable{
    private String message;

    public LowBatteryException(String message) {
        this.message = message;
    }
}
