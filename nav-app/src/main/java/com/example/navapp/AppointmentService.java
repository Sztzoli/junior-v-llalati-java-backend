package com.example.navapp;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {

    private List<Appointment> appointments = new ArrayList<>();

    public Appointment createAppointment(AppointmentCommand command) {
        Appointment appointment =
                new Appointment(command.getTaxNumber(), command.getStartTime(), command.getEndTime(), command.getCaseType());
        appointments.add(appointment);
        return appointment;
    }
}
