package com.example.navapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AppointmentControllerIT {

    @Autowired
    TestRestTemplate template;

    @Test
    void createAppointment() {
        Appointment appointment = template.postForObject(
                "/api/appointments",
                new AppointmentCommand("1111111111",
                        LocalDateTime.now().plusDays(1),
                        LocalDateTime.now().plusDays(1).plusHours(1),
                        "001"),
                Appointment.class);

        assertEquals("1111111111",appointment.getTaxNumber());
    }

    @Test
    void invalidTaxNumber1() {
        Problem problem = template.postForObject(
                "/api/appointments",
                new AppointmentCommand("111111111",
                        LocalDateTime.now().plusDays(1),
                        LocalDateTime.now().plusDays(1).plusHours(1),
                        "001"),
                Problem.class);

        assertEquals(Status.BAD_REQUEST,problem.getStatus());
    }

    @Test
    void invalidTaxNumber2() {
        Problem problem = template.postForObject(
                "/api/appointments",
                new AppointmentCommand("1111111110",
                        LocalDateTime.now().plusDays(1),
                        LocalDateTime.now().plusDays(1).plusHours(1),
                        "001"),
                Problem.class);

        assertEquals(Status.BAD_REQUEST,problem.getStatus());
    }

    @Test
    void invalidTaxNumber3() {
        Problem problem = template.postForObject(
                "/api/appointments",
                new AppointmentCommand("1111s11110",
                        LocalDateTime.now().plusDays(1),
                        LocalDateTime.now().plusDays(1).plusHours(1),
                        "001"),
                Problem.class);

        assertEquals(Status.BAD_REQUEST,problem.getStatus());
    }

    @Test
    void invalidStartTime() {
        Problem problem = template.postForObject(
                "/api/appointments",
                new AppointmentCommand("1111s11110",
                        LocalDateTime.now().minusDays(1),
                        LocalDateTime.now().plusDays(1).plusHours(1),
                        "001"),
                Problem.class);

        assertEquals(Status.BAD_REQUEST,problem.getStatus());
    }

    @Test
    void invalidEndTime() {
        Problem problem = template.postForObject(
                "/api/appointments",
                new AppointmentCommand("1111s11110",
                        LocalDateTime.now().plusDays(1),
                        LocalDateTime.now().minusDays(1).plusHours(1),
                        "001"),
                Problem.class);

        assertEquals(Status.BAD_REQUEST,problem.getStatus());
    }

    @Test
    void invalidInternalTime() {
        Problem problem = template.postForObject(
                "/api/appointments",
                new AppointmentCommand("1111s11110",
                        LocalDateTime.now().plusDays(1).plusHours(1),
                        LocalDateTime.now().plusDays(1),
                        "001"),
                Problem.class);

        assertEquals(Status.BAD_REQUEST,problem.getStatus());
    }

    @Test
    void invalidType() {
        Problem problem = template.postForObject(
                "/api/appointments",
                new AppointmentCommand("1111111111",
                        LocalDateTime.now().plusDays(1),
                        LocalDateTime.now().plusDays(1).plusHours(1),
                        "002"),
                Problem.class);

        assertEquals(Status.BAD_REQUEST,problem.getStatus());
    }
}