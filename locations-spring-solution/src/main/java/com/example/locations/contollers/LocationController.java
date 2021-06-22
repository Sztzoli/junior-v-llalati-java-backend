package com.example.locations.contollers;


import com.example.locations.commands.CreateLocationCommand;
import com.example.locations.commands.UpdateLocationCommand;
import com.example.locations.converters.LocationDto;
import com.example.locations.model.Location;
import com.example.locations.services.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/locations")
@Tag(name = "Operations on locations")
@CrossOrigin(origins = "http://localhost:4200")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    //    @GetMapping
//    private String getLocations() {
//        List<Location> locationList = new ArrayList<>();
//        locationList.add(new Location(1L,"Budapest",40,40));
//        locationList.add(new Location(2L,"Kecskemét",40,40));
//        return locationList.toString();
//    }

    @GetMapping
    @Operation(summary = "receive Locations")
    public List<LocationDto> getLocations() {
        return locationService.getLocations();
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "receive a Location by Name")
    public LocationDto findLocationByName(
            @Parameter(description = "name of location", example = "Budapest")
            @PathVariable String name) {
        return locationService.findLocationByName(name);
    }

    @GetMapping(value = "/id/{id}")
    @Operation(summary = "receive a Location by Id")
    public LocationDto getLocationById(
            @Parameter(description = "id of location", example = "1")
            @PathVariable Long id) {
        return locationService.findLocationById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "create new Location")
    @ApiResponse(responseCode = "201", description = "location has been created")
    public LocationDto createLocation(@Valid @RequestBody CreateLocationCommand command) {
        return locationService.createLocation(command);
    }

    @PutMapping("/{id}")
    @Operation(summary = "update a Location by Id")
    public LocationDto updateLocation(
            @Parameter(description = "id of location", example = "1")
            @PathVariable Long id, @Valid @RequestBody UpdateLocationCommand command) {
        return locationService.updateLocation(id, command);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete a Location by Id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLocation(
            @Parameter(description = "id of location", example = "1")
            @PathVariable Long id) {
        locationService.deleteLocation(id);
    }


}
