package com.example.bikes;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@NoArgsConstructor
public class BikeService {

    private List<BikeRent> bikeRentList = new ArrayList<>();

    public List<BikeRent> getHistories() {
        preCheckForBikeRentList();
        return bikeRentList;
    }

    public List<String> getUsers() {
        preCheckForBikeRentList();
        return bikeRentList.stream()
                .map(BikeRent::getUserId)
                .collect(Collectors.toList());
    }

    private void readFromFile() {
        try (Stream<String> lines = Files.lines(Path.of("src/main/resources/bikes.csv"))) {
            lines.map(this::convertToBikeRent).forEach(bikeRentList::add);
        } catch (IOException ioe) {
            throw new IllegalStateException("File cannot find", ioe);
        }
    }

    private BikeRent convertToBikeRent(String line) {
        String[] parts = line.split(";");
        return new BikeRent(
                parts[0],
                parts[1],
                LocalDateTime.parse(parts[2],
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                Double.parseDouble(parts[3]));
    }


    private void preCheckForBikeRentList() {
        if (bikeRentList.isEmpty()) {
            readFromFile();
        }
    }
}
