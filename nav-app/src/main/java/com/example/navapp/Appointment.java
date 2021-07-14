package com.example.navapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {

    private String taxNumber;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String caseType;
}
