package com.example.locations.contollers;

import com.example.locations.converters.LocationDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@SpringBootTest
public class LocationControllerIT {

    @Autowired
    LocationController locationController;

    @Test
    void testGetLocations() {

        List<LocationDto> result = locationController.getLocations();

        assertThat(result)
                .hasSize(2)
                .extracting(LocationDto::getName)
                .contains("Budapest", "Kecskemét");
    }

    @Test
    void findLocationByName() {

        LocationDto result = locationController.findLocationByName("Budapest");
        assertEquals("Budapest", result.getName());
    }

    @Test
    void findLocationById() {

        LocationDto result = locationController.getLocationById(1L);
        assertEquals("Budapest", result.getName());
    }
}
