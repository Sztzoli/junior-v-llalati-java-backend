package location;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@LocationOperationsFeatureTest
class LocationOperatorsTest {


    LocationOperators locationOperators;
    List<Location> locations = new ArrayList<>();

    @BeforeEach
    void setUp() {
        locationOperators = new LocationOperators();
        Location location = new Location("Budapest", 49, 20);
        Location location2 = new Location("Kecskemét", 44, 20);
        Location location3 = new Location("Fokváros", -33, 18);
        locations.add(location);
        locations.add(location2);
        locations.add(location3);
    }

    @Test
    void testWinterIsComing() {
        List<String> northCity = locationOperators.filterOnNorth(locations).stream().map(Location::getName).collect(Collectors.toList());

        assertEquals(List.of("Budapest", "Kecskemét"), northCity);
    }
}