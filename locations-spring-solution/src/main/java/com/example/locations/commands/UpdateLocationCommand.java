package com.example.locations.commands;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLocationCommand {

    @Schema(description = "name of location", example = "Kecskemét")
    private String name;
    @Schema(description = "lat of location", example = "47.0")
    private double lat;
    @Schema(description = "lon of location", example = "20.0")
    private double lon;
}
