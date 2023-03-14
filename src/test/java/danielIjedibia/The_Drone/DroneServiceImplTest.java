package danielIjedibia.The_Drone;

import danielIjedibia.The_Drone.dto.request.DroneDto;
import danielIjedibia.The_Drone.dto.response.ApiResponse;
import danielIjedibia.The_Drone.entities.DroneEntity;
import danielIjedibia.The_Drone.enums.DroneModels;
import danielIjedibia.The_Drone.enums.DroneState;
import danielIjedibia.The_Drone.repository.DroneRepository;
import danielIjedibia.The_Drone.service.implementation.DroneServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.Month;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class DroneServiceImplTest {

    @InjectMocks // creates an instance of the class under test and injects the mocks into it
    private DroneServiceImpl droneService;
    @Mock // creates a mock object for the repository interface
    private DroneRepository droneRepository;
    private DroneEntity model;
    private LocalDateTime localDateTime;

    @BeforeEach // runs before each test method
    public void init() {
         model = new DroneEntity(1L, "ABC123", DroneModels.LIGHTWEIGHT, 100, 500,   DroneState.IDLE , null);
        localDateTime = LocalDateTime.of(2023, Month.MARCH , 12, 23, 00 , 00);

//////         create some test data
//        drone1 = new DroneDto();
//        drone1.setId(1L);
//        drone1.setSerialNumber("ABC123");
//        drone1.setBatteryCapacity(100);
//        drone1.setWeightLimit(500);
//        drone1.setModel("LIGHTWEIGHT");
//        drone1.setState("IDLE");
//
//        droneDto = new DroneDto();
//        droneDto.setId(2L);
//        droneDto.setSerialNumber("DEF123");
//        droneDto.setBatteryCapacity(20);
//        droneDto.setWeightLimit(500);
//        droneDto.setModel("HEAVYWEIGHT");
//        droneDto.setState("IDLE");
//
//        drone3 = new DroneDto();
//        droneDto.setId(3L);
//        droneDto.setSerialNumber("GHI123");
//        droneDto.setBatteryCapacity(70);
//        droneDto.setWeightLimit(500);
//        droneDto.setModel("CRUISERWEIGHT");
//        droneDto.setState("IDLE");
//
//        droneModel1 = new DroneModel();
//        droneModel1.setId(3L);
//        droneModel1.setSerialNumber("GHI123");
//        droneModel1.setBatteryCapacity(70);
//        droneModel1.setWeightLimit(500);
//        droneModel1.setModel(DroneModels.CRUISERWEIGHT);
//        droneModel1.setState(DroneState.IDLE);
//
//        droneModel2 = new DroneModel();
//        droneModel2.setId(2L);
//        droneModel2.setSerialNumber("DEF123");
//        droneModel2.setBatteryCapacity(20);
//        droneModel2.setWeightLimit(500);
//        droneModel2.setModel(DroneModels.HEAVYWEIGHT);
//        droneModel2.setState(DroneState.IDLE);
//
//        droneModel3 = new DroneModel();
//        droneModel3.setId(1L);
//        droneModel3.setSerialNumber("ABC123");
//        droneModel3.setBatteryCapacity(100);
//        droneModel3.setWeightLimit(500);
//        droneModel3.setModel(DroneModels.LIGHTWEIGHT);
//        droneModel3.setState(DroneState.IDLE);

    }
    @Test
    @DisplayName("Should register the drone object to database")
    void registerDrone() {
        DroneDto dto = new DroneDto(1L, "ABC123", "LIGHTWEIGHT", 100, 500,   "IDLE" , null );
        ApiResponse<String> expected = new ApiResponse<>(HttpStatus.CREATED , "Success" , "ABC123" , localDateTime);
        var newDrone = droneService.registerDrone(dto);
        newDrone.setTime(localDateTime);
        assertEquals(expected , newDrone);
        assertEquals(expected.getStatusCode().value() , newDrone.getStatusCode().value());
    }


}
