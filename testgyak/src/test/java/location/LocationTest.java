package location;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;


import java.util.stream.Stream;

//import static org.hamcrest.MatcherAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
//import static location.LocationWithZeroCoordinateMatcher.*;

class LocationTest {

    LocationParser locationParser;
    public static final String TEXT = "Budapest,47.497912,19.040235";

    @BeforeEach
    void setUp() {
        locationParser = new LocationParser();
    }

    @Test
    void testParse() {

        Location budapest = locationParser.parser(TEXT);
        System.out.println(budapest);
        assertEquals("Budapest", budapest.getName());
    }

    @Test
    void testParse2() {

        Location budapest = locationParser.parser(TEXT);
        System.out.println(budapest);
        assertEquals("Budapest", budapest.getName());
    }

    @Test
    void testIsEquatorTrue() {
        Location test = locationParser.parser("Budapest,0,0");

        assertTrue(test.isOnEquator());
    }

    @Test
    @DisplayName("is Equator false")
    void testIsEquatorFalse() {
        Location test = locationParser.parser(TEXT);

        assertFalse(test.isOnEquator());
    }

    @Test
    void testIsPrimeMeridian() {
        Location test = locationParser.parser("Budapest,0,0");
        assertTrue(test.isOnEquator());
    }

    @Test
    void testIsPrimeMeridianFalse() {
        Location test = locationParser.parser(TEXT);
        assertFalse(test.isOnEquator());
    }

    @Test
    void testDistanceFrom() {
        Location budapest = locationParser.parser(TEXT);
        Location kecskemet = locationParser.parser("Kecskemét, 47,20");

        assertEquals(91176.71d, budapest.distance(kecskemet), 0.005);
    }

    @Test
    void testAssertAll() {
        Location budapest = locationParser.parser(TEXT);
        assertAll(
                () -> assertEquals("Budapest", budapest.getName()),
                () -> assertEquals(47.497912d, budapest.getLat(), 0.005),
                () -> assertEquals(19.040235d, budapest.getLon(), 0.005)
        );
    }

    @Test
    void testCreateFailLat() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new Location("buda", -91, 0)),
                () -> assertThrows(IllegalArgumentException.class, () -> new Location("buda", 91, 0))
        );
    }

    @Test
    void testCreateFailLon() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new Location("buda", 0, -181)),
                () -> assertThrows(IllegalArgumentException.class, () -> new Location("buda", 0, 181))
        );
    }

    private String[][] values = {{"Budapest,0,0", "true"}, {"Kecskemét,1,0", "false"}};

    @RepeatedTest(value = 2)
    void testIsOnEquator(RepetitionInfo repetitionInfo) {
        Location location = locationParser.parser(values[repetitionInfo.getCurrentRepetition() - 1][0]);
        assertEquals(Boolean.parseBoolean(values[repetitionInfo.getCurrentRepetition() - 1][1]), location.isOnEquator());
    }

    @ParameterizedTest(name = "data: {0} - isPrimeMeridian: {1}")
    @MethodSource("createLocations")
    void testIsOnPrimeMeridianMethodSource(String text, Boolean isPrime) {
        Location location = locationParser.parser(text);
        assertEquals(isPrime, location.isOnPrimeMeridian());
    }

    static Stream<Arguments> createLocations() {
        return Stream.of(
                Arguments.arguments("Budapest,0,0", true),
                Arguments.arguments("Kecskemét,0,1", false)
        );
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv")
    void testDistance(double lat1, double lon1, double lat2, double lon2, double distance) {
        Location location1 = new Location("", lat1, lon1);
        Location location2 = new Location("", lat2, lon2);
        assertEquals(distance, location1.distance(location2), 0.05);
    }

    @TestFactory
    Stream<DynamicTest> isEquator() {
        return Stream.of("Budapest,0,0", "Kecskemét,0,1")
                .map(text -> DynamicTest.dynamicTest(
                        "is on Equator:" + text,
                        () -> assertTrue(locationParser.parser(text).isOnEquator())
                ));
    }

//    @Test
//    void testLocationWithZeroCoordinate() {
//        Location result = locationParser.parser("Kecskemét,0,1");
//
//        assertThat(result, locationWithZeroCoordinate());
//    }

    Condition<Location> locationWithZeroCoordinate =
            new Condition<>(
                    location -> location.isOnPrimeMeridian() || location.isOnEquator(),
                    "Location have zero coordinate");

    @Test
    void testLocationWithZeroCoordinate() {
        Location result = locationParser.parser("Kecskemét,0,1");

        assertThat(result).has(locationWithZeroCoordinate);
    }
}