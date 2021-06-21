package com.example.locations.contollers;


import com.example.locations.commands.CreateLocationCommand;
import com.example.locations.commands.UpdateLocationCommand;
import com.example.locations.converters.LocationDto;
import com.example.locations.model.Location;
import com.example.locations.services.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public List<LocationDto> getLocations() {
        return locationService.getLocations();
    }

    @GetMapping("/name/{name}")
    public LocationDto findLocationByName(@PathVariable String name) {
        return locationService.findLocationByName(name);
    }

    @GetMapping(value = "/id/{id}")
    public LocationDto getLocationById(@PathVariable Long id) {
        return locationService.findLocationById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "create new Location")
    @ApiResponse(responseCode = "201", description = "location has been created")
    public LocationDto createLocation(@RequestBody CreateLocationCommand command) {
        return locationService.createLocation(command);
    }

    @PutMapping("/{id}")
    public LocationDto updateLocation(@PathVariable Long id, @RequestBody UpdateLocationCommand command) {
        return locationService.updateLocation(id, command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
    }


}
