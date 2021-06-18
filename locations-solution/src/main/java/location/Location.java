package location;

public class Location {

    private String name;
    private double lat;
    private double lon;


    public Location(String name, double lat, double lon) {
        this.name = name;
        if (lat < -90 || lat > 90) {
            throw new IllegalArgumentException("Wrong lat:" + lat);
        }
        this.lat = lat;
        if (lon < -180 || lon > 180) {
            throw new IllegalArgumentException("Wrong lon:" + lon);
        }
        this.lon = lon;
    }

    public static Location convertToLocation(String line) {
        String[] parts = line.split(",");
        return new Location(parts[0], Double.parseDouble(parts[1]), Double.parseDouble(parts[2]));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public boolean isOnEquator() {
        return lat == 0;
    }

    public boolean isOnPrimeMeridian() {
        return lon == 0;
    }

    public double distance(Location locationFrom) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(locationFrom.lat - this.lat);
        double lonDistance = Math.toRadians(locationFrom.lon - this.lon);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(this.lat)) * Math.cos(Math.toRadians(locationFrom.lat))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters


        distance = Math.pow(distance, 2);

        return Math.sqrt(distance);
    }
}
