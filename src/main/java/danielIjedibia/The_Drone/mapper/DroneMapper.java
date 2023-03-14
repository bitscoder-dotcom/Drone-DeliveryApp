package danielIjedibia.The_Drone.mapper;

import danielIjedibia.The_Drone.dto.request.DroneDto;
import danielIjedibia.The_Drone.entities.DroneEntity;
import danielIjedibia.The_Drone.enums.DroneModels;
import danielIjedibia.The_Drone.enums.DroneState;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
//@NoArgsConstructor
@Getter
public class DroneMapper {
    public static DroneDto toDto(DroneEntity droneModel){
        DroneDto droneDto = new DroneDto();
        droneDto.setId(droneModel.getId());
        droneDto.setSerialNumber(droneModel.getSerialNumber());
        droneDto.setModel(droneModel.getModel().toString());
        droneDto.setWeightLimit(droneModel.getWeightLimit());
        droneDto.setBatteryCapacity(droneModel.getBatteryCapacity());
        droneDto.setBatteryPercentage(droneModel.getBatteryPercentage());
        droneDto.setState(droneModel.getState().toString());
        droneDto.setMedications(droneModel.getMedications());

        return droneDto;
    }

    public static DroneEntity toEntity(DroneDto droneDto) {
        DroneEntity droneModel = new DroneEntity();
        droneModel.setId(droneDto.getId());
        droneModel.setSerialNumber(droneDto.getSerialNumber());
        droneModel.setModel(DroneModels.valueOf(droneDto.getModel()));
        droneModel.setWeightLimit(droneDto.getWeightLimit());
        droneModel.setBatteryCapacity(droneDto.getBatteryCapacity());
        droneModel.setBatteryPercentage(droneDto.getBatteryPercentage());
        droneModel.setState(DroneState.valueOf(droneDto.getState()));
        droneModel.setMedications(droneDto.getMedications());

        return droneModel;
    }
}
