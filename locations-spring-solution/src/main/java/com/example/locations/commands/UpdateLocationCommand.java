package com.example.locations.commands;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLocationCommand {

    @NotBlank(message = "name cannot be null")
    @Schema(description = "name of location", example = "Kecskemét")
    private String name;

    @Min(value = -90, message = "min value of lat -90 ")@Max(value = 90, message = "max value of lat 90")
    @Schema(description = "lat of location", example = "47.0")
    private double lat;

    @Min(value = -180, message = "min value of lon -180")@Max(value = 180, message = "max value of lon +180")
    @Schema(description = "lon of location", example = "20.0")
    private double lon;
}
