package activitytracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrackPointDaoTest {


    TrackPointDao trackPointDao;

    @BeforeEach
    void setUp() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");
        trackPointDao = new TrackPointDao(factory);
    }

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