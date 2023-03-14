package danielIjedibia.The_Drone.mapper;

import danielIjedibia.The_Drone.dto.request.BatteryLogDto;
import danielIjedibia.The_Drone.entities.BatteryLogEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BatteryLogMapper {

    public static BatteryLogDto toDto(BatteryLogEntity batteryLogModel) {
        BatteryLogDto batteryLogDto = new BatteryLogDto();
        batteryLogDto.setId(batteryLogModel.getId());
        batteryLogDto.setDroneModel(batteryLogModel.getDroneModel());
        batteryLogDto.setBatteryCapacity(batteryLogModel.getBatteryCapacity());
        batteryLogDto.setBatteryPercentage(batteryLogModel.getBatteryPercentage());
        batteryLogDto.setTimestamp(batteryLogModel.getTimestamp());

        return batteryLogDto;
    }

    public static BatteryLogEntity toEntity(BatteryLogDto batteryLogDto) {
        BatteryLogEntity batteryLogModel = new BatteryLogEntity();
        batteryLogModel.setId(batteryLogDto.getId());
        batteryLogModel.setDroneModel(batteryLogDto.getDroneModel());
        batteryLogModel.setBatteryCapacity(batteryLogDto.getBatteryCapacity());
        batteryLogModel.setBatteryPercentage(batteryLogDto.getBatteryPercentage());
        batteryLogModel.setTimestamp(batteryLogDto.getTimestamp());

        return batteryLogModel;
    }
}
