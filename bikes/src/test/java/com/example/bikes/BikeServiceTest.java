package com.example.bikes;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class BikeServiceTest {

    BikeService bikeService = new BikeService();

    @Test
    void getHistories() {
        List<BikeRent> result = bikeService.getHistories();

        assertThat(result)
                .isNotNull()
                .extracting(BikeRent::getBikeId)
                .contains("FH675", "FH676");
    }

    @Test
    void getUsers() {
        List<String> result = bikeService.getUsers();

        assertThat(result)
                .contains("US3434");

    }
}