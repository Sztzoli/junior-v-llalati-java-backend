package location;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LocationNestedTest {

    LocationParser locationParser;
    Location location;

    @BeforeEach
    void setUp() {
        locationParser = new LocationParser();
    }

    @Nested
    class ZeroPoints {

        @BeforeEach
        void setUp() {
            location = locationParser.parser("zero,0,0");
        }

        @Test
        void testIsOnEquator() {
            assertTrue(location.isOnEquator());
        }

        @Test
        void testIsOnPrimeMeridian() {
            assertTrue(location.isOnPrimeMeridian());
        }
    }

    @Nested
    class Budapest {


        @BeforeEach
        void setUp() {
            location = locationParser.parser("Budapest,47.497912,19.040235");
        }

        @Test
        void testIsOnEquator() {
            assertFalse(location.isOnEquator());
        }

        @Test
        void testIsOnPrimeMeridian() {
            assertFalse(location.isOnPrimeMeridian());
        }
    }
}
