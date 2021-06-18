package location;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LocationService {

    public void writeLocations(Path file, List<Location> locations) {
        Locale.setDefault(new Locale("en", "UK"));
        try (BufferedWriter writer = Files.newBufferedWriter(file)) {
            for (Location location : locations) {
                String text = String.format("%s,%f,%f\n", location.getName(), location.getLat(), location.getLon());
                writer.write(text);
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("File cannot write", ioe);
        }
    }

    public List<Location> readLocations(Path file) {
        try (Stream<String> lines = Files.lines(file)) {
            return lines
                    .map(Location::convertToLocation)
                    .collect(Collectors.toList());

        } catch (IOException ioe) {
            throw new IllegalArgumentException("File cannot read", ioe);
        }
    }

}
