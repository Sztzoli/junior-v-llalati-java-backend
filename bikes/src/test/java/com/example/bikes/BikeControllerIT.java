package com.example.bikes;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BikeControllerIT {

    @Autowired
    BikeController bikeController;

    @Test
    void getHistories() {

        List<BikeRent> result = bikeController.getHistories();

        assertThat(result)
                .isNotNull()
                .extracting(BikeRent::getBikeId)
                .contains("FH675", "FH676");

    }

    @Test
    void getUsers() {
        List<String> result = bikeController.getUsers();

        assertThat(result)
                .contains("US3434");

    }
}
