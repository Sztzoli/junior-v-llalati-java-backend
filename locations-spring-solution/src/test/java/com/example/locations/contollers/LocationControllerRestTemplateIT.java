package com.example.locations.contollers;

import com.example.locations.commands.CreateLocationCommand;
import com.example.locations.commands.UpdateLocationCommand;
import com.example.locations.converters.LocationDto;
import com.example.locations.services.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LocationControllerRestTemplateIT {

    @Autowired
    TestRestTemplate template;

    @Autowired
    LocationService locationService;

    @BeforeEach
    void setUp() {
        locationService.deleteAllLocations();
    }

    @Test
    void testListLocations() {
        LocationDto kecskemét
                = template.postForObject("/locations",
                new CreateLocationCommand("Kecskemét", 47d, 20d), LocationDto.class);
        LocationDto budapest
                = template.postForObject("/locations",
                new CreateLocationCommand("Budapest", 47.497912, 19.040235), LocationDto.class);

        List<LocationDto> locations = template.exchange("/locations",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<LocationDto>>() {
                })
                .getBody();

        assertThat(locations)
                .extracting(LocationDto::getName)
                .containsExactly("Kecskemét", "Budapest");
    }

    @Test
    void testFindLocationById() {
        LocationDto kecskemét
                = template.postForObject("/locations",
                new CreateLocationCommand("Kecskemét", 47d, 20d), LocationDto.class);

        LocationDto result = template.exchange("/locations/id/1",
                HttpMethod.GET,
                null,
                LocationDto.class
        )
                .getBody();

        assertEquals("Kecskemét", result.getName());
    }

    @Test
    void testFindLocationByName() {
        LocationDto kecskemét
                = template.postForObject("/locations",
                new CreateLocationCommand("Kecskemét", 47d, 20d), LocationDto.class);

        LocationDto result = template.exchange("/locations/name/kecskemét",
                HttpMethod.GET,
                null,
                LocationDto.class
        )
                .getBody();

        assertEquals("Kecskemét", result.getName());
    }

    @Test
    void testPostLocation() {
        LocationDto kecskemét
                = template.postForObject("/locations",
                new CreateLocationCommand("Kecskemét", 47d, 20d), LocationDto.class);

        assertEquals("Kecskemét", kecskemét.getName());
    }

    @Test
    void testPutLocations() {
        LocationDto kecskemét
                = template.postForObject("/locations",
                new CreateLocationCommand("Kecskemét", 47d, 20d), LocationDto.class);
        template.put("/locations/1",
                new UpdateLocationCommand("Budapest", 1, 1));

        LocationDto result = template.exchange("/locations/id/1",
                HttpMethod.GET,
                null,
                LocationDto.class
        )
                .getBody();

        assertEquals("Budapest", result.getName());

    }

    @Test
    void testDeleteLocationById() {
        LocationDto kecskemét
                = template.postForObject("/locations",
                new CreateLocationCommand("Kecskemét", 47d, 20d), LocationDto.class);

        template.delete("/locations/1");

        List<LocationDto> locations = template.exchange("/locations",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<LocationDto>>() {
                })
                .getBody();

        assertEquals(0, locations.size());


    }

}
