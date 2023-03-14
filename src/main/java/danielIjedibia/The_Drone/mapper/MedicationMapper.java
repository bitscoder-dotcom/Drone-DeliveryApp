package danielIjedibia.The_Drone.mapper;

import danielIjedibia.The_Drone.dto.request.MedicationDto;
import danielIjedibia.The_Drone.entities.MedicationEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
//@NoArgsConstructor
@Getter
public class MedicationMapper {
    public static MedicationDto toDto(MedicationEntity medicationModel) {
        MedicationDto medicationDto = new MedicationDto();
        medicationDto.setName(medicationModel.getName());
        medicationDto.setWeight(medicationModel.getWeight());
        medicationDto.setCode(medicationModel.getCode());
        medicationDto.setImage(medicationModel.getImage());

        return medicationDto;
    }

    public static MedicationEntity toEntity(MedicationDto medicationDto) {
        MedicationEntity medicationModel = new MedicationEntity();
        medicationModel.setName(medicationDto.getName());
        medicationModel.setCode(medicationDto.getCode());
        medicationModel.setWeight(medicationDto.getWeight());
        medicationModel.setImage(medicationDto.getImage());

        return medicationModel;
    }
}
