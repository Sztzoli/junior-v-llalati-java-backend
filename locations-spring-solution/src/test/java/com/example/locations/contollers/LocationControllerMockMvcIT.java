package com.example.locations.contollers;

import com.example.locations.commands.CreateLocationCommand;
import com.example.locations.commands.UpdateLocationCommand;
import com.example.locations.converters.LocationDto;
import com.example.locations.services.LocationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = LocationController.class)
public class LocationControllerMockMvcIT {

    @MockBean
    LocationService locationService;

    @Autowired
    MockMvc mockMvc;

    LocationDto budapest;
    LocationDto kecskemet;

    @BeforeEach
    void setUp() {
        budapest = new LocationDto("Budapest", 47.497912, 19.040235);
        kecskemet = new LocationDto("Kecskemét", 47, 20);
    }

    @Test
    void testGetLocations() throws Exception {
        when(locationService.getLocations()).thenReturn(new ArrayList<>(List.of(
                budapest, kecskemet
        )));

        mockMvc.perform(get("/locations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", equalTo("Budapest")));
    }

    @Test
    void testGetLocation() throws Exception {
        when(locationService.findLocationById(anyLong())).thenReturn(budapest);


        mockMvc.perform(get("/locations/id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", equalTo("Budapest")));
    }

    @Test
    void testGetLocationByName() throws Exception {
        when(locationService.findLocationByName(anyString())).thenReturn(budapest);


        mockMvc.perform(get("/locations/name/budapest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", equalTo("Budapest")));
    }

    String asJsonString(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    @Test
    void testPostLocation() throws Exception {
        when(locationService.createLocation(any())).thenReturn(budapest);

        mockMvc.perform(post("/locations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new CreateLocationCommand("Budapest", 1d, 1d)))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name", equalTo("Budapest")));
    }

    @Test
    void testPutLocation() throws Exception {
        when(locationService.updateLocation(anyLong(), any(UpdateLocationCommand.class))).thenReturn(budapest);

        mockMvc.perform(put("/locations/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new UpdateLocationCommand("Kecskemét", 1d, 1d)))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", equalTo("Budapest")));
    }

    @Test
    void testDeleteLocation() throws Exception {

        mockMvc.perform(delete("/locations/1")
        )
                .andExpect(status().isNoContent());
        verify(locationService, times(1)).deleteLocation(anyLong());
    }


}
