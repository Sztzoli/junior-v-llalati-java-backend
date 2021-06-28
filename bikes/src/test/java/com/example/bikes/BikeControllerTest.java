package com.example.bikes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Incubating;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BikeControllerTest {

    @Mock
    BikeService bikeService;

    @InjectMocks
    BikeController bikeController;

    List<BikeRent> bikeRentList;

    @BeforeEach
    void setUp() {
        bikeRentList = new ArrayList<>(List.of(
                new BikeRent("1B","1U", LocalDateTime.of(2021, Month.JUNE,28,11,15,50),1.1),
                new BikeRent("2B","2U", LocalDateTime.of(2021, Month.JUNE,27,11,15,50),0.9)
        ));
    }

    @Test
    void getHistories() {
        when(bikeService.getHistories()).thenReturn(bikeRentList);

        List<BikeRent> result = bikeController.getHistories();

        assertThat(result)
                .hasSize(2)
                .extracting(BikeRent::getBikeId)
                .containsExactly("1B","2B");

        verify(bikeService, times(1)).getHistories();

    }

    @Test
    void getUsers() {
        when(bikeService.getUsers())
                .thenReturn(bikeRentList.stream().map(BikeRent::getUserId).collect(Collectors.toList()));

        List<String> result = bikeController.getUsers();

        assertThat(result)
                .hasSize(2)
                .containsExactly("1U","2U");

        verify(bikeService, times(1)).getUsers();
    }
}