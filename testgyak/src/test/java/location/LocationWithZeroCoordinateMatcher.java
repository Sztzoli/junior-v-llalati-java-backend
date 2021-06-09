package location;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class LocationWithZeroCoordinateMatcher extends TypeSafeMatcher<Location> {


    public static Matcher locationWithZeroCoordinate() {
        return new LocationWithZeroCoordinateMatcher();
    }

    @Override
    protected boolean matchesSafely(Location location) {
        return location.isOnPrimeMeridian() || location.isOnEquator();
    }

    @Override
    public void describeTo(Description description) {

    }
}
