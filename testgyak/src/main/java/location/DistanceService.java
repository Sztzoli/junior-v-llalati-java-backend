package location;

import java.util.Optional;

public class DistanceService {

    private final LocationRepository locationRepository;

    public DistanceService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Optional<Double> calculateDistance(String name1, String name2) {
        Optional<Location> locationOptional1 = locationRepository.findByName(name1);
        Optional<Location> locationOptional2 = locationRepository.findByName(name2);
        if (locationOptional1.isEmpty() || locationOptional2.isEmpty()) {
            return Optional.empty();
        }
        Location location1 = locationOptional1.get();
        Location location2 = locationOptional2.get();
        return Optional.of(location1.distance(location2));
    }
}
