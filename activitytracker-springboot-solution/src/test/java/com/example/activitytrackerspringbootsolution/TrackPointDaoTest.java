package com.example.activitytrackerspringbootsolution;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TrackPointDaoTest {

    @Autowired
    TrackPointDao trackPointDao;


    @Test
    void coordinate() {
        LocalDate time = LocalDate.of(2017,7,1);
        int lat = 1;
        int lon = -1;
        for (int i = 0; i < 30; i++) {
            trackPointDao.saveTrackPoint(new TrackPoint(time,lat++,lon--));
            time = time.plusDays(1);
        }
        List<Coordinate> coordinates = trackPointDao.findTrackPointCoordinatesByDate(LocalDate.of(2017, 7, 15), 5, 5);

        assertEquals(5,coordinates.size());
        assertEquals(21,coordinates.get(0).getLat());
    }

}