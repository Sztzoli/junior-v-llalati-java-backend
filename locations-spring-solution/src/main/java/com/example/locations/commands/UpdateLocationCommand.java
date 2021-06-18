package com.example.locations.commands;

import lombok.Data;

@Data
public class UpdateLocationCommand {

    private String name;
    private double lat;
    private double lon;
}
