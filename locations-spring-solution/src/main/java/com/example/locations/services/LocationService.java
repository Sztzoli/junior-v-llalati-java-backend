package com.example.locations.services;

import com.example.locations.commands.CreateLocationCommand;
import com.example.locations.commands.UpdateLocationCommand;
import com.example.locations.converters.LocationDto;
import com.example.locations.exceptions.LocationNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;


import com.example.locations.model.Location;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class LocationService {

    private ModelMapper modelMapper;
    private AtomicLong idGenerator = new AtomicLong();

    public LocationService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    private List<Location> locations = Collections.synchronizedList(new ArrayList<>(List.of(
            new Location(idGenerator.incrementAndGet(), "Budapest", 47.497912, 19.040235),
            new Location(idGenerator.incrementAndGet(), "Kecskemét", 47, 20)
    )));

    public List<LocationDto> getLocations() {
        Type targetListType = new TypeToken<List<LocationDto>>() {
        }.getType();
        return modelMapper.map(locations, targetListType);
    }

    public LocationDto findLocationByName(String name) {
        return modelMapper.map(
                locations.stream()
                        .filter(location -> location.getName().equalsIgnoreCase(name)).findFirst()
                        .orElseThrow(() -> new LocationNotFoundException("Location not found by: " + name)),
                LocationDto.class);
    }

    public LocationDto findLocationById(Long id) {
        return modelMapper.map(
                getLocationById(id),
                LocationDto.class);
    }

    public LocationDto createLocation(CreateLocationCommand command) {
        Location location = new Location(idGenerator.incrementAndGet(), command.getName(), command.getLat(), command.getLon());
        locations.add(location);
        return modelMapper.map(location, LocationDto.class);
    }

    public LocationDto updateLocation(Long id, UpdateLocationCommand command) {
        Location location = getLocationById(id);
        location.setName(command.getName());
        location.setLat(command.getLat());
        location.setLon(command.getLon());
        return modelMapper.map(location, LocationDto.class);
    }

    public void deleteLocation(Long id) {
        Location location = getLocationById(id);
        locations.remove(location);
    }


    public void deleteAllLocations() {
        idGenerator = new AtomicLong();
        locations.clear();
    }

    private Location getLocationById(Long id) {
        return locations.stream().filter(location -> location.getId().equals(id)).findFirst()
                .orElseThrow(() -> new LocationNotFoundException("Location not found by: " + id));
    }

}
