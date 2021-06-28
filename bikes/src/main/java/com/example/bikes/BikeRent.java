package com.example.bikes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BikeRent {

    private String bikeId;
    private String userId;
    private LocalDateTime putDown;
    private double distance;
}
