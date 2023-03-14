package danielIjedibia.The_Drone.dto.response;

import danielIjedibia.The_Drone.entities.DroneEntity;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ApiResponse<T> {
    private HttpStatus statusCode;
    private String message;
    private T data;
    private LocalDateTime time = LocalDateTime.now();

    public ApiResponse(HttpStatus statusCode, String message, T data, LocalDateTime time) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.time = time;
    }

    public ApiResponse(HttpStatus status, String message, List<DroneEntity> drones) {
    }
}
