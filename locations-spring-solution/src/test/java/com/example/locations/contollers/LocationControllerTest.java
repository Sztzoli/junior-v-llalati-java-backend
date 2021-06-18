package com.example.locations.contollers;

import com.example.locations.commands.CreateLocationCommand;
import com.example.locations.commands.UpdateLocationCommand;
import com.example.locations.converters.LocationDto;
import com.example.locations.services.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationControllerTest {

    @InjectMocks
    private LocationController locationController;

    @Mock
    private LocationService locationService;

    LocationDto budapest;
    LocationDto kecskemet;

    @BeforeEach
    void setUp() {
        budapest = new LocationDto("Budapest", 47.497912, 19.040235);
        kecskemet = new LocationDto("Kecskemét", 47, 20);
    }

    @Test
    void testGetLocations() {
        List<LocationDto> locations = new ArrayList<>(List.of(
                budapest, kecskemet));

        when(locationService.getLocations()).thenReturn(locations);

        List<LocationDto> result = locationController.getLocations();

        assertThat(result)
                .hasSize(2)
                .extracting(LocationDto::getName)
                .contains("Budapest", "Kecskemét");
    }

    @Test
    void findLocationByName() {

        when(locationService.findLocationByName(anyString())).thenReturn(budapest);

        LocationDto result = locationController.findLocationByName("Budapest");

        assertEquals("Budapest", result.getName());
    }

    @Test
    void findLocationById() {
        when(locationService.findLocationById(anyLong())).thenReturn(budapest);

        LocationDto result = locationController.getLocationById(1L);

        assertEquals("Budapest", result.getName());
    }

    @Test
    void createLocation() {
        when(locationService.createLocation(any())).thenReturn(budapest);

        LocationDto result = locationController.createLocation(new CreateLocationCommand());

        assertEquals("Budapest", result.getName());
    }

    @Test
    void updateLocation() {
        when(locationService.updateLocation(anyLong(), any())).thenReturn(budapest);

        LocationDto result = locationController.updateLocation(1L, new UpdateLocationCommand());

        assertEquals("Budapest", result.getName());
    }

    @Test
    void delete() {
        locationController.deleteLocation(1L);
        verify(locationService,times(1)).deleteLocation(anyLong());
    }
}