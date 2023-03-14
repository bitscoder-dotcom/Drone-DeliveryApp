package danielIjedibia.The_Drone.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum DroneModels {
    LIGHTWEIGHT(3000), MIDDLEWEIGHT(5000), CRUISERWEIGHT(7000), HEAVYWEIGHT(10000);

    private final int defaultBatteryCapacity;
    private static final String[] MODEL_NAMES = {"LIGHTWEIGHT", "MIDDLEWEIGHT", "CRUISERWEIGHT", "HEAVYWEIGHT"};

    DroneModels(int defaultBatteryCapacity) {
        this.defaultBatteryCapacity = defaultBatteryCapacity;
    }
    public static int getDefaultBatteryCapacity(String modelName) {
        return DroneModels.valueOf(modelName.toUpperCase()).getDefaultBatteryCapacity();
    }

    public int getDefaultBatteryCapacity() {
        return defaultBatteryCapacity;
    }

    public static List<DroneModels> getAllModels() {
        return Arrays.stream(MODEL_NAMES)
                .map(DroneModels::valueOf)
                .collect(Collectors.toList());
    }
}
