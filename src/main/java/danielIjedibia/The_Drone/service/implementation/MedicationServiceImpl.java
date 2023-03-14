package danielIjedibia.The_Drone.service.implementation;

import danielIjedibia.The_Drone.repository.MedicationRepository;
import danielIjedibia.The_Drone.service.MedicationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MedicationServiceImpl implements MedicationService {
    private final MedicationRepository medicationRepository;
//    @Override
//    public MedicationModel medicationsTOBeLoadedOnDrone(MedicationDto medicationDto) {
//        MedicationModel medication = MedicationMapperClass.toEntity(medicationDto);
//        return medicationRepository.save(medication);
//    }
}
