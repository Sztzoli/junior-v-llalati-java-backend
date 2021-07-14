package com.example.navapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@InternalTime
public class AppointmentCommand {

    @TaxNumber
    private String taxNumber;

    @Future
    private LocalDateTime startTime;

    @Future
    private LocalDateTime endTime;

    @CaseType
    private String caseType;
}
