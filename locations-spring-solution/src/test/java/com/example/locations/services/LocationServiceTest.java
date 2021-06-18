package com.example.locations.services;

import com.example.locations.commands.CreateLocationCommand;
import com.example.locations.commands.UpdateLocationCommand;
import com.example.locations.converters.LocationDto;
import com.example.locations.exceptions.LocationNotFoundException;
import com.example.locations.model.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;

class LocationServiceTest {

    LocationService locationService;

    ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void setUp() {
        locationService = new LocationService(modelMapper);
    }


    @Test
    void getLocations() {
        List<LocationDto> result = locationService.getLocations();

        assertThat(result)
                .hasSize(2)
                .extracting(LocationDto::getName)
                .contains("Budapest", "Kecskemét");
    }

    @Test
    void findLocationByName() {
        LocationDto result = locationService.findLocationByName("Budapest");

        assertThat(result.getName())
                .isEqualTo("Budapest");
    }

    @Test
    void findLocationByNameNotFound() {

        Exception ex = assertThrows(LocationNotFoundException.class, () -> locationService.findLocationByName("Cegléd"));
        assertEquals("Location not found by: Cegléd", ex.getMessage());

    }

    @Test
    void findLocationById() {
        LocationDto result = locationService.findLocationById(1L);

        assertThat(result).isNotNull()
                .extracting(LocationDto::getName).isEqualTo("Budapest");
    }

    @Test
    void findLocationByIdNotFound() {
        Exception ex = assertThrows(LocationNotFoundException.class, () -> locationService.findLocationById(10L));
        assertEquals("Location not found by: 10", ex.getMessage());
    }

    @Test
    void createLocation() {
        CreateLocationCommand command = new CreateLocationCommand("Cegléd", 50d, 40d);
        LocationDto result = locationService.createLocation(command);

        assertAll(
                () -> assertEquals("Cegléd", result.getName()),
                () -> assertEquals(50d, result.getLat()),
                () -> assertEquals(40d, result.getLon())
        );
    }

    @Test
    void updateLocation() {
        UpdateLocationCommand command = new UpdateLocationCommand();
        command.setName("Cegléd");
        command.setLat(50d);
        command.setLon(40d);
        LocationDto result = locationService.updateLocation(1L, command);
        assertAll(
                () -> assertEquals("Cegléd", result.getName()),
                () -> assertEquals(50d, result.getLat()),
                () -> assertEquals(40d, result.getLon())
        );
    }

    @Test
    void deleteLocation() {
        locationService.deleteLocation(1L);

        assertEquals(1, locationService.getLocations().size());

    }

}