package com.example.locations.commands;

import com.example.locations.commands.validations.Coordinate;
import com.example.locations.commands.validations.Type;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateLocationCommand {

    @NotBlank(message = "name cannot be null")
    @Schema(description = "name of location", example = "Kecskemét")
    private String name;

//    @Min(value = -90, message = "min value of lat -90 ")@Max(value = 90, message = "max value of lat 90")
    @Coordinate(type = Type.LAT, message = "lat is bigger than -90 and below than 90")
    @Schema(description = "lat of location", example = "47.0")
    private double lat;

//    @Min(value = -180, message = "min value of lon -180")@Max(value = 180, message = "max value of lon +180")
    @Coordinate(type = Type.LON, message = "lon is bigger than -180 and below than 180")
    @Schema(description = "lon of location", example = "20.0")
    private double lon;
}
