package location;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LocationServiceTest {

    LocationService service = new LocationService();

    @TempDir
    Path tempDir;

    @Test
    void testTempDir() throws IOException {
        Path file = tempDir.resolve("file.txt");
        LocationParser parser = new LocationParser();
        Location kecskemét = parser.parser("Kecskemét, 47,20");
        Location budapest = parser.parser("Budapest,47.497912,19.040235");
        List<Location> locations = new ArrayList<>();
        locations.add(kecskemét);
        locations.add(budapest);

        service.writeLocations(file, locations);

        List<String> text = Files.readAllLines(file);
        assertEquals("Budapest,47.497912,19.040235", text.get(1));
    }
//      Hamcrest
//    @Test
//    void testReadLocation() {
//        Path file = Path.of("src/test/resources/hamcrest.csv");
//        List<Location> result = service.readLocations(file);
//
//        assertThat(result, hasSize(2));
//        assertThat(result, hasItem(
//                hasProperty("name", equalTo("Budapest"))));
//
//    }

    @Test
    void testReadLocation() {
        Path file = Path.of("src/test/resources/hamcrest.csv");
        List<Location> result = service.readLocations(file);

        assertThat(result)
                .hasSize(2)
                .extracting(Location::getName)
                .contains("Budapest", "Kecskemét");
    }

    Condition<Location> locationWithZeroCoordinate =
            new Condition<>(
                    location -> location.isOnPrimeMeridian() || location.isOnEquator(),
                    "Location have zero coordinate");


}